package com.dynamic.api_key_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @GetMapping("/hello")
    public String protectedHello() {
        return "Hello, this is a protected endpoint!";
    }
}
