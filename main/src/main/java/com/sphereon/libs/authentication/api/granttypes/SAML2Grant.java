package com.sphereon.libs.authentication.api.granttypes;

public interface SAML2Grant extends Grant {

    void setAssertion(String assertion);
}
