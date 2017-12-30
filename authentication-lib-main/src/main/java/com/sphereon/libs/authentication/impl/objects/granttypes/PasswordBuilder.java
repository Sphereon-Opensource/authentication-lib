/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.assertions.Assert;
import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;

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
