package com.dynamic.api_key_management.service;

import com.dynamic.api_key_management.model.Company;

public interface CompanyService {
    Long createCompany(String name, String email);
    Company getCompanyById(Long id);
    // Additional methods for updating and deleting companies
}
