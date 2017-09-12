package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.propertyio.*;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigManager {
    private static final String GATEWAY_URL = "https://gw.api.cloud.sphereon.com/";

    private TokenApiConfiguration configuration;

    private static final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    private PropertyConfig propertyIO;


    public ConfigManager(TokenApiConfiguration tokenApiConfiguration) {
        this.configuration = tokenApiConfiguration;
        if (StringUtils.isEmpty(configuration.getGatewayBaseUrl())) {
            configuration.setGatewayBaseUrl(readProperty(PropertyKey.GATEWAY_BASE_URL, GATEWAY_URL));
        }
        propertyIO = selectPropertyIO();
    }


    private PropertyConfig selectPropertyIO() {
        switch (configuration.getPersistenceType()) {
            case DISABLED:
                return new NoopPropertyIO();
            case STANDALONE_PROPERTY_FILE:
                File propertiesFile = new File(configuration.getStandalonePropertyFilePath());
                return new PropertyFileIO(configuration.getPersistenceMode(), propertiesFile.toURI());
            case APPLICATION_PROPERTIES:
                return createPropertyFileIoFromSpringConfig();
            case SYSTEM_ENVIRONMENT:
                return new SystemEnvPropertyIO(configuration.getPersistenceMode());
            default:
                return new InMemoryConfig();
        }
    }


    private PropertyConfig createPropertyFileIoFromSpringConfig() {
        URL url = getClass().getClassLoader().getResource("/application.properties");
        if (url == null) {
            throw new RuntimeException("application.properties was not found in the classpath");
        }
        PersistenceMode persistenceMode = configuration.getPersistenceMode();
        if (url.getProtocol().startsWith("jar")) {
            persistenceMode = PersistenceMode.READ_ONLY;
        }
        try {
            return new PropertyFileIO(persistenceMode, url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not read property file URL " + url, e);
        }
    }


    public TokenApiConfiguration getConfiguration() {
        return configuration;
    }


    public void saveProperty(PropertyKey key, String value) {
        propertyIO.saveProperty(key, value);
    }


    public String readProperty(PropertyKey key) {
        return propertyIO.readProperty(key, null);
    }


    public String readProperty(PropertyKey key, String defaultValue) {
        return propertyIO.readProperty(key, defaultValue);
    }


    public void loadTokenRequest(ConfigPersistence configPersistence) {
        configPersistence.loadConfig(this);
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
