package com.be.ecommerce.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    // general
    int productId;
    String productName;
    int quantity;
    BigDecimal productPrice;
    String productDescription;
    String imgUrl;

    // optional
    int sellerId;
    String shopName;
    String shopDescription;
    int categoryId;
    String categoryName;
}
