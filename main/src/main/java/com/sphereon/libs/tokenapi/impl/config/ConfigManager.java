package com.sphereon.libs.tokenapi.impl.config;

import com.sphereon.libs.tokenapi.TokenRequest;
import com.sphereon.libs.tokenapi.config.PersistenceMode;
import com.sphereon.libs.tokenapi.config.PersistenceType;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ConfigManager {
    private static final String GATEWAY_URL = "https://gw.api.cloud.sphereon.com/";

    private static final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();


    public TokenApiConfiguration loadConfig(PersistenceType persistenceType, PersistenceMode persistenceMode) {
        TokenApiConfiguration configuration = new TokenApiConfigurationImpl(persistenceType, persistenceMode);
        configuration.setGatewayBaseUrl(readProperty(configuration, PropertyKey.GATEWAY_BASE_URL, GATEWAY_URL));
        return configuration;
    }


    public void saveProperty(TokenApiConfiguration tokenApiConfiguration, PropertyKey key, String value) {

    }


    public String readProperty(TokenApiConfiguration tokenApiConfiguration, PropertyKey key) {

        return readProperty(tokenApiConfiguration, key, null);
    }


    public String readProperty(TokenApiConfiguration tokenApiConfiguration, PropertyKey key, String defaultValue) {
        return defaultValue;
    }


    public void loadTokenRequest(TokenApiConfiguration tokenApiConfiguration, ConfigPersistence configPersistence) {

    }


    public void loadGrant(String applicationName, TokenApiConfiguration tokenApiConfiguration, Grant grant) {
        if (grant instanceof ConfigPersistence) {
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(tokenApiConfiguration, this);
        }
    }


    public void persist(TokenApiConfiguration tokenApiConfiguration) {
        saveProperty(tokenApiConfiguration, PropertyKey.GATEWAY_BASE_URL, tokenApiConfiguration.getGatewayBaseUrl());
    }


    public void persist(TokenApiConfiguration tokenApiConfiguration, TokenRequest tokenRequest) {
        if (tokenRequest instanceof ConfigPersistence) {
            ConfigPersistence configPersistence = (ConfigPersistence) tokenRequest;
            configPersistence.persistConfig(tokenApiConfiguration, this);
        }
    }
}
