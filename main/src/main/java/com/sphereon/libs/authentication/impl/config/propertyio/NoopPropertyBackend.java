package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.impl.config.PropertyKey;

public class NoopPropertyBackend implements PropertyConfigBackend {

    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        return defaultValue;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
    }
}
