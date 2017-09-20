package com.sphereon.libs.authentication.api;

public interface TokenResponse {

    String getTokenType();

    String getAccessToken();

    String getRefreshToken();

    String getScope();

    Long getExpiresInSeconds();

    Long getResponseTimeMs();

    boolean isExpired();
}