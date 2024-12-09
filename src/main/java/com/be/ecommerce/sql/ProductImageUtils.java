package com.be.ecommerce.sql;

import lombok.Getter;

@Getter
public enum ProductImageUtils {
    ADD_IMAGE(""),
    UPDATE_IMAGE(""),
    DELETE_IMAGE(""),
    GET_IMAGE(""),
    ;
    ProductImageUtils(String call){
        this.call = call;
    }
    private final String call;
}
