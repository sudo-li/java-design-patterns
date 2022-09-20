package com.smrc.javadesignpatterns.architectural.apigateway.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
    @GetMapping("/price")
    public String getPrice() {
        return "400";
    }
}
