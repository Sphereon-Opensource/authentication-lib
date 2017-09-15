package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.impl.commons.assertions.Assert;

public interface PasswordBuilder {

    final class PasswordGrantBuilder implements GrantBuilder {

        private String userName;
        private String password;


        public static PasswordGrantBuilder newInstance() {
            return new PasswordGrantBuilder();
        }


        public PasswordGrantBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }


        public PasswordGrantBuilder withPassword(String password) {
            this.password = password;
            return this;
        }


        public PasswordGrant build() {
            return build(true);
        }


        public PasswordGrant build(boolean validate) {
            if (validate) {
                validate();
            }

            PasswordGrantImpl passwordGrant = new PasswordGrantImpl();
            passwordGrant.setUserName(userName);
            passwordGrant.setPassword(password);
            return passwordGrant;
        }


        private void validate() {
            Assert.notNull(userName, "User name is not set in PasswordBuilder");
            Assert.notNull(password, "Password is not set in PasswordBuilder");
        }
    }
}
