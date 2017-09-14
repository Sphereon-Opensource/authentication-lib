package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface PasswordBuilder {

    final class PasswordGrantBuilder implements GrantBuilder {

        private String userName;
        private String password;


        public PasswordGrantBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }


        public PasswordGrantBuilder withPassword(String password) {
            this.password = password;
            return this;
        }


        public Grant build() {
            PasswordGrantImpl passwordGrant = new PasswordGrantImpl();
            passwordGrant.setUserName(userName);
            passwordGrant.setPassword(password);
            return passwordGrant;
        }
    }
}
