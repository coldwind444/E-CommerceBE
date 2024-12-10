package com.be.ecommerce.controller;

import com.be.ecommerce.dto.app.ProductResponse;
import com.be.ecommerce.model.Product;
import com.be.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Objects;


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

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable int id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable int id) {
        ProductResponse productResponse = productService.deleteProduct(id);
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }

    @GetMapping("/get-all-filter/{sellerId}")
    public ResponseEntity<ProductResponse> getAllProductsWithFilter(
            @PathVariable int sellerId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String min,
            @RequestParam(required = false) String max,
            @RequestParam(required = false) String order
    ) {
        // Initialize minValue and maxValue as null
        BigDecimal minValue = null;
        BigDecimal maxValue = null;

        // Automatically parse 'min' to BigDecimal or null if it's empty or "null"
        if (min != null && !min.trim().isEmpty() && !min.equals("null")) {
            minValue = new BigDecimal(min);
        }

        // Automatically parse 'max' to BigDecimal or null if it's empty or "null"
        if (max != null && !max.trim().isEmpty() && !max.equals("null")) {
            maxValue = new BigDecimal(max);
        }

        // Print values for debugging (optional, can be removed in production)
        System.out.println("Search: " + search + ", Min: " + min + ", Max: " + max + ", Order: " + order);

        // Call the service layer to fetch filtered products
        ProductResponse productResponse = productService.getAllProductsWithFilter(sellerId, search, minValue, maxValue, order);

        // Return the response with appropriate status
        return ResponseEntity.status(productResponse.getStatus()).body(productResponse);
    }


}
