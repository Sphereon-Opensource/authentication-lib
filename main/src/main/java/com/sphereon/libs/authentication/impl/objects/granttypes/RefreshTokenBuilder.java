package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import com.sphereon.libs.authentication.impl.commons.assertions.Assert;

public interface RefreshTokenBuilder {

    final class RefreshTokenGrantBuilder implements GrantBuilder {

        private String refreshToken;


        public static RefreshTokenGrantBuilder newInstance() {
            return new RefreshTokenGrantBuilder();
        }


        public RefreshTokenGrantBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }


        public RefreshTokenGrant build() {
            return build(true);
        }


        public RefreshTokenGrant build(boolean validate) {
            if (validate) {
                validate();
            }
            RefreshTokenGrantImpl refreshTokenGrant = new RefreshTokenGrantImpl();
            refreshTokenGrant.setRefreshToken(refreshToken);
            return refreshTokenGrant;
        }


        private void validate() {
            Assert.notNull(refreshToken, "Refresh token is not set in RefreshTokenBuilder");
        }
    }
}
