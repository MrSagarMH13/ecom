package com.ecom.dynamic.web.controller;

import com.ecom.dynamic.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Map<String, Object> requestData) {
        productService.createTableAndInsertRecords(requestData);
        return ResponseEntity.ok("Table created and data inserted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Map<String, Object>> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

