package com.sphereon.libs.tokenapi.impl.objects;

import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

public class RevokeTokenRequestImpl extends TokenRequestImpl implements RevokeTokenRequest, ConfigPersistence, BodyParameters {

    protected String token;


    @Override
    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String getToken() {
        return token;
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        super.loadConfig((configManager));
        configManager.readProperty(PropertyKey.CURRENT_TOKEN);
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        super.persistConfig((configManager));
        configManager.saveProperty(PropertyKey.CURRENT_TOKEN, "");
    }


}