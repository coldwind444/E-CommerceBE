package com.be.ecommerce.sql;

import lombok.Getter;

@Getter
public enum VariantPropertyUtils {
    ADD_PROPERTY(""),
    UPDATE_PROPERTY(""),
    DELETE_PROPERTY(""),
    GET_ALL_PROPERTIES("")
    ;

    VariantPropertyUtils(String call){
        this.call = call;
    }
    private final String call;
}
