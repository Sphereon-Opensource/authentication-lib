package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;

import java.util.Map;

public class RefreshTokenGrantImpl extends AutoHashedObject implements RefreshTokenGrant, BodyParameters, ConfigPersistence {

    private String refreshToken;


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
    public void loadConfig(ConfigManager configManager) {
        setRefreshToken(configManager.readProperty(PropertyKey.REFRESH_TOKEN));
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.REFRESH_TOKEN, getRefreshToken());
    }
}