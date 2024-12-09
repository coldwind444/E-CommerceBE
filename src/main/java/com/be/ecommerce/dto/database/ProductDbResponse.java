package com.be.ecommerce.dto.database;

import com.be.ecommerce.model.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDbResponse extends DatabaseResponse {
    Product product;
    List<Product> productList;
}
