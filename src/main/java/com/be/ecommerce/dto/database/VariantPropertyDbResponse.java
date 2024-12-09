package com.be.ecommerce.dto.database;

import com.be.ecommerce.model.VariantProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantPropertyDbResponse extends DatabaseResponse{
    VariantProperty variantProperty;
    List<VariantProperty> variantPropertyList;
}
