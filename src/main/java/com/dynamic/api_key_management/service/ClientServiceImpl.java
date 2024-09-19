package com.dynamic.api_key_management.service;

import com.dynamic.api_key_management.model.Client;
import com.dynamic.api_key_management.model.Company;
import com.dynamic.api_key_management.repo.ClientRepository;
import com.dynamic.api_key_management.repo.CompanyRepository;
import com.dynamic.api_key_management.util.ApiKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public String createClient(Long companyId, String clientName) throws NoSuchAlgorithmException {
        // Generate a new API key
        String apiKey = ApiKeyUtils.generateApiKey();
        // Hash the API key before storing it
        String apiKeyHash = ApiKeyUtils.hashApiKey(apiKey);

        // Retrieve the company from the repository
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));

        // Save client with hashed API key
        Client client = Client.builder()
                .company(company)
                .clientName(clientName)
                .apiKeyHash(apiKeyHash)
                .isActive(true)
                .build();

        clientRepository.save(client);

        // Optionally, return or log the API key for client usage
        System.out.println("Generated API Key for client: " + apiKey);
        return apiKey;
    }

    @Override
    public boolean isValidApiKey(String apiKeyHash) {
        return clientRepository.findByApiKeyHash(apiKeyHash).isPresent();
    }
}
