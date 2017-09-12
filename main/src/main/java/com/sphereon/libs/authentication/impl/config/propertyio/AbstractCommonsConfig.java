package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

abstract class AbstractCommonsConfig implements PropertyConfig {
    protected final PersistenceMode persistenceMode;
    protected Configuration config;


    protected AbstractCommonsConfig(PersistenceMode persistenceMode) {
        this.persistenceMode = persistenceMode;
    }


    @Override
    public String readProperty(PropertyKey key, String defaultValue) {
        String value = config.getString(key.getPropertyKey());
        if (StringUtils.isEmpty(value)) {
            if (StringUtils.isNotBlank(defaultValue)) {
                saveProperty(key, defaultValue);
            }
            return defaultValue;
        }
        return value;
    }


    @Override
    public void saveProperty(PropertyKey key, String value) {
        if (persistenceMode == PersistenceMode.READ_WRITE) {
            config.setProperty(key.getPropertyKey(), value);
        }
    }
}
