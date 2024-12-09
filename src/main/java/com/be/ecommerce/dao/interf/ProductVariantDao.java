package com.be.ecommerce.dao.interf;

import com.be.ecommerce.dto.database.ProductVariantDbResponse;
import com.be.ecommerce.model.Product;
import com.be.ecommerce.model.ProductVariant;

public interface ProductVariantDao {
    ProductVariantDbResponse addProductVariant(ProductVariant productVariant);
    ProductVariantDbResponse updateProductVariant(ProductVariant productVariant);
    ProductVariantDbResponse deleteProductVariant(int productId);
    ProductVariantDbResponse getAllProductVariants(int productId);
}
