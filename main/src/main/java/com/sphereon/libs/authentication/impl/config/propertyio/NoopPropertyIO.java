package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.impl.config.PropertyKey;

public class NoopPropertyIO implements PropertyConfig {
    @Override
    public String readProperty(PropertyKey key, String defaultValue) {
        return defaultValue;
    }


    @Override
    public void saveProperty(PropertyKey key, String value) {
    }
}
