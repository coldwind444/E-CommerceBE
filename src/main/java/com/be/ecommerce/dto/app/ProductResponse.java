package com.be.ecommerce.dto.app;

import com.be.ecommerce.model.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
    int errorCode;
    String errorMessage;
}
