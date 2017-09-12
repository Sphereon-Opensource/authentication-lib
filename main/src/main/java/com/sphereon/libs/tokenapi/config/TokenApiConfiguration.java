package com.sphereon.libs.tokenapi.config;

import com.sphereon.libs.tokenapi.impl.config.TokenApiConfigurationImpl;

public interface TokenApiConfiguration {

    String getApplication();

    String getGatewayBaseUrl();

    void setGatewayBaseUrl(String gatewayBaseUrl);

    PersistenceType getPersistenceType();

    PersistenceMode getPersistenceMode();

    String getStandalonePropertyFilePath();

    TokenApiConfigurationImpl setStandalonePropertyFilePath(String standalonePropertyFilePath);

    void setPersistenceType(PersistenceType persistenceType);

    void setPersistenceMode(PersistenceMode persistenceMode);
}
