package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import org.apache.commons.lang3.StringUtils;

public interface RevokeTokenRequestBuilderPrivate {

    class Builder implements TokenRequestBuilder {

        private final TokenApiConfiguration tokenApiConfiguration;

        private String consumerKey;

        private String consumerSecret;

        private String currentToken;


        public Builder(TokenApiConfiguration tokenApiConfiguration) {
            this.tokenApiConfiguration = tokenApiConfiguration;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withCurrentToken() {
            this.currentToken = currentToken;
            return this;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public RevokeTokenRequestBuilderPrivate.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(tokenApiConfiguration);
            revokeTokenRequest.setConsumerKey(consumerKey);
            revokeTokenRequest.setConsumerSecret(consumerSecret);
            revokeTokenRequest.setToken(currentToken);
            return revokeTokenRequest;
        }


        private void validate() {
            if (StringUtils.isEmpty(consumerKey)) {
                this.consumerKey = tokenApiConfiguration.getConsumerKey();
            }
            if (StringUtils.isEmpty(consumerSecret)) {
                this.consumerSecret = tokenApiConfiguration.getConsumerSecret();
            }

            if (StringUtils.isEmpty(currentToken)) {
                throw new NullPointerException("RevokeTokenRequest.setToken() is not set");
            }
        }
    }
}