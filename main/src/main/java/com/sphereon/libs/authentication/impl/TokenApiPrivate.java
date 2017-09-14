package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;

public interface TokenApiPrivate {

    final class Builder {

        private TokenApiConfiguration tokenApiConfiguration;


        public Builder() {
        }


        public Builder withConfiguration(TokenApiConfiguration tokenApiConfiguration) {
            this.tokenApiConfiguration = tokenApiConfiguration;
            return this;
        }


        public TokenApi build() {
            if (tokenApiConfiguration == null) {
                tokenApiConfiguration = new TokenApiConfiguration.Builder()
                        .withPersistenceType(PersistenceType.IN_MEMORY)
                        .withPersistenceMode(PersistenceMode.READ_WRITE).build();
            }

            return new TokenApiImpl(tokenApiConfiguration);
        }
    }
}
