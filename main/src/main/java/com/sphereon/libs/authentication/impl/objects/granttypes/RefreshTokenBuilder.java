package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface RefreshTokenBuilder {

    final class RefreshTokenGrantBuilder implements GrantBuilder {

        private String refreshToken;


        public RefreshTokenGrantBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }


        public Grant build() {
            RefreshTokenGrantImpl refreshTokenGrant = new RefreshTokenGrantImpl();
            refreshTokenGrant.setRefreshToken(refreshToken);
            return refreshTokenGrant;
        }
    }
}
