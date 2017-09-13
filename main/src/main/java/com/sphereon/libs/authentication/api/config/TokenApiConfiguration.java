package com.sphereon.libs.authentication.api.config;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface TokenApiConfiguration {

    String getApplication();

    String getGatewayBaseUrl();

    void setGatewayBaseUrl(String gatewayBaseUrl);

    PersistenceType getPersistenceType();

    PersistenceMode getPersistenceMode();

    String getStandalonePropertyFilePath();

    Grant getDefaultGrant();

    void setDefaultGrant(Grant grant);

    final class Configurator {

        private ConfigManager configManager;

        final TokenApiConfiguration tokenApiConfiguration;


        public Configurator(ConfigManager configManager) {
            this.configManager = configManager;
            this.tokenApiConfiguration = configManager.getConfiguration();
        }


        public Configurator withGatewayBaseUrl(String gatewayBaseUrl) {
            tokenApiConfiguration.setGatewayBaseUrl(gatewayBaseUrl);
            return this;
        }


        public Configurator withDefaultGrant(Grant grant) {
            tokenApiConfiguration.setDefaultGrant(grant);
            return this;
        }


        public void persist() {
            configManager.persist();
        }
    }
}
