package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.RevokeTokenRequest;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;

import java.util.Map;

class RevokeTokenRequestImpl extends TokenRequestImpl implements RevokeTokenRequest, ConfigPersistence, RequestParameters {

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


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        super.bodyParameters(parameterMap);
        // TODO:
    }
}