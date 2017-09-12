package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.SAML2Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface Saml2GrantBuilder extends Grant {

    final class Builder implements GrantBuilder<SAML2Grant> {

        private final ConfigManager configManager;

        private String assertion;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withAssertion(String assertion) {
            this.assertion = assertion;
            return this;
        }


        public SAML2Grant build() {
            SAML2GrantImpl saml2Grant = new SAML2GrantImpl();
            saml2Grant.setAssertion(assertion);
            configManager.loadGrant(saml2Grant);
            return saml2Grant;
        }
    }
}
