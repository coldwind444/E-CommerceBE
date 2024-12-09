package com.be.ecommerce.dto.database;

import com.be.ecommerce.model.ProductVariant;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantDbResponse extends DatabaseResponse {
    ProductVariant productVariant;
    List<ProductVariant> productVariants;
}
