package com.library.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {

        String name = (String) request.getAttribute("name");
        String role = (String) request.getAttribute("role");

        if (name == null) {
            return "Unauthorized access";
        }

        if ("ADMIN".equals(role)) {
            return "Welcome Admin " + name + "! You have full access to the Library Management System.";
        } else {
            return "Welcome " + name + "! You have access to the Library.";
        }
    }
}