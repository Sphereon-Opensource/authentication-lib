package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;

public interface TokenApiConfigurator {

    final class Builder {

        final TokenApiConfigurationImpl tokenApiConfiguration;


        public Builder() {
            this.tokenApiConfiguration = new TokenApiConfigurationImpl();
        }


        public Builder withApplication(String applicationName) {
            tokenApiConfiguration.setApplication(applicationName);
            return this;
        }


        public Builder withConsumerKey(String consumerKey) {
            tokenApiConfiguration.setConsumerKey(consumerKey);
            return this;
        }


        public Builder withConsumerSecret(String consumerSecret) {
            tokenApiConfiguration.setConsumerSecret(consumerSecret);
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


        public Builder withGatewayBaseUrl(String gatewayBaseUrl) {
            tokenApiConfiguration.setGatewayBaseUrl(gatewayBaseUrl);
            return this;
        }

        public TokenApiConfiguration build() {
            ConfigPersistence configPersistence = tokenApiConfiguration;
            configPersistence.loadConfig(tokenApiConfiguration.getConfigManager());
            configPersistence.persistConfig(tokenApiConfiguration.getConfigManager());
            return tokenApiConfiguration;
        }
    }

}
