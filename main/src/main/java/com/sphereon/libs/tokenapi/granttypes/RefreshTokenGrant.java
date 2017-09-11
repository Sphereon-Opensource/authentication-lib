package com.sphereon.libs.tokenapi.granttypes;

public interface RefreshTokenGrant extends Grant {
    String getRefreshToken();

    void setRefreshToken(String refreshToken);
}
