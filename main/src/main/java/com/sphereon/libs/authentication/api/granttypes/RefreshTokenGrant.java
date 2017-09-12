package com.sphereon.libs.authentication.api.granttypes;

public interface RefreshTokenGrant extends Grant {

    void setRefreshToken(String refreshToken);
}
