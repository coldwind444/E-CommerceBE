package com.be.ecommerce.controller;

import com.be.ecommerce.dto.app.ProductResponse;
import com.be.ecommerce.model.Product;
import com.be.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody Product product) {
        var response = productService.addProduct(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody Product product) {
        var response = productService.updateProduct(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable int id) {
        var response = productService.getProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ProductResponse> getAllProducts() {
        var response = productService.getAllProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable int id) {
        var response = productService.deleteProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
