package com.sphereon.libs.authentication.impl.config;

public enum PropertyKey {

    GATEWAY_BASE_URL("gateway-base-url", false, false),
    USER_NAME("username", true, true),
    PASSWORD("password", true, true),
    CONSUMER_KEY("consumer-key", true, true),
    CONSUMER_SECRET("consumer-secret", true, true),
    SCOPE("scope", false, true),
    VALIDITY_PERIOD("validity-period", false, true),
    KERBEROS_REALM("kerberos-realm", true, true),
    KERBEROS_TOKEN("kerberos-token", true, true),
    CURRENT_TOKEN("current-token", true, false),
    WINDOWS_TOKEN("windows-token", true, true),
    REFRESH_TOKEN("refresh-token", true, true),
    SAML2_ASSERTION("saml2-assertion", true, true);


    private final String propertyKey;
    private final boolean encrypt;
    private final boolean checkReadOnly;


    PropertyKey(String value, boolean encrypt, boolean checkReadOnly) {
        this.propertyKey = value;
        this.encrypt = encrypt;
        this.checkReadOnly = checkReadOnly;
    }


    public String getPropertyKey() {
        return propertyKey;
    }


    public boolean isEncrypt() {
        return encrypt;
    }


    public boolean isCheckReadOnly() {
        return checkReadOnly;
    }


    @Override
    public String toString() {
        return getPropertyKey();
    }
}
