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

import com.sphereon.libs.authentication.api.TokenResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class TokenResponseImpl implements TokenResponse {

    private static final int SAFETY_MARGIN_SECONDS = 30;

    private String accessToken;

    private String refreshToken;

    private String scope;

    private String tokenType;

    private Long expiresInSeconds;

    private final long responseTimeMs = System.currentTimeMillis();


    TokenResponseImpl(Map<String, String> parameters) {
        accessToken = parameters.get(ResponseParameterKey.ACCESS_TOKEN.getValue());
        refreshToken = parameters.get(ResponseParameterKey.REFRESH_TOKEN.getValue());
        scope = parameters.get(ResponseParameterKey.SCOPE.getValue());
        tokenType = parameters.get(ResponseParameterKey.TOKEN_TYPE.getValue());
        String stringExpiresIn = parameters.get(ResponseParameterKey.EXPIRES_IN.getValue());
        if (StringUtils.isNotEmpty(stringExpiresIn)) {
            expiresInSeconds = Long.parseLong(stringExpiresIn);
        }
    }


    @Override
    public String getAccessToken() {
        return accessToken;
    }


    @Override
    public String getRefreshToken() {
        return refreshToken;
    }


    @Override
    public String getScope() {
        return scope;
    }


    @Override
    public String getTokenType() {
        return tokenType;
    }


    @Override
    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }


    @Override
    public Long getResponseTimeMs() {
        return responseTimeMs;
    }


    @Override
    public boolean isExpired() {
        if (expiresInSeconds == null) {
            return false;
        }

        long expiresInSeconds = this.expiresInSeconds - SAFETY_MARGIN_SECONDS;
        if (expiresInSeconds < 0) {
            expiresInSeconds = 0;
        }
        long expiryTimeMs = expiresInSeconds * 1000;
        return System.currentTimeMillis() > responseTimeMs + expiryTimeMs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenResponseImpl)) {
            return false;
        }

        TokenResponseImpl that = (TokenResponseImpl) o;

        if (getAccessToken() != null ? !getAccessToken().equals(that.getAccessToken()) : that.getAccessToken() != null) {
            return false;
        }
        if (getRefreshToken() != null ? !getRefreshToken().equals(that.getRefreshToken()) : that.getRefreshToken() != null) {
            return false;
        }
        if (getScope() != null ? !getScope().equals(that.getScope()) : that.getScope() != null) {
            return false;
        }
        if (getTokenType() != null ? !getTokenType().equals(that.getTokenType()) : that.getTokenType() != null) {
            return false;
        }
        return getExpiresInSeconds() != null ? getExpiresInSeconds().equals(that.getExpiresInSeconds()) : that.getExpiresInSeconds() == null;
    }


    @Override
    public int hashCode() {
        int result = getAccessToken() != null ? getAccessToken().hashCode() : 0;
        result = 31 * result + (getRefreshToken() != null ? getRefreshToken().hashCode() : 0);
        result = 31 * result + (getScope() != null ? getScope().hashCode() : 0);
        result = 31 * result + (getTokenType() != null ? getTokenType().hashCode() : 0);
        result = 31 * result + (getExpiresInSeconds() != null ? getExpiresInSeconds().hashCode() : 0);
        return result;
    }
}
