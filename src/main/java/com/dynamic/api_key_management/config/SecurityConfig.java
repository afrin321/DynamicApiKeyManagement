package com.dynamic.api_key_management.config;

import com.dynamic.api_key_management.filter.ApiKeyRequestFilter;
import com.dynamic.api_key_management.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private ClientService clientService;

    @Bean
    public ApiKeyRequestFilter apiKeyRequestFilter() {
        return new ApiKeyRequestFilter(clientService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)// Disable CSRF for simplicity
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public/**").permitAll() // Public endpoints
                                .requestMatchers("/protected/**").hasAuthority("ROLE_API_CLIENT") // Authenticated endpoints
                )
                .addFilterBefore(apiKeyRequestFilter(), UsernamePasswordAuthenticationFilter.class); // Add custom filter

        return http.build();
    }
}
