package com.sphereon.libs.tokenapi.impl.config;

import com.sphereon.libs.tokenapi.config.PersistenceMode;
import com.sphereon.libs.tokenapi.config.PersistenceType;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;

public class TokenApiConfigurationImpl implements TokenApiConfiguration {

    private final PersistenceType persistenceType;

    private final PersistenceMode persistenceMode;

    private String gatewayBaseUrl;

    private String standalonePropertyFilePath;


    public TokenApiConfigurationImpl(PersistenceType persistenceType, PersistenceMode persistenceMode) {
        this.persistenceType = persistenceType;
        this.persistenceMode = persistenceMode;
    }


    @Override
    public String getGatewayBaseUrl() {
        return gatewayBaseUrl;
    }


    @Override
    public void setGatewayBaseUrl(String gatewayBaseUrl) {
        this.gatewayBaseUrl = gatewayBaseUrl;
    }


    @Override
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }


    @Override
    public PersistenceMode getPersistenceMode() {
        return persistenceMode;
    }


    @Override
    public String getStandalonePropertyFilePath() {
        return standalonePropertyFilePath;
    }


    @Override
    public void setStandalonePropertyFilePath(String standalonePropertyFilePath) {
        this.standalonePropertyFilePath = standalonePropertyFilePath;
    }
}
