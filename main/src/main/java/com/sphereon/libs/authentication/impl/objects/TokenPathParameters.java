package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum TokenPathParameters implements StringValueEnum {
    TOKEN("token"), REVOKE("revoke");


    private final String value;


    TokenPathParameters(String value) {
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
