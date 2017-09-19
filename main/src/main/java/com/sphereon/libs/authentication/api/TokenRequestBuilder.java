package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

public interface TokenRequestBuilder {

    TokenRequest build();


    final class Builder {

        private final ApiConfiguration configuration;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public GenerateTokenRequestBuilder.Builder generateTokenRequestBuilder() {
            return new GenerateTokenRequestBuilderPrivate.Builder(configuration);
        }


        public RevokeTokenRequestBuilder.Builder revokeTokenRequestBuilder() {
            return new RevokeTokenRequestBuilderPrivate.Builder(configuration);
        }
    }
}
