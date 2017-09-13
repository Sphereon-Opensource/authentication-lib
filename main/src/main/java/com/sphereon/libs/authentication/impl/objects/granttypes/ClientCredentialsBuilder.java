package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface ClientCredentialsBuilder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Grant build() {
            ClientCredentialsGrantImpl clientCredentialsGrant = new ClientCredentialsGrantImpl();
            configManager.loadGrant(clientCredentialsGrant);
            return clientCredentialsGrant;
        }
    }
}
