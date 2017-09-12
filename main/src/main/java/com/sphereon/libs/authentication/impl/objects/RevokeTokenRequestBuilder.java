package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.RevokeTokenRequest;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface RevokeTokenRequestBuilder extends TokenRequest {

    class Builder implements TokenRequestBuilder<RevokeTokenRequest> {

        private final ConfigManager configManager;

        private String currentToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withCurrentToken() {
            this.currentToken = currentToken;
            return this;
        }


        @Override
        public RevokeTokenRequest build() {
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl();
            revokeTokenRequest.setToken(currentToken);
            configManager.loadTokenRequest(revokeTokenRequest);
            return revokeTokenRequest;
        }
    }
}
