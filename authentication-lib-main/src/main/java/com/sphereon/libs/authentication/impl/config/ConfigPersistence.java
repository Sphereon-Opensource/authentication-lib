package com.sphereon.libs.authentication.impl.config;

public interface ConfigPersistence {

    void loadConfig(ConfigManager configManager);

    void persistConfig(ConfigManager configManager);

}
