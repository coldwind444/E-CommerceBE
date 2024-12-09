package com.be.ecommerce.dao.interf;

import com.be.ecommerce.dto.database.ProductImageDbResponse;
import com.be.ecommerce.model.ProductImage;

public interface ProductImageDao {
    ProductImageDbResponse getProductImage(int productId);
    ProductImageDbResponse addProductImage(ProductImage productImage);
    ProductImageDbResponse updateProductImage(ProductImage productImage);
    ProductImageDbResponse deleteProductImage(int productId);
}
