package com.be.ecommerce.sql;

import lombok.Getter;

@Getter
public enum ProductVariantUtils {
    ADD_VARIANT(""),
    UPDATE_VARIANT(""),
    DELETE_VARIANT(""),
    GET_ALL_VARIANTS(""),
    ;

    ProductVariantUtils(String call){
        this.call = call;
    }
    private final String call;
}
