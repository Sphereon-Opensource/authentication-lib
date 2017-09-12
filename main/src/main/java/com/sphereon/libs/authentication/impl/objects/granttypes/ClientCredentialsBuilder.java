package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface ClientCredentialsBuilder extends Grant {

    final class Builder implements GrantBuilder<ClientCredentialsGrant> {

        private final ConfigManager configManager;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public ClientCredentialsGrant build() {
            ClientCredentialsGrant clientCredentialsGrant = new ClientCredentialsGrantImpl();
            configManager.loadGrant(clientCredentialsGrant);
            return clientCredentialsGrant;
        }
    }
}
