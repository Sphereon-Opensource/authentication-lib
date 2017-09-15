package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.backends.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigManager {
    private static final String PROPERTY_PREFIX = "authentication-api";

    private final ApiConfiguration configuration;

    private String propertyPrefix;

    private PropertyConfigBackend propertyConfig;

    private boolean reinit;


    ConfigManager(ApiConfiguration configuration) {
        this.configuration = configuration;
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
                return new PropertyFileBackend(configuration.getApplication(), configuration.getPersistenceMode(), propertiesFile.toURI());
            case SPRING_APPLICATION_PROPERTIES:
                return createSpringPropertyBackend();
            case SYSTEM_ENVIRONMENT:
                return new SystemEnvPropertyBackend(configuration.getApplication());
            default:
                return new InMemoryConfig(configuration.getApplication());
        }
    }


    private PropertyConfigBackend createSpringPropertyBackend() {
        // FIXME: This is not sufficient:

        URL url = null;
        String propertiesLocation = System.getProperty("spring.config.location");
        if (StringUtils.isNotEmpty(propertiesLocation)) {
            try {
                url = new File(propertiesLocation).toURI().toURL();
            } catch (MalformedURLException e) {
            }
        }
        if (url == null) {
            try {
                url = new File("config" + File.separator + "application.properties").toURI().toURL();
            } catch (MalformedURLException e) {
            }
        }
        if (url == null) {
            try {
                url = new File("application.properties").toURI().toURL();
            } catch (MalformedURLException e) {
            }
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("application.properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("/application.properties");
        }
        if (url == null) {
            throw new RuntimeException("application.properties was not found in the classpath");
        }
        PersistenceMode persistenceMode = configuration.getPersistenceMode();
        if (url.getProtocol().startsWith("jar")) {
            persistenceMode = PersistenceMode.READ_ONLY;
        }
        try {
            return new PropertyFileBackend(configuration.getApplication(), persistenceMode, url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not read property file URL " + url, e);
        }
    }


    public ApiConfiguration getConfiguration() {
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
