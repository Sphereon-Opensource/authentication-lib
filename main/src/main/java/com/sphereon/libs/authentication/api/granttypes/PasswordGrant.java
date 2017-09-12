package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.granttypes.PasswordGrantImpl;

public interface PasswordGrant extends Grant {

    void setUserName(String userName);

    void setPassword(String password);

    final class Builder {

        private final ConfigManager configManager;
        private String userName;
        private String password;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public PasswordGrant.Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }


        public PasswordGrant.Builder withPassword(String password) {
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
