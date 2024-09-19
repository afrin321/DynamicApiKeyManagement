package com.dynamic.api_key_management.repo;

import com.dynamic.api_key_management.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Additional query methods can be defined here
}
