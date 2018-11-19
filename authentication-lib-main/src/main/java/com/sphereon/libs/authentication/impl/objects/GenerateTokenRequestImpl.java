/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.util.HashMap;
import java.util.Map;

class GenerateTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {

    private static final Map<Object, TokenResponse> cachedResponses = new HashMap<>();

    protected Grant grant;

    protected Long validityPeriodInSeconds;


    GenerateTokenRequestImpl(ApiConfiguration configuration) {
        super(configuration);
    }


    public Grant getGrant() {
        return grant;
    }


    public void setGrant(Grant grant) {
        this.grant = grant;
    }


    public Long getValidityPeriodInSeconds() {
        return validityPeriodInSeconds;
    }


    public void setValidityPeriodInSeconds(Long validityPeriodInSeconds) {
        this.validityPeriodInSeconds = validityPeriodInSeconds;
    }


    @Override
    public TokenResponse execute() {
        TokenResponse cachedResponse = getCachedResponse();
        if (cachedResponse != null) {
            return cachedResponse;
        }

        TokenResponse tokenResponse = buildAndExecuteRequest();
        cachedResponses.put(this, tokenResponse);
        return tokenResponse;
    }


    @Override
    public void executeAsync() {
        TokenResponse cachedResponse = getCachedResponse();
        if (cachedResponse != null) {
            for (TokenResponseListener listener : tokenResponseListeners) {
                listener.tokenResponse(cachedResponse);
            }
        }
        buildAndExecuteRequestAsync();
        addTokenResponseListener(new TokenResponseListener() {
            @Override
            public void tokenResponse(TokenResponse tokenResponse) {
                cachedResponses.put(this, tokenResponse);

            }


            @Override
            public void exception(Throwable t) {

            }
        });
    }


    private TokenResponse getCachedResponse() {
        TokenResponse cachedResponse = cachedResponses.get(this);
        if (cachedResponse != null) {
            if (cachedResponse.isExpired()) {
                cachedResponses.remove(this);
                tryRevokeToken(cachedResponse);
            } else {
                return cachedResponse;
            }
        }
        return null;
    }


    private void tryRevokeToken(TokenResponse cachedResponse) {
        try {
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(configuration);
            revokeTokenRequest.setToken(cachedResponse.getAccessToken());
        } catch (Throwable throwable) {
            throwable.printStackTrace(); // FIXME no other way to log this now
        }
    }


    private TokenResponse buildAndExecuteRequest() {
        HttpRequestHandler requestHandler = new HttpRequestHandler(configuration);
        Request httpRequest = buildRequest(requestHandler);
        return executeRequest(requestHandler, httpRequest);
    }


    private void buildAndExecuteRequestAsync() {
        HttpRequestHandler requestHandler = new HttpRequestHandler(configuration);
        Request httpRequest = buildRequest(requestHandler);
        executeRequestAsync(requestHandler, httpRequest);
    }


    private Request buildRequest(HttpRequestHandler requestHandler) {
        FormBody requestBody = requestHandler.buildBody(this);
        Headers headers = requestHandler.buildHeaders(this);
        return requestHandler.newTokenRequest(configuration.getGatewayBaseUrl(), headers, requestBody);
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
        super.headerParameters(parameterMap);
        RequestParameters grantParameters = (RequestParameters) getGrant();
        grantParameters.headerParameters(parameterMap);
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        if (getValidityPeriodInSeconds() != null) {
            parameterMap.put(RequestParameterKey.VALIDITY_PERIOD, "" + getValidityPeriodInSeconds());
        }
        if (getScope() != null) {
            parameterMap.put(RequestParameterKey.SCOPE, "" + getScope());
        }
        RequestParameters grantParameters = (RequestParameters) getGrant();
        grantParameters.bodyParameters(parameterMap);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenerateTokenRequestImpl)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        GenerateTokenRequestImpl that = (GenerateTokenRequestImpl) o;

        if (getGrant() != null ? !getGrant().equals(that.getGrant()) : that.getGrant() != null) {
            return false;
        }
        return getValidityPeriodInSeconds() != null ? getValidityPeriodInSeconds().equals(that.getValidityPeriodInSeconds()) : that.getValidityPeriodInSeconds() == null;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getGrant() != null ? getGrant().hashCode() : 0);
        result = 31 * result + (getValidityPeriodInSeconds() != null ? getValidityPeriodInSeconds().hashCode() : 0);
        return result;
    }
}