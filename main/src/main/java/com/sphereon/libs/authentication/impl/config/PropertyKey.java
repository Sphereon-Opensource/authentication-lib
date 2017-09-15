package com.sphereon.libs.authentication.impl.config;

public enum PropertyKey {

    GATEWAY_BASE_URL("gateway-base-url", false),
    PERSISTENCE_MODE("persistence-mode", false),
    USER_NAME("username", false),
    PASSWORD("password", true),
    CONSUMER_KEY("consumer-key", false),
    CONSUMER_SECRET("consumer-secret", true),
    SCOPE("scope", false),
    VALIDITY_PERIOD("validity-period", false),
    KERBEROS_REALM("kerberos-realm", true),
    KERBEROS_TOKEN("kerberos-token", true),
    CURRENT_TOKEN("current-token", true),
    WINDOWS_TOKEN("windows-token", true),
    REFRESH_TOKEN("refresh-token", true),
    SAML2_ASSERTION("saml2-assertions", true),
    GRANT_TYPE("grant-type", false);


    private final String propertyKey;
    private final boolean encrypt;


    PropertyKey(String value, boolean encrypt) {
        this.propertyKey = value;
        this.encrypt = encrypt;
    }


    public String getPropertyKey() {
        return propertyKey;
    }


    public boolean isEncrypt() {
        return encrypt;
    }


    @Override
    public String toString() {
        return getPropertyKey();
    }
}
