package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface Saml2Builder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;

        private String assertion;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Saml2Builder.Builder withAssertion(String assertion) {
            this.assertion = assertion;
            return this;
        }


        public Grant build() {
            SAML2GrantImpl saml2Grant = new SAML2GrantImpl();
            saml2Grant.setAssertion(assertion);
            configManager.loadGrant(saml2Grant);
            return saml2Grant;
        }
    }
}
