package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;

public class TokenApiConfigurationImpl implements TokenApiConfiguration {

    private String application = "default-application";

    private PersistenceType persistenceType;

    private PersistenceMode persistenceMode;

    private String gatewayBaseUrl;

    private String standalonePropertyFilePath;

    private Grant defaultGrant;


    @Override
    public String getApplication() {
        return application;
    }


    public void setApplication(String application) {
        this.application = application;
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


    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }


    @Override
    public PersistenceMode getPersistenceMode() {
        return persistenceMode;
    }


    public void setPersistenceMode(PersistenceMode persistenceMode) {
        this.persistenceMode = persistenceMode;
    }


    @Override
    public String getStandalonePropertyFilePath() {
        return standalonePropertyFilePath;
    }


    @Override
    public Grant getDefaultGrant() {
        return defaultGrant;
    }

    @Override
    public void setDefaultGrant(Grant grant) {
        this.defaultGrant = grant;
    }


    public void setStandalonePropertyFilePath(String standalonePropertyFilePath) {
        this.standalonePropertyFilePath = standalonePropertyFilePath;
    }
}
