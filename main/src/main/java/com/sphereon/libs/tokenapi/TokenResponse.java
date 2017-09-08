package com.sphereon.libs.tokenapi;

import java.time.Duration;

public interface TokenResponse {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getRefreshToken();

    void setRefreshToken(String refreshToken);

    String getScope();

    void setScope(String scope);

    String getTokenType();

    void setTokenType(String tokenType);

    Duration getExpiresIn();

    void setExpiresIn(Duration expiresIn);
}
