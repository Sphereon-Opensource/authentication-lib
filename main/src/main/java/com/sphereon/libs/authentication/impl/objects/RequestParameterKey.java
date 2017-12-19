package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum RequestParameterKey implements StringValueEnum {

    GRANT_TYPE("grant_type"),
    USER_NAME("username"),
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    WINDOWS_TOKEN("windows_token"),
    KERBEROS_REALM("kerberos_realm"),
    KERBEROS_TOKEN("kerberos_token"),
    ASSERTION("assertions"),
    VALIDITY_PERIOD("validity_period"),
    SCOPE("scope"),
    AUTHORIZATION("Authorization"),
    TOKEN("token");


    private final String value;


    RequestParameterKey(String value) {
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
