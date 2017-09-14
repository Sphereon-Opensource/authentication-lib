package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.backends.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigManager {
    private static final String PROPERTY_PREFIX = "sphereon.authentication-api";

    private final TokenApiConfiguration configuration;

    private String propertyPrefix;

    private PropertyConfigBackend propertyConfig;

    private boolean reinit;


    public ConfigManager(TokenApiConfiguration tokenApiConfiguration) {
        this.configuration = tokenApiConfiguration;
        init();
    }


    private void init() {
        this.propertyConfig = selectPropertyConfig();
        this.propertyPrefix = PROPERTY_PREFIX + '.' + this.configuration.getApplication() + '.';
        reinit = false;
    }


    private PropertyConfigBackend selectPropertyConfig() {
        switch (configuration.getPersistenceType()) {
            case DISABLED:
                return new NoopPropertyBackend();
            case STANDALONE_PROPERTY_FILE:
                File propertiesFile = new File(configuration.getStandalonePropertyFilePath());
                return new PropertyFileBackend(configuration.getPersistenceMode(), propertiesFile.toURI(), configuration.getApplication());
            case APPLICATION_PROPERTIES:
                return createPropertyFileIoFromSpringConfig();
            case SYSTEM_ENVIRONMENT:
                return new SystemEnvPropertyBackend(configuration.getPersistenceMode());
            default:
                return new InMemoryConfig(configuration.getPersistenceMode());
        }
    }


    private PropertyConfigBackend createPropertyFileIoFromSpringConfig() {
        URL url = getClass().getClassLoader().getResource("/application.properties");
        if (url == null) {
            throw new RuntimeException("application.properties was not found in the classpath");
        }
        PersistenceMode persistenceMode = configuration.getPersistenceMode();
        if (url.getProtocol().startsWith("jar")) {
            persistenceMode = PersistenceMode.READ_ONLY;
        }
        try {
            return new PropertyFileBackend(persistenceMode, url.toURI(), configuration.getApplication());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not read property file URL " + url, e);
        }
    }


    public TokenApiConfiguration getConfiguration() {
        return configuration;
    }


    public void saveProperty(PropertyKey key, String value) {
        if (reinit) {
            init();
        }
        propertyConfig.saveProperty(this.propertyPrefix, key, value);
    }


    public String readProperty(PropertyKey key) {
        if (reinit) {
            init();
        }
        return this.propertyConfig.readProperty(this.propertyPrefix, key, null);
    }


    public String readProperty(PropertyKey key, String defaultValue) {
        if (reinit) {
            init();
        }
        return this.propertyConfig.readProperty(this.propertyPrefix, key, defaultValue);
    }


    public void loadTokenRequest(ConfigPersistence configPersistence) {
        if (reinit) {
            init();
        }
        configPersistence.loadConfig(this);
    }


    public void loadGrant(Grant grant) {
        if (reinit) {
            init();
        }
        if (grant instanceof ConfigPersistence) {
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(this);
        }
    }


    public void setReinit() {
        this.reinit = true;
    }
}
