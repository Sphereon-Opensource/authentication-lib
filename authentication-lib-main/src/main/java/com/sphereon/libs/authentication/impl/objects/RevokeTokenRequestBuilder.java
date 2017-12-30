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

package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import org.apache.commons.lang3.StringUtils;

public interface RevokeTokenRequestBuilder {

    class Builder implements TokenRequestBuilder {

        private final ApiConfiguration configuration;

        private String consumerKey;

        private String consumerSecret;

        private String currentToken;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public RevokeTokenRequestBuilder.Builder withCurrentToken(String currentToken) {
            this.currentToken = currentToken;
            return this;
        }


        public RevokeTokenRequestBuilder.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public RevokeTokenRequestBuilder.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(configuration);
            revokeTokenRequest.setConsumerKey(consumerKey);
            revokeTokenRequest.setConsumerSecret(consumerSecret);
            revokeTokenRequest.setToken(currentToken);
            return revokeTokenRequest;
        }


        private void validate() {
            if (StringUtils.isEmpty(consumerKey)) {
                this.consumerKey = configuration.getConsumerKey();
            }
            if (StringUtils.isEmpty(consumerSecret)) {
                this.consumerSecret = configuration.getConsumerSecret();
            }

            if (StringUtils.isEmpty(currentToken)) {
                throw new NullPointerException("RevokeTokenRequest.setToken() is not set");
            }
        }
    }
}