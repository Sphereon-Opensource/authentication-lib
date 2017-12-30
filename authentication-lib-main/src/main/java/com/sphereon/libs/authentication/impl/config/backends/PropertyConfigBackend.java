package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.impl.config.PropertyKey;

public interface PropertyConfigBackend {

    String readProperty(String propertyPrefix, PropertyKey key, String defaultValue);

    void saveProperty(String propertyPrefix, PropertyKey key, String value);
}
