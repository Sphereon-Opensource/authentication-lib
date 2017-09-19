package com.sphereon.libs.authentication.impl.objects;

public enum TokenPathParameters {
    TOKEN("token"), REVOKE("revoke");


    private final String value;


    TokenPathParameters(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return getValue();
    }
}
