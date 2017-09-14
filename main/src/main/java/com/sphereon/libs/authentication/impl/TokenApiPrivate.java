package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.impl.config.TokenApiConfigurationImpl;

public interface TokenApiPrivate {

    final class Builder {

        TokenApiConfigurationImpl tokenApiConfiguration;


        public Builder() {
            tokenApiConfiguration = new TokenApiConfigurationImpl();
            tokenApiConfiguration.setPersistenceType(PersistenceType.DISABLED);
            tokenApiConfiguration.setPersistenceMode(PersistenceMode.READ_ONLY);
        }


        public Builder withApplication(String application) {
            tokenApiConfiguration.setApplication(application);
            return this;
        }


        public Builder withPersistenceType(PersistenceType persistenceType) {
            tokenApiConfiguration.setPersistenceType(persistenceType);
            return this;
        }


        public Builder withPersistenceMode(PersistenceMode persistenceMode) {
            tokenApiConfiguration.setPersistenceMode(persistenceMode);
            return this;
        }


        public Builder setStandaloneConfigPath(String standaloneConfigPath) {
            tokenApiConfiguration.setStandalonePropertyFilePath(standaloneConfigPath);
            return this;
        }

        public Builder withDefaultGrant(Grant grant) {
            tokenApiConfiguration.setDefaultGrant(grant);
            return this;
        }



        public TokenApi build() {
            validate();
            return new TokenApiImpl(tokenApiConfiguration);
        }


        private void validate() {
            // TODO
        }
    }
}
