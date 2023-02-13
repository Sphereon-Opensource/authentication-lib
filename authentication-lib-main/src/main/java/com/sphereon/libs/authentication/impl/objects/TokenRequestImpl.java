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
import com.sphereon.libs.authentication.api.config.ClientCredentialsMode;
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sphereon.commons.SigUtils.hmacSha256;
import static com.sphereon.commons.SigUtils.toBase64;

abstract class TokenRequestImpl implements TokenRequest, RequestParameters {

    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    protected static final Log log = LogFactory.getLog(TokenRequest.class);
    protected static final Base64 base64Encoder = new Base64();

    protected final List<TokenResponseListener> tokenResponseListeners = new ArrayList<>();

    protected final ApiConfiguration configuration;

    private String consumerKey;

    private transient String consumerSecret;

    protected String scope;
    protected String resource;
    private ClientCredentialsMode clientCredentialsMode;


    public TokenRequestImpl(ApiConfiguration configuration) {
        this.configuration = configuration;
    }


    public String getScope() {
        return scope;
    }


    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setResource(final String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public ClientCredentialsMode getClientCredentialsMode() {
        return clientCredentialsMode;
    }

    public void setClientCredentialsMode(final ClientCredentialsMode clientCredentialsMode) {
        this.clientCredentialsMode = clientCredentialsMode;
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
        // This must be available for all grant types as long as credentials mode is BASIC_HEADER
        switch (getClientCredentialsMode()) {
            case BASIC_HEADER:
                try {
                    String clientParameters = String.format("%s:%s", getConsumerKey(), getConsumerSecret());
                    String authorizationHeader = String.format("Basic %s", new String(base64Encoder.encode(clientParameters.getBytes("UTF-8"))));
                    parameterMap.put(RequestParameterKey.AUTHORIZATION, authorizationHeader);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case SESSION_TOKEN:
            String authorizationHeader = String.format("Bearer %s", createSessionRequestToken());
            parameterMap.put(RequestParameterKey.AUTHORIZATION, authorizationHeader);
        }
    }

    private String createSessionRequestToken() {
        // This method was created for the Djuma interface using a JWT session request mechanism
        // (Create JSON without extra deps)

        final long expirationSecs = (System.currentTimeMillis() / 1000) + 60;
        final String jwtPayload = new StringBuilder()
                .append("{\"exp\": \"").append(expirationSecs).append("\",")
                .append("\"clientidentifier\": \"").append(getConsumerKey()).append("\"}")
                .toString();
        final String signature = hmacSha256(toBase64(JWT_HEADER) + "." + toBase64(jwtPayload), getConsumerSecret());
        return toBase64(JWT_HEADER) + "." + toBase64(jwtPayload) + "." + signature;
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        if (StringUtils.isNotBlank(getScope())) {
            parameterMap.put(RequestParameterKey.SCOPE, getScope());
        }
    }


    protected TokenResponse executeRequest(HttpRequestHandler requestHandler, Request httpRequest) {
        try {
            log.info(String.format("Executing auth token request scope: %s, url: %s", scope, httpRequest.url()));
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
            log.info(String.format("Auth token received, expires in %s seconds, url: %s", tokenResponse.getExpiresInSeconds(), httpRequest.url()));
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
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof TokenRequestImpl)) return false;

        final TokenRequestImpl that = (TokenRequestImpl) o;

        return new EqualsBuilder().append(tokenResponseListeners, that.tokenResponseListeners).append(configuration, that.configuration).append(consumerKey, that.consumerKey).append(consumerSecret, that.consumerSecret).append(scope, that.scope).append(clientCredentialsMode, that.clientCredentialsMode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tokenResponseListeners).append(configuration).append(consumerKey).append(consumerSecret).append(scope).append(clientCredentialsMode).toHashCode();
    }
}
