package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;

public class TokenApiConfigurationImpl implements TokenApiConfiguration {

    private final String application;

    private PersistenceType persistenceType;

    private PersistenceMode persistenceMode;

    private String gatewayBaseUrl;

    private String standalonePropertyFilePath;


    public TokenApiConfigurationImpl(String application) {
        this.application = application;
    }


    @Override
    public String getApplication() {
        return application;
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
    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }


    @Override
    public PersistenceMode getPersistenceMode() {
        return persistenceMode;
    }


    @Override
    public void setPersistenceMode(PersistenceMode persistenceMode) {
        this.persistenceMode = persistenceMode;
    }


    @Override
    public String getStandalonePropertyFilePath() {
        return standalonePropertyFilePath;
    }


    @Override
    public TokenApiConfigurationImpl setStandalonePropertyFilePath(String standalonePropertyFilePath) {
        this.standalonePropertyFilePath = standalonePropertyFilePath;
        return this;
    }


}
