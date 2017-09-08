package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.TokenResponse;

import java.time.Duration;

public class TokenResponseImpl implements TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String scope;

    private String tokenType;

    private Duration expiresIn;


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
