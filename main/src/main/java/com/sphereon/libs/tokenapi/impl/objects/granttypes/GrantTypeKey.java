package com.sphereon.libs.tokenapi.impl.objects.granttypes;

public enum GrantTypeKey {

    KERBEROS("kerberos"), CLIENT_CREDENTIALS("client_credentials"), NTLM("iwa:ntlm"), PASSWORD("password"), REFRESH_TOKEN("refresh_token"),
    SAML2("urn:ietf:params:oauth:grant-type:saml2-bearer");

    private final String value;


    GrantTypeKey(String value) {
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
