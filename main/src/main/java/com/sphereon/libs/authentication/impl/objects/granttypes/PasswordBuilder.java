package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface PasswordBuilder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;
        private String userName;
        private String password;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public PasswordBuilder.Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }


        public PasswordBuilder.Builder withPassword(String password) {
            this.password = password;
            return this;
        }


        public Grant build() {
            PasswordGrantImpl passwordGrant = new PasswordGrantImpl();
            passwordGrant.setUserName(userName);
            passwordGrant.setPassword(password);
            configManager.loadGrant(passwordGrant);
            return passwordGrant;
        }
    }
}
