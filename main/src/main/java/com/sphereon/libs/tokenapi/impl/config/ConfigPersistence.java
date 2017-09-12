package com.sphereon.libs.tokenapi.impl.config;

import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;

public interface ConfigPersistence {

    void loadConfig(ConfigManager configManager);

    void persistConfig(ConfigManager configManager);

}
