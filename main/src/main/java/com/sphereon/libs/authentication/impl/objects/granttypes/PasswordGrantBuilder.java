package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface PasswordGrantBuilder extends Grant {

    final class Builder implements GrantBuilder<PasswordGrant> {

        private final ConfigManager configManager;
        private String userName;
        private String password;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }


        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }


        public PasswordGrant build() {
            PasswordGrantImpl passwordGrant = new PasswordGrantImpl();
            passwordGrant.setUserName(userName);
            passwordGrant.setPassword(password);
            configManager.loadGrant(passwordGrant);
            return passwordGrant;
        }
    }
}
