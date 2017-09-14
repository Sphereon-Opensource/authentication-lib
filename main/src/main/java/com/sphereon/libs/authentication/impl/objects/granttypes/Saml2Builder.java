package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.assertions.Assert;
import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.SAML2Grant;

public interface Saml2Builder {

    final class Saml2GrantBuilder implements GrantBuilder {

        private String assertion;

        public static Saml2GrantBuilder newInstance() {
            return new Saml2Builder.Saml2GrantBuilder();
        }


        public Saml2GrantBuilder withAssertion(String assertion) {
            this.assertion = assertion;
            return this;
        }


        public SAML2Grant build() {
            return build(true);
        }


        public SAML2Grant build(boolean validate) {
            if (validate) {
                validate();
            }
            SAML2GrantImpl saml2Grant = new SAML2GrantImpl();
            saml2Grant.setAssertion(assertion);
            return saml2Grant;
        }


        private void validate() {
            Assert.notNull(assertion, "Assertion is not set in Saml2GrantBuilder");
        }
    }
}
