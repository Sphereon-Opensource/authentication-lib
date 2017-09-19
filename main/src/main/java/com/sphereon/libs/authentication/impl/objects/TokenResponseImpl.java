package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class TokenResponseImpl implements TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String scope;

    private String tokenType;

    private Long expiresInSeconds;


    TokenResponseImpl(Map<String, String> parameters) {
        accessToken = parameters.get(ResponseParameterKey.ACCESS_TOKEN);
        refreshToken = parameters.get(ResponseParameterKey.REFRESH_TOKEN);
        scope = parameters.get(ResponseParameterKey.SCOPE);
        tokenType = parameters.get(ResponseParameterKey.TOKEN_TYPE);
        String stringExpiresIn = parameters.get(ResponseParameterKey.EXPIRES_IN);
        if (StringUtils.isNotEmpty(stringExpiresIn)) {
            expiresInSeconds = Long.parseLong(stringExpiresIn);
        }
    }


    @Override
    public String getAccessToken() {
        return accessToken;
    }


    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public String getRefreshToken() {
        return refreshToken;
    }


    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    @Override
    public String getScope() {
        return scope;
    }


    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }


    @Override
    public String getTokenType() {
        return tokenType;
    }


    @Override
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


    @Override
    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }


    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
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
