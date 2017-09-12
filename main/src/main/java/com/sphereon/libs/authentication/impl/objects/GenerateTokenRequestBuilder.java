package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.GenerateTokenRequest;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialsBuilder;

public interface GenerateTokenRequestBuilder extends TokenRequest {

    class Builder implements TokenRequestBuilder<GenerateTokenRequest> {

        private final ConfigManager configManager;

        private Grant grant;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public GenerateTokenRequestBuilder.Builder withGrant(Grant grant) {
            this.grant = grant;
            return this;
        }


        @Override
        public GenerateTokenRequest build() {
            validate();
            GenerateTokenRequestImpl tokenRequest = new GenerateTokenRequestImpl(grant);
            configManager.loadTokenRequest(tokenRequest);
            return tokenRequest;
        }


        private void validate() {
            if (grant == null) {
                grant = new ClientCredentialsBuilder.Builder(configManager).build();
            }
        }
    }
}
