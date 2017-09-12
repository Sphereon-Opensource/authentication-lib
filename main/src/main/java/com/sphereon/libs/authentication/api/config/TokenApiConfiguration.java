package com.sphereon.libs.authentication.api.config;

import com.sphereon.libs.authentication.impl.config.TokenApiConfigurationImpl;

public interface TokenApiConfiguration {

    String getApplication();

    void setApplication(String application);

    String getGatewayBaseUrl();

    void setGatewayBaseUrl(String gatewayBaseUrl);

    PersistenceType getPersistenceType();

    PersistenceMode getPersistenceMode();

    String getStandalonePropertyFilePath();

    TokenApiConfigurationImpl setStandalonePropertyFilePath(String standalonePropertyFilePath);

    void setPersistenceType(PersistenceType persistenceType);

    void setPersistenceMode(PersistenceMode persistenceMode);
}
