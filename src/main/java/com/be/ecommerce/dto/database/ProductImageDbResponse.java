package com.be.ecommerce.dto.database;

import com.be.ecommerce.model.ProductImage;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageDbResponse extends DatabaseResponse {
    ProductImage productImage;
}
