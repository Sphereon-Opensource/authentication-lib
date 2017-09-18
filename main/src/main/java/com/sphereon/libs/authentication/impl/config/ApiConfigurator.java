package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;

import java.io.File;

public interface ApiConfigurator {

    final class Builder {

        final ApiConfigurationImpl configuration;


        public Builder() {
            this.configuration = new ApiConfigurationImpl();
        }


        public Builder withApplication(String applicationName) {
            configuration.setApplication(applicationName);
            return this;
        }


        public Builder withConsumerKey(String consumerKey) {
            configuration.setConsumerKey(consumerKey);
            return this;
        }


        public Builder withConsumerSecret(String consumerSecret) {
            configuration.setConsumerSecret(consumerSecret);
            return this;
        }


        public Builder withPersistenceType(PersistenceType persistenceType) {
            configuration.setPersistenceType(persistenceType);
            return this;
        }


        public Builder withPersistenceMode(PersistenceMode persistenceMode) {
            configuration.setPersistenceMode(persistenceMode);
            return this;
        }


        public Builder withAutoEncryptSecrets(boolean autoEncryptSecrets) {
            configuration.setAutoEncryptSecrets(autoEncryptSecrets);
            return this;
        }


        public Builder withAutoEncryptionPassword(String autoEncryptionPassword) {
            configuration.setAutoEncryptionPassword(autoEncryptionPassword);
            return this;
        }


        public Builder setStandaloneConfigFile(File standaloneConfigPath) {
            configuration.setStandalonePropertyFile(standaloneConfigPath);
            return this;
        }


        public Builder withDefaultGrant(Grant grant) {
            configuration.setDefaultGrant(grant);
            return this;
        }


        public Builder withGatewayBaseUrl(String gatewayBaseUrl) {
            configuration.setGatewayBaseUrl(gatewayBaseUrl);
            return this;
        }


        public ApiConfiguration build() {
            ConfigPersistence configPersistence = configuration;
            configPersistence.loadConfig(configuration.getConfigManager());
            configPersistence.persistConfig(configuration.getConfigManager());
            return configuration;
        }
    }

}
