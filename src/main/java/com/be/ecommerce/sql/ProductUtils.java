package com.be.ecommerce.sql;

import lombok.Getter;

@Getter
public enum ProductUtils {
    ADD_PRODUCT("{CALL InsertProduct(?,?,?,?,?,?)}"),
    UPDATE_PRODUCT("{CALL UpdateProduct(?,?,?,?,?)}"),
    DELETE_PRODUCT("{CALL DeleteProduct(?)}"),
    GET_PRODUCT_BY_ID("{CALL sp_get_product_by_id(?)}"),
    GET_ALL_PRODUCTS("{CALL sp_get_products_by_seller(?)}"),
    GET_ALL_PRODUCTS_WITH_FILTER("{CALL sp_get_seller_products(?,?,?,?,?) }"),
    GET_STOCK_PRODUCTS("{? = CALL list_out_of_stock_products(?)}"),
    ;

    ProductUtils(String call){
        this.call = call;
    }
    private final String call;
}
