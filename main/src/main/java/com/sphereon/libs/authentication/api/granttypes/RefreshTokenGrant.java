package com.sphereon.libs.authentication.api.granttypes;

public interface RefreshTokenGrant extends Grant {

    String getRefreshToken();

    RefreshTokenGrant setRefreshToken(String refreshToken);
}
