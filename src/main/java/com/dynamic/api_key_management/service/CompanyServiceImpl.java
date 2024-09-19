package com.dynamic.api_key_management.service;

import com.dynamic.api_key_management.model.Company;
import com.dynamic.api_key_management.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Long createCompany(String name, String email) {
        Company company = Company.builder()
                .name(name)
                .email(email)
                .build();
        return companyRepository.save(company).getId();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
