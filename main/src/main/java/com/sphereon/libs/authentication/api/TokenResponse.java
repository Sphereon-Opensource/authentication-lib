package com.sphereon.libs.authentication.api;

public interface TokenResponse {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    String getScope();

    void setScope(String scope);

    String getTokenType();

    void setTokenType(String tokenType);

    Long getExpiresInSeconds();

    void setExpiresInSeconds(Long expiresIn);
}
