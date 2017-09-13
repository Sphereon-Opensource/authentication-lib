package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

public interface TokenRequestBuilder {

    TokenRequest build();


    final class Builder {

        private final ConfigManager configManager;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public GenerateTokenRequestBuilder.Builder generateTokenRequestBuilder() {
            return new GenerateTokenRequestBuilderPrivate.Builder(configManager);
        }


        public RevokeTokenRequestBuilder.Builder revokeTokenRequestBuilder() {
            return new RevokeTokenRequestBuilderPrivate.Builder(configManager);
        }

    }


}
