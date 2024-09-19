package com.dynamic.api_key_management.controller;

import com.dynamic.api_key_management.model.Company;
import com.dynamic.api_key_management.service.ClientService;
import com.dynamic.api_key_management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ClientService clientService;

    @GetMapping("/hello")
    public String publicHello() {
        return "Hello, this is a public endpoint!";
    }

    @GetMapping("/generate-default-company")
    public String generateCompany() throws NoSuchAlgorithmException {
        Long companyId = companyService.createCompany("self", "test@gmail.com");
        return clientService.createClient(companyId, "self-website");
    }

}
