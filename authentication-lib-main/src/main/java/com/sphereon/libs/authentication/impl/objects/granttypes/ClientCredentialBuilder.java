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
import com.sphereon.libs.authentication.api.config.ClientCredentialsMode;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialGrant;

public interface ClientCredentialBuilder {

    final class ClientCredentialGrantBuilder implements GrantBuilder {

        private ClientCredentialsMode clientCredentialsMode;
        private String consumerKey;

        private String consumerSecret;


        public static ClientCredentialGrantBuilder newInstance() {
            return new ClientCredentialGrantBuilder();
        }


        public ClientCredentialGrant build() {
            return build(true);
        }


        public ClientCredentialGrant build(boolean validate) {
            if (validate) {
                validate();
            }

            return new ClientCredentialGrantImpl(clientCredentialsMode, consumerKey, consumerSecret);
        }

        private void validate() {
            Assert.notNull(clientCredentialsMode, "clientCredentialsMode is not set in ClientCredentialGrantBuilder");
            if(clientCredentialsMode == ClientCredentialsMode.BODY) {
                Assert.notNull(consumerKey, "consumerKey is not set in ClientCredentialGrantBuilder");
                Assert.notNull(consumerSecret, "consumerSecret is not set in ClientCredentialGrantBuilder");
            }
        }

        public ClientCredentialGrantBuilder withClientCredentialsMode(final ClientCredentialsMode clientCredentialsMode) {
            this.clientCredentialsMode = clientCredentialsMode;
            return this;
        }

        public ClientCredentialGrantBuilder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }

        public ClientCredentialGrantBuilder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }
    }
}
