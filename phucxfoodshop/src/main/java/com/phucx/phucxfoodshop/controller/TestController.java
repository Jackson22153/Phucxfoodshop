package com.phucx.phucxfoodshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phucx.phucxfoodshop.exceptions.NotFoundException;
import com.phucx.phucxfoodshop.service.product.ProductSizeService;

@RestController
public class TestController {
    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping("/test")
    public ResponseEntity<String> generateOTP() throws NotFoundException{

        return ResponseEntity.ok().body("Successful");
    }
}
