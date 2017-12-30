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

package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;

public interface AuthenticationApiBuilder {

    final class Builder {

        private ApiConfiguration configuration;


        public static final Builder newInstance() {
            return new Builder();
        }


        public Builder() {
        }


        public Builder withConfiguration(ApiConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }


        public AuthenticationApi build() {
            if (configuration == null) {
                configuration = new ApiConfiguration.Builder()
                        .withPersistenceType(PersistenceType.IN_MEMORY)
                        .withPersistenceMode(PersistenceMode.READ_WRITE).build();
            }

            return new AuthenticationApiImpl(configuration);
        }
    }
}
