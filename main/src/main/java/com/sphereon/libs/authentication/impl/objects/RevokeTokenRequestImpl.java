package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class RevokeTokenRequestImpl extends TokenRequestImpl implements TokenRequest, ConfigPersistence, RequestParameters {


    protected String token;


    public RevokeTokenRequestImpl(ConfigManager configManager) {
        super(configManager);
    }


    public void setToken(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }


    @Override
    public TokenResponse execute() {

        FormBody requestBody = httpRequestHandler.buildBody(this);
        Headers headers = httpRequestHandler.buildHeaders(this);
        Request httpRequest = httpRequestHandler.newRevokeRequest(configManager.getConfiguration().getGatewayBaseUrl(), headers, requestBody);
        return executeRequest(httpRequest);
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        super.loadConfig((configManager));
        if (StringUtils.isEmpty(getToken())) {
            setToken(configManager.readProperty(PropertyKey.CURRENT_TOKEN));
        }
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
        // TODO:
    }
}