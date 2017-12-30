package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum ResponseParameterKey implements StringValueEnum {

    ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token"), SCOPE("scope"), TOKEN_TYPE("token_type"), EXPIRES_IN("expires_in");


    private final String value;


    ResponseParameterKey(String value) {
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return getValue();
    }
}
