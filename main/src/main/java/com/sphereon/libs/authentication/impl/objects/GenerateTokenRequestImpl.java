package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.Grant;
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

import java.time.Duration;
import java.util.Map;

class GenerateTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {
    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    protected final Grant grant;

    protected Duration validityPeriod;


    GenerateTokenRequestImpl(Grant grant, ConfigManager configManager) {
        super(configManager);
        this.grant = grant;
    }


    public Grant getGrant() {
        return grant;
    }


    public Duration getValidityPeriod() {
        return validityPeriod;
    }


    public void setValidityPeriod(Duration validityPeriod) {
        this.validityPeriod = validityPeriod;
    }


    @Override
    public TokenResponse execute() {
        FormBody requestBody = httpRequestHandler.buildBody(this);
        Headers headers = httpRequestHandler.buildHeaders(this);
        Request httpRequest = httpRequestHandler.newTokenRequest(configManager.getConfiguration().getGatewayBaseUrl(), headers, requestBody);
        return executeRequest(httpRequest);
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        if (StringUtils.isEmpty(getScope())) {
            setScope(configManager.readProperty(PropertyKey.SCOPE));
        }
        if (getValidityPeriod() == null) {
            String validityPeriod = configManager.readProperty(PropertyKey.VALIDITY_PERIOD);
            if (StringUtils.isNotBlank(validityPeriod)) {
                setValidityPeriod(Duration.parse(validityPeriod));
            }
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        if (getValidityPeriod() != null) {
            configManager.saveProperty(PropertyKey.VALIDITY_PERIOD, getValidityPeriod().toString());
        }
        if (getGrant() != null && getGrant() instanceof ConfigPersistence) {
            ((ConfigPersistence) getGrant()).persistConfig(configManager);
        }
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
        super.headerParameters(parameterMap);
        RequestParameters grantParameters = (RequestParameters) getGrant();
        grantParameters.headerParameters(parameterMap);
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        if (getValidityPeriod() != null) {
            parameterMap.put(RequestParameterKey.VALIDITY_PERIOD, "" + getValidityPeriod().getSeconds());
        }
        RequestParameters grantParameters = (RequestParameters) getGrant();
        grantParameters.bodyParameters(parameterMap);
    }
}