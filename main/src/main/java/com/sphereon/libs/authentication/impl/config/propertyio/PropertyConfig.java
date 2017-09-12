package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.impl.config.PropertyKey;

public interface PropertyConfig {

    String readProperty(PropertyKey key, String defaultValue);

    void saveProperty(PropertyKey key, String value);
}
