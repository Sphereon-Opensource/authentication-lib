package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface Saml2Builder {

    final class Builder implements GrantBuilder {

        private String assertion;


        public Saml2Builder.Builder withAssertion(String assertion) {
            this.assertion = assertion;
            return this;
        }


        public Grant build() {
            SAML2GrantImpl saml2Grant = new SAML2GrantImpl();
            saml2Grant.setAssertion(assertion);
            return saml2Grant;
        }
    }
}
