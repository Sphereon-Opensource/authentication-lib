package com.sphereon.libs.authentication.api.granttypes;

public interface RefreshTokenGrant extends Grant {

    String getRefreshToken();

    void setRefreshToken(String refreshToken);
}
