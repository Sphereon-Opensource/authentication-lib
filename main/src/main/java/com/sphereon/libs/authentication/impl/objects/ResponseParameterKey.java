package com.sphereon.libs.authentication.impl.objects;

public enum ResponseParameterKey {

    ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token"), SCOPE("scope"), TOKEN_TYPE("token_type"), EXPIRES_IN("expires_in");


    private final String value;


    ResponseParameterKey(String value) {
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
