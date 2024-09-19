package com.dynamic.api_key_management.service;

import java.security.NoSuchAlgorithmException;

public interface ClientService {
    String createClient(Long companyId, String clientName) throws NoSuchAlgorithmException;
    boolean isValidApiKey(String apiKeyHash);
}
