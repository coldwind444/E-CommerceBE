package com.be.ecommerce.controller;

import com.be.ecommerce.dto.app.ProductResponse;
import com.be.ecommerce.model.Product;
import com.be.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/cloudinary")
    public ResponseEntity<String> cloudinary(@RequestParam String path) throws UnsupportedEncodingException {
        String url = productService.uploadImageToCloudinary(path);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody Product product) {
        ProductResponse productResponse = productService.addProduct(product);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody Product product) {
        ProductResponse productResponse = productService.updateProduct(product);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }

    @GetMapping("/get-all/{sellerId}")
    public ResponseEntity<ProductResponse> getAllProducts(@PathVariable int sellerId) {
        ProductResponse productResponse = productService.getAllProducts(sellerId);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable int id) {
        ProductResponse productResponse = productService.deleteProduct(id);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }
}
