package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;

public interface AuthenticationApiBuilder {

    final class Builder {

        private ApiConfiguration configuration;


        public static final Builder newInstance() {
            return new Builder();
        }


        public Builder() {
        }


        public Builder withConfiguration(ApiConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }


        public AuthenticationApi build() {
            if (configuration == null) {
                configuration = new ApiConfiguration.Builder()
                        .withPersistenceType(PersistenceType.IN_MEMORY)
                        .withPersistenceMode(PersistenceMode.READ_WRITE).build();
            }

            return new AuthenticationApiImpl(configuration);
        }
    }
}
