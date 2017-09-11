package com.sphereon.libs.tokenapi.impl.config;

import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;

public interface ConfigPersistence {

    void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager);

    void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager);

}
