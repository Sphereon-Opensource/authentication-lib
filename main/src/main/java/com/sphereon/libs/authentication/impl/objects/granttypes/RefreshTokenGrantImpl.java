package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class RefreshTokenGrantImpl extends AutoHashedObject implements RefreshTokenGrant, RequestParameters, ConfigPersistence {

    private String refreshToken;


    RefreshTokenGrantImpl() {
    }


    @Override
    public String getRefreshToken() {
        return refreshToken;
    }


    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.REFRESH_TOKEN.getValue());
        parameterMap.put(RequestParameterKey.REFRESH_TOKEN, getRefreshToken());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isEmpty(getRefreshToken())) {
            setRefreshToken(configManager.readProperty(PropertyKey.REFRESH_TOKEN));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.REFRESH_TOKEN, getRefreshToken());
    }


    @Override
    public GrantType getGrantType() {
        return GrantType.REFRESH_TOKEN;
    }
}