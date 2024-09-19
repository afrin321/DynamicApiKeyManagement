package com.dynamic.api_key_management.filter;

import com.dynamic.api_key_management.service.ClientService;
import com.dynamic.api_key_management.util.ApiKeyUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@Component
public class ApiKeyRequestFilter extends OncePerRequestFilter {

    @Autowired
    private ClientService clientService;

    public ApiKeyRequestFilter(ClientService clientService) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Skip API key filtering for public endpoints (e.g., /public/**)
        if (requestPath.startsWith("/public")) {
            filterChain.doFilter(request, response);
            return; // Skip further processing
        }

        // Extract the API key from the request header
        String apiKey = request.getHeader("x-api-key");

        if (apiKey == null || apiKey.isEmpty()) {
            // API key is missing, respond with an unauthorized status
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: API key is missing\"}");
            return;
        }

        String hashedApiKey = null;
        try {
            hashedApiKey = ApiKeyUtils.hashApiKey(apiKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Validate the API key
        if (clientService.isValidApiKey(hashedApiKey)) {
            // Set authentication in the context to let Spring Security know this request is authenticated
            Authentication auth = new AnonymousAuthenticationToken(
                    "apiKey", "authenticatedClient",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_CLIENT")));

            auth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            // API key is invalid, respond with an unauthorized status
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized: API key is invalid\"}");
            return;
        }

        // Continue the request
        filterChain.doFilter(request, response);
    }
}
