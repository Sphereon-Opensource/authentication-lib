package com.sphereon.libs.authentication.api.granttypes;

public interface SAML2Grant extends Grant {

    String getAssertion();

    void setAssertion(String assertion);

}
