package com.be.ecommerce.dao.interf;

import com.be.ecommerce.dto.database.VariantPropertyDbResponse;
import com.be.ecommerce.model.VariantProperty;

public interface VariantPropertyDao {
    VariantPropertyDbResponse getAllVariantProperties(int productId);
    VariantPropertyDbResponse addVariantProperty(VariantProperty variantProperty);
    VariantPropertyDbResponse updateVariantProperty(VariantProperty variantProperty);
    VariantPropertyDbResponse deleteVariantProperty(int productId);
}
