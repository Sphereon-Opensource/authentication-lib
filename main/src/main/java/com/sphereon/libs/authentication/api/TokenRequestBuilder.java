package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilder;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilder;

public interface TokenRequestBuilder {

    TokenRequest build();


    final class Builder {

        private final ApiConfiguration configuration;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public com.sphereon.libs.authentication.api.GenerateTokenRequestBuilder.Builder generateTokenRequestBuilder() {
            return new GenerateTokenRequestBuilder.Builder(configuration);
        }


        public com.sphereon.libs.authentication.api.RevokeTokenRequestBuilder.Builder revokeTokenRequestBuilder() {
            return new RevokeTokenRequestBuilder.Builder(configuration);
        }
    }
}
