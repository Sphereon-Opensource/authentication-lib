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
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.util.Map;

class RevokeTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {


    protected String token;


    RevokeTokenRequestImpl(ApiConfiguration configuration) {
        super(configuration);
    }


    public void setToken(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }


    @Override
    public TokenResponse execute() {
        HttpRequestHandler requestHandler = new HttpRequestHandler(configuration);
        FormBody requestBody = requestHandler.buildBody(this);
        Headers headers = requestHandler.buildHeaders(this);
        Request httpRequest = requestHandler.newRevokeRequest(configuration.getGatewayBaseUrl(), headers, requestBody);
        return executeRequest(requestHandler, httpRequest);
    }


    @Override
    public void addTokenResponseListener(TokenResponseListener listener) {

    }


    @Override
    public void removeTokenResponseListener(TokenResponseListener listener) {

    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.TOKEN, "" + getToken());
    }
}