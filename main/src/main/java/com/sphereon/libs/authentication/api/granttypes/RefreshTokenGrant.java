package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.RefreshTokenGrantBuilder;

public interface RefreshTokenGrant extends RefreshTokenGrantBuilder {

    void setRefreshToken(String refreshToken);
}
