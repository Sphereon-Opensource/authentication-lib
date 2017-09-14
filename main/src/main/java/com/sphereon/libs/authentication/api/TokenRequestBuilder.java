package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

public interface TokenRequestBuilder {

    TokenRequest build();


    final class Builder {

        private final TokenApiConfiguration tokenApiConfiguration;


        public Builder(TokenApiConfiguration tokenApiConfiguration) {
            this.tokenApiConfiguration = tokenApiConfiguration;
        }


        public GenerateTokenRequestBuilder.Builder generateTokenRequestBuilder() {
            return new GenerateTokenRequestBuilderPrivate.Builder(tokenApiConfiguration);
        }


        public RevokeTokenRequestBuilder.Builder revokeTokenRequestBuilder() {
            return new RevokeTokenRequestBuilderPrivate.Builder(tokenApiConfiguration);
        }
    }
}
