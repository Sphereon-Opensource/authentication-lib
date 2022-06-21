/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.backends.InMemoryConfig;
import com.sphereon.libs.authentication.impl.config.backends.NoopPropertyBackend;
import com.sphereon.libs.authentication.impl.config.backends.PropertyConfigBackend;
import com.sphereon.libs.authentication.impl.config.backends.PropertyFileBackend;
import com.sphereon.libs.authentication.impl.config.backends.SystemEnvPropertyBackend;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ConfigManager {
    private static final String PROPERTY_PREFIX = "authentication-api";

    private final Log log = LogFactory.getLog(ConfigManager.class);

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
        if (StringUtils.isNotEmpty(configuration.getEnvVarPrefix())) {
            this.propertyPrefix = configuration.getEnvVarPrefix();
            if (!this.propertyPrefix.endsWith(".")) {
                this.propertyPrefix += '.';
            }
        } else if (StringUtils.isNotEmpty(configuration.getApplication())) {
            this.propertyPrefix = PROPERTY_PREFIX + '.' + configuration.getApplication() + '.';
        } else {
            this.propertyPrefix = PROPERTY_PREFIX + '.';
        }
        reinit = false;
    }


    private PropertyConfigBackend selectPropertyConfig() {
        log.info("Auth configuration is of type: " + configuration.getPersistenceType());
        switch (configuration.getPersistenceType()) {
            case DISABLED:
                return new NoopPropertyBackend();
            case STANDALONE_PROPERTY_FILE:
                return new PropertyFileBackend(configuration, configuration.getStandalonePropertyFile());
            case SPRING_APPLICATION_PROPERTIES:
                return createSpringPropertyBackend();
            case SYSTEM_ENVIRONMENT:
                return new SystemEnvPropertyBackend(configuration);
            default:
                return new InMemoryConfig(configuration);
        }
    }

    private URL url(final String location) {
        final File file = new File(location);
        if (!file.exists()) {
            return null;
        }
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private PropertyConfigBackend createSpringPropertyBackend() {
        URL url = null;
        String propertiesLocation = System.getProperty("spring.config.location");
        if (StringUtils.isNotEmpty(propertiesLocation)) {
            log.info("Using custom config location as specified by 'spring.config.location': " + propertiesLocation);
            url = url(propertiesLocation);
        } else {
            log.info("Searching default locations for config file...");
        }

        final String[] activeProfiles = StringUtils.split(System.getProperty("spring.profiles.active"));
        if (activeProfiles != null) {
            for (String profile : activeProfiles) {
                url = getUrlForProfile("-" + profile, url);
                if (url != null) {
                    break;
                }
            }
        }
        if (url == null) {
            url = url("config" + File.separator + "application.properties");
        }
        if (url == null) {
            url = url("./application.properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("/config/application.properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("application.properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("/application.properties");
        }
        if (url == null) {
            throw new RuntimeException("application.properties was not found in default locations nor on the classpath");
        }
        log.info("Config file location search resulted in: " + url);
        return new PropertyFileBackend(configuration, url);
    }

    private URL getUrlForProfile(String profile, URL url) {
        if (url == null) {
            url = url("config" + File.separator + "application.properties");
        }
        if (url == null) {
            url = url("./application" + profile + ".properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("/config/application" + profile + ".properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("application" + profile + ".properties");
        }
        if (url == null) {
            url = getClass().getClassLoader().getResource("/application" + profile + ".properties");
        }
        if (url == null) {
            throw new RuntimeException("application.properties was not found in the classpath");
        }
        if (url.getProtocol().startsWith("jar")) {
            configuration.setPersistenceMode(PersistenceMode.READ_ONLY);
        }
        return url;
    }


    public ApiConfiguration getConfiguration() {
        return configuration;
    }


    public void saveProperty(PropertyKey key,
                             String value) {
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


    public String readProperty(PropertyKey key,
                               String defaultValue) {
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
