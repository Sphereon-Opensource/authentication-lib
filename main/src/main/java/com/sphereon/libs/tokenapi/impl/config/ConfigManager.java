package com.sphereon.libs.tokenapi.impl.config;

import com.sphereon.libs.tokenapi.TokenRequest;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ConfigManager {
    private static final String GATEWAY_URL = "https://gw.api.cloud.sphereon.com/";

    private TokenApiConfiguration configuration;

    private static final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();


    public ConfigManager(TokenApiConfiguration tokenApiConfiguration) {
        this.configuration = tokenApiConfiguration;
        if (StringUtils.isEmpty(configuration.getGatewayBaseUrl())) {
            configuration.setGatewayBaseUrl(readProperty(PropertyKey.GATEWAY_BASE_URL, GATEWAY_URL));
        }
    }


    public TokenApiConfiguration getConfiguration() {
        return configuration;
    }


    public void saveProperty(PropertyKey key, String value) {

    }


    public String readProperty(PropertyKey key) {

        return readProperty(key, null);
    }


    public String readProperty(PropertyKey key, String defaultValue) {
        return defaultValue;
    }


    public void loadTokenRequest(ConfigPersistence configPersistence) {

    }


    public void loadGrant(Grant grant) {
        if (grant instanceof ConfigPersistence) {
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(this);
        }
    }


    public void persist() {
        saveProperty(PropertyKey.GATEWAY_BASE_URL, configuration.getGatewayBaseUrl());
    }


    public void persist(TokenRequest tokenRequest) {
        if (tokenRequest instanceof ConfigPersistence) {
            ConfigPersistence configPersistence = (ConfigPersistence) tokenRequest;
            configPersistence.persistConfig(this);
        }
    }
}
