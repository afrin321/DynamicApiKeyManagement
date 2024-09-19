package com.dynamic.api_key_management.repo;

import com.dynamic.api_key_management.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByApiKeyHash(String apiKeyHash);
}
