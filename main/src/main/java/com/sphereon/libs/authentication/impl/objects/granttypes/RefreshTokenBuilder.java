package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface RefreshTokenBuilder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;

        private String refreshToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public RefreshTokenBuilder.Builder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }


        public Grant build() {
            RefreshTokenGrantImpl refreshTokenGrant = new RefreshTokenGrantImpl();
            refreshTokenGrant.setRefreshToken(refreshToken);
            configManager.loadGrant(refreshTokenGrant);
            return refreshTokenGrant;
        }
    }
}
