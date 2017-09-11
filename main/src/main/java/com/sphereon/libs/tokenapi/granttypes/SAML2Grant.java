package com.sphereon.libs.tokenapi.granttypes;

public interface SAML2Grant extends Grant {
    String getAssertion();

    void setAssertion(String assertion);
}
