package com.smrc.javadesignpatterns.architectural.apigateway.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @GetMapping("/image-url")
    public String getImagePath() {
        return "/product-image.png";
    }
}
