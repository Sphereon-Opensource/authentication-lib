package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum GrantTypeKey implements StringValueEnum {

    KERBEROS("kerberos"), CLIENT_CREDENTIALS("client_credentials"), NTLM("iwa:ntlm"), PASSWORD("password"), REFRESH_TOKEN("refresh_token"),
    SAML2("urn:ietf:params:oauth:grant-type:saml2-bearer");

    private final String value;


    GrantTypeKey(String value) {
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
