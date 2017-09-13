package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface RevokeTokenRequestBuilderPrivate {

    class Builder implements TokenRequestBuilder {

        private final ConfigManager configManager;

        private String consumerKey;

        private String consumerSecret;

        private String currentToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withCurrentToken() {
            this.currentToken = currentToken;
            return this;
        }


        @Override
        public TokenRequest build() {
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(configManager);
            revokeTokenRequest.setConsumerKey(consumerKey);
            revokeTokenRequest.setConsumerSecret(consumerSecret);
            revokeTokenRequest.setToken(currentToken);
            configManager.loadTokenRequest(revokeTokenRequest);
            return revokeTokenRequest;
        }
    }
}
