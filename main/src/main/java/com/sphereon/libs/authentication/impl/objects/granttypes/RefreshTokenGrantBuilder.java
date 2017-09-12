package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface RefreshTokenGrantBuilder extends Grant {

    final class Builder implements GrantBuilder<RefreshTokenGrant> {

        private final ConfigManager configManager;

        private String refreshToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }


        public RefreshTokenGrant build() {
            RefreshTokenGrantImpl refreshTokenGrant = new RefreshTokenGrantImpl();
            refreshTokenGrant.setRefreshToken(refreshToken);
            configManager.loadGrant(refreshTokenGrant);
            return refreshTokenGrant;
        }
    }
}
