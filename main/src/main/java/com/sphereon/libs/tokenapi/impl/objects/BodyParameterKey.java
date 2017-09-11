package com.sphereon.libs.tokenapi.impl.objects;

public enum BodyParameterKey {

    GRANT_TYPE("grant_type"),
    USER_NAME("username"),
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    WINDOWS_TOKEN("windows_token"),
    KERBEROS_REALM("kerberos_realm"),
    KERBEROS_TOKEN("kerberos_token"),
    ASSERTION("assertion"),
    VALIDITY_PERIOD("validity_period"),
    SCOPE("scope");


    private final String value;


    BodyParameterKey(String value) {
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
