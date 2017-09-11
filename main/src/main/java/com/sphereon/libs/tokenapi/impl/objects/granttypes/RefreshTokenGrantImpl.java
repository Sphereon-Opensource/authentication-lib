package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.RefreshTokenGrant;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

import java.util.Map;

public class RefreshTokenGrantImpl extends AutoHashedObject implements RefreshTokenGrant, BodyParameters, ConfigPersistence {

    private String refreshToken;


    @Override
    public String getRefreshToken() {
        return refreshToken;
    }


    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.REFRESH_TOKEN.getValue());
        parameterMap.put(BodyParameterKey.REFRESH_TOKEN, getRefreshToken());
    }


    @Override
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        setRefreshToken(configManager.readProperty(tokenApiConfiguration, PropertyKey.REFRESH_TOKEN));
    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.REFRESH_TOKEN, getRefreshToken());
    }
}