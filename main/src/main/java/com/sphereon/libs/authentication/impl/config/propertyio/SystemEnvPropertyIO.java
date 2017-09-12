package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.lang3.StringUtils;

public class SystemEnvPropertyIO extends InMemoryConfig {

    public SystemEnvPropertyIO(PersistenceMode persistenceMode) {
        super(persistenceMode);
    }


    @Override
    public String readProperty(PropertyKey key, String defaultValue) {
        String value = System.getenv(key.getPropertyKey());
        if (StringUtils.isEmpty(value)) {
            value = System.getProperty(key.getPropertyKey());
        }
        if (StringUtils.isEmpty(value)) {
            if (persistenceMode == PersistenceMode.READ_WRITE && StringUtils.isNotBlank(defaultValue)) {
                saveProperty(key, defaultValue);
            }
            return super.readProperty(key, defaultValue);
        }
        return value;
    }


    @Override
    public void saveProperty(PropertyKey key, String value) {
        if (persistenceMode == PersistenceMode.READ_WRITE) {
            if (key.isEncrypt()) {
                // TODO: write encrypted version to log?
            }
        }
        super.saveProperty(key, value);
    }
}
