package com.sphereon.libs.authentication.impl.objects;


import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.commons.objects.AutoHashedObject;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

abstract class TokenRequestImpl extends AutoHashedObject implements TokenRequest, RequestParameters {

    protected static final Base64.Encoder base64Encoder = Base64.getEncoder();

    protected static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    protected final ApiConfiguration configuration;

    private String consumerKey;

    private transient String consumerSecret;

    protected String scope;


    public TokenRequestImpl(ApiConfiguration configuration) {
        this.configuration = configuration;
    }


    public String getScope() {
        return scope;
    }


    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getConsumerKey() {
        return consumerKey;
    }


    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }


    public String getConsumerSecret() {
        return consumerSecret;
    }


    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
        try {
            String clientParameters = String.format("%s:%s", getConsumerKey(), getConsumerSecret());
            String authorizationHeader = String.format("Basic %s", base64Encoder.encodeToString(clientParameters.getBytes("UTF-8")));
            parameterMap.put(RequestParameterKey.AUTHORIZATION, authorizationHeader);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        if (StringUtils.isNotBlank(getScope())) {
            parameterMap.put(RequestParameterKey.SCOPE, getScope());
        }
    }


    protected TokenResponse executeRequest(Request httpRequest) {
        try {
            Response response = httpRequestHandler.execute(httpRequest);
            String responseBody = httpRequestHandler.getResponseBodyContent(response);
            Map<String, String> parameters = httpRequestHandler.parseJsonResponseBody(responseBody);
            return new TokenResponseImpl(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}