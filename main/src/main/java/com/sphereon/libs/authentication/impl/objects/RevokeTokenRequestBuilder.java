package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import org.apache.commons.lang3.StringUtils;

public interface RevokeTokenRequestBuilder {

    class Builder implements TokenRequestBuilder {

        private final ApiConfiguration configuration;

        private String consumerKey;

        private String consumerSecret;

        private String currentToken;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public RevokeTokenRequestBuilder.Builder withCurrentToken(String currentToken) {
            this.currentToken = currentToken;
            return this;
        }


        public RevokeTokenRequestBuilder.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public RevokeTokenRequestBuilder.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(configuration);
            revokeTokenRequest.setConsumerKey(consumerKey);
            revokeTokenRequest.setConsumerSecret(consumerSecret);
            revokeTokenRequest.setToken(currentToken);
            return revokeTokenRequest;
        }


        private void validate() {
            if (StringUtils.isEmpty(consumerKey)) {
                this.consumerKey = configuration.getConsumerKey();
            }
            if (StringUtils.isEmpty(consumerSecret)) {
                this.consumerSecret = configuration.getConsumerSecret();
            }

            if (StringUtils.isEmpty(currentToken)) {
                throw new NullPointerException("RevokeTokenRequest.setToken() is not set");
            }
        }
    }
}