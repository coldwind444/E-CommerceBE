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
    int productId;
    String productName;
    int quantity;
    BigDecimal productPrice;
    int sellerId;
    String productDescription;

    List<ProductVariant> productVariantList;
    List<VariantProperty> variantPropertyList;
    List<ProductImage> productImageList;
}
