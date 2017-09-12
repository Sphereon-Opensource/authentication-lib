package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.Saml2GrantBuilder;

public interface SAML2Grant extends Saml2GrantBuilder {

    void setAssertion(String assertion);
}
