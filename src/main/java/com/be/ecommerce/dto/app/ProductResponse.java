package com.be.ecommerce.dto.app;

import com.be.ecommerce.model.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    // general
    int status;
    String message;

    // return objects
    Product product;
    List<Product> productList;

    // logs
    List<ErrorLog> errorLogList;
}
