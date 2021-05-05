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
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class TokenRequestImpl implements TokenRequest, RequestParameters {

    protected static final Base64 base64Encoder = new Base64();

    protected final List<TokenResponseListener> tokenResponseListeners = new ArrayList<>();

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
    public void addTokenResponseListener(TokenResponseListener listener) {
        this.tokenResponseListeners.add(listener);
    }


    @Override
    public void removeTokenResponseListener(TokenResponseListener listener) {
        this.tokenResponseListeners.remove(listener);
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
        try {
            String clientParameters = String.format("%s:%s", getConsumerKey(), getConsumerSecret());
            String authorizationHeader = String.format("Basic %s", new String(base64Encoder.encode(clientParameters.getBytes("UTF-8"))));
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


    protected TokenResponse executeRequest(HttpRequestHandler requestHandler, Request httpRequest) {
        try {
            Response response = requestHandler.execute(httpRequest);
            String responseBody = requestHandler.getResponseBodyContent(response);
            Map<String, String> parameters = requestHandler.parseJsonResponseBody(responseBody);
            if (parameters == null) {
                return null;
            }
            TokenResponse tokenResponse = new TokenResponseImpl(parameters);
            for (TokenResponseListener listener : tokenResponseListeners) {
                listener.tokenResponse(tokenResponse);
            }
            return tokenResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void executeRequestAsync(final HttpRequestHandler requestHandler, Request httpRequest) {
        requestHandler.executeAsync(httpRequest, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                for (TokenResponseListener listener : tokenResponseListeners) {
                    listener.exception(e);
                }
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = requestHandler.getResponseBodyContent(response);
                Map<String, String> parameters = requestHandler.parseJsonResponseBody(responseBody);
                TokenResponse tokenResponse = new TokenResponseImpl(parameters);
                for (TokenResponseListener listener : tokenResponseListeners) {
                    listener.tokenResponse(tokenResponse);
                }
            }
        });
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenRequestImpl)) {
            return false;
        }

        TokenRequestImpl that = (TokenRequestImpl) o;

        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null) {
            return false;
        }
        if (getConsumerKey() != null ? !getConsumerKey().equals(that.getConsumerKey()) : that.getConsumerKey() != null) {
            return false;
        }
        if (getConsumerSecret() != null ? !getConsumerSecret().equals(that.getConsumerSecret()) : that.getConsumerSecret() != null) {
            return false;
        }
        return getScope() != null ? getScope().equals(that.getScope()) : that.getScope() == null;
    }


    @Override
    public int hashCode() {
        int result = configuration != null ? configuration.hashCode() : 0;
        result = 31 * result + (getConsumerKey() != null ? getConsumerKey().hashCode() : 0);
        result = 31 * result + (getConsumerSecret() != null ? getConsumerSecret().hashCode() : 0);
        result = 31 * result + (getScope() != null ? getScope().hashCode() : 0);
        return result;
    }
}