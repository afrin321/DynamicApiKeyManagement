package com.dynamic.api_key_management.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ApiKeyUtils {

    public static String hashApiKey(String apiKey) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(apiKey.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String generateApiKey() {
        // In a real application, use a library to generate secure random API keys.
        return java.util.UUID.randomUUID().toString();
    }
}
