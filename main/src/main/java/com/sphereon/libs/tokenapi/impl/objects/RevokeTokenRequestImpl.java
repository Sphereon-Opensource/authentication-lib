package com.sphereon.libs.tokenapi.impl.objects;

import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;
import com.sphereon.libs.tokenapi.impl.config.TokenApiConfigurationImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

public class RevokeTokenRequestImpl extends TokenRequestImpl implements RevokeTokenRequest, ConfigPersistence, BodyParameters {

    protected String token;


    public RevokeTokenRequestImpl(String application) {
        super(application);
    }


    @Override
    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String getToken() {
        return token;
    }


    @Override
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        super.loadConfig(tokenApiConfiguration, configManager);
        configManager.readProperty(tokenApiConfiguration, PropertyKey.CURRENT_TOKEN);
    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        super.persistConfig(tokenApiConfiguration, configManager);
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.CURRENT_TOKEN, "");
    }


}