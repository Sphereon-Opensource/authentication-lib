package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

abstract class AbstractCommonsConfig implements PropertyConfigBackend {

    protected final PersistenceMode persistenceMode;

    protected Configuration config;


    protected AbstractCommonsConfig(PersistenceMode persistenceMode) {
        this.persistenceMode = persistenceMode;
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        String value = config.getString(propertyPrefix + key.getPropertyKey());
        if (StringUtils.isEmpty(value)) {
            if (StringUtils.isNotBlank(defaultValue)) {
                saveProperty(propertyPrefix, key, defaultValue);
            }
            return defaultValue;
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (persistenceMode == PersistenceMode.READ_WRITE || (!key.isCheckReadOnly() && persistenceMode == PersistenceMode.READ_ONLY)) {
            config.setProperty(propertyPrefix + key.getPropertyKey(), value);
        }
    }
}
