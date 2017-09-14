package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.util.Map;

class RevokeTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {


    protected String token;


    RevokeTokenRequestImpl(TokenApiConfiguration tokenApiConfiguration) {
        super(tokenApiConfiguration);
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
        Request httpRequest = httpRequestHandler.newRevokeRequest(tokenApiConfiguration.getGatewayBaseUrl(), headers, requestBody);
        return executeRequest(httpRequest);
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.TOKEN, "" + getToken());
    }
}