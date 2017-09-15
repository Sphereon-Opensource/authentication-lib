package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.lang3.StringUtils;

public class SystemEnvPropertyBackend extends InMemoryConfig {

    public SystemEnvPropertyBackend(String application) {
        super(application);
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {

        String value = System.getenv(propertyPrefix + key.getPropertyKey());
        if (StringUtils.isEmpty(value)) {
            value = System.getProperty(propertyPrefix + key.getPropertyKey());
        }
        if (StringUtils.isEmpty(value)) {
            if (persistenceMode == PersistenceMode.READ_WRITE && StringUtils.isNotBlank(defaultValue)) {
                saveProperty(propertyPrefix, key, defaultValue);
            }
            return super.readProperty(propertyPrefix, key, defaultValue);
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (persistenceMode == PersistenceMode.READ_WRITE) {
            if (key.isEncrypt()) {
                // TODO: write encrypted version to log?
            }
        }
        super.saveProperty(propertyPrefix, key, value);
    }
}