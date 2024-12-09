package com.be.ecommerce.sql;

import lombok.Getter;

@Getter
public enum ProductUtils {
    ADD_PRODUCT(""),
    UPDATE_PRODUCT(""),
    DELETE_PRODUCT(""),
    GET_PRODUCT_BY_ID(""),
    GET_ALL_PRODUCTS(""),
    ;

    ProductUtils(String call){
        this.call = call;
    }
    private final String call;
}
