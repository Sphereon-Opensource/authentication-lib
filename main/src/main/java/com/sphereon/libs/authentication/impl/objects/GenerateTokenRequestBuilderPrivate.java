package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialsBuilder;

import java.time.Duration;

public interface GenerateTokenRequestBuilderPrivate {

    class Builder implements TokenRequestBuilder {

        private final ConfigManager configManager;

        private String consumerKey;
        private String consumerSecret;
        private Grant grant;
        private String scope;
        private Duration validityPeriod;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withGrant(Grant grant) {
            this.grant = grant;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withValidityPeriod(Duration validityPeriod) {
            this.validityPeriod = validityPeriod;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            GenerateTokenRequestImpl tokenRequest = new GenerateTokenRequestImpl(grant);
            tokenRequest.setConsumerKey(consumerKey);
            tokenRequest.setConsumerSecret(consumerSecret);
            tokenRequest.setScope(scope);
            tokenRequest.setValidityPeriod(validityPeriod);
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
