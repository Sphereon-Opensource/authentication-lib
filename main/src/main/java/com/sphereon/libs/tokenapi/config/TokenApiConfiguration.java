package com.sphereon.libs.tokenapi.config;

public interface TokenApiConfiguration {

    String getGatewayBaseUrl();

    void setGatewayBaseUrl(String gatewayBaseUrl);

    PersistenceType getPersistenceType();

    PersistenceMode getPersistenceMode();

    String getStandalonePropertyFilePath();

    void setStandalonePropertyFilePath(String standalonePropertyFilePath);

}
