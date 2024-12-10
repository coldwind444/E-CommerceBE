package com.be.ecommerce.dao.interf;

import com.be.ecommerce.dto.database.ProductDbResponse;
import com.be.ecommerce.model.Product;

public interface ProductDao {
    ProductDbResponse addProduct(Product product);
    ProductDbResponse updateProduct(Product product);
    ProductDbResponse deleteProduct(int productId);
  //  ProductDbResponse getProduct(int id);
    ProductDbResponse getAllProducts(int sellerId);
}
