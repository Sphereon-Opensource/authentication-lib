package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.time.Duration;
import java.util.Map;

class GenerateTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {
    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    protected Grant grant;

    protected Duration validityPeriod;


    GenerateTokenRequestImpl(ApiConfiguration configuration) {
        super(configuration);
    }


    public Grant getGrant() {
        return grant;
    }


    public void setGrant(Grant grant) {
        this.grant = grant;
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
        Request httpRequest = httpRequestHandler.newTokenRequest(configuration.getGatewayBaseUrl(), headers, requestBody);
        return executeRequest(httpRequest);
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
        if (getScope() != null) {
            parameterMap.put(RequestParameterKey.SCOPE, "" + getScope());
        }
        RequestParameters grantParameters = (RequestParameters) getGrant();
        grantParameters.bodyParameters(parameterMap);
    }
}