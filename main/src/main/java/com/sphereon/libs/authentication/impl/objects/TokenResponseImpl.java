package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.TokenResponse;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.Map;

public class TokenResponseImpl extends AutoHashedObject implements TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String scope;

    private String tokenType;

    private Duration expiresIn;


    public TokenResponseImpl(Map<String, String> parameters) {
        accessToken = parameters.get("access_token");
        refreshToken = parameters.get("refresh_token");
        scope = parameters.get("scope");
        tokenType = parameters.get("token_type");
        String stringExpiresIn = parameters.get("expires_in");
        if (StringUtils.isNotEmpty(stringExpiresIn)) {
            expiresIn = Duration.ofSeconds(Long.parseLong(stringExpiresIn));
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
    public Duration getExpiresIn() {
        return expiresIn;
    }


    @Override
    public void setExpiresIn(Duration expiresIn) {
        this.expiresIn = expiresIn;
    }
}
