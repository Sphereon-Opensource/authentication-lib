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
import com.sphereon.libs.authentication.api.config.ClientCredentialsMode;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigManagerProvider;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialBuilder;
import org.apache.commons.lang3.StringUtils;


public interface GenerateTokenRequestBuilder {

    class Builder implements TokenRequestBuilder {

        private final ApiConfiguration configuration;

        private String consumerKey;
        private String consumerSecret;
        private Grant grant;
        private String scope;
        private String resource;
        private Long validityPeriodInSeconds;
        private ClientCredentialsMode clientCredentialsMode;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public GenerateTokenRequestBuilder.Builder withClientCredentialsMode(final ClientCredentialsMode clientCredentialsMode) {
            this.clientCredentialsMode = clientCredentialsMode;
            return this;
        }

        public GenerateTokenRequestBuilder.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withGrant(Grant grant) {
            this.grant = grant;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }

        public GenerateTokenRequestBuilder.Builder withResource(String resource) {
            this.resource = resource;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withValidityPeriod(int validityPeriodInSeconds) {
            this.validityPeriodInSeconds = Long.valueOf(validityPeriodInSeconds);
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withValidityPeriod(Long validityPeriodInSeconds) {
            this.validityPeriodInSeconds = validityPeriodInSeconds;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            ConfigManagerProvider configManagerProvider = (ConfigManagerProvider) configuration;
            ConfigManager configManager = configManagerProvider.getConfigManager();
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(configManager);
            GenerateTokenRequestImpl tokenRequest = new GenerateTokenRequestImpl(configuration);
            tokenRequest.setGrant(grant);
            tokenRequest.setClientCredentialsMode(clientCredentialsMode);
            tokenRequest.setConsumerKey(consumerKey);
            tokenRequest.setConsumerSecret(consumerSecret);
            tokenRequest.setScope(scope);
            tokenRequest.setResource(resource);
            tokenRequest.setValidityPeriodInSeconds(validityPeriodInSeconds);
            return tokenRequest;
        }


        private void validate() {
            if (clientCredentialsMode == null) {
                this.clientCredentialsMode = ClientCredentialsMode.BASIC_HEADER; // Backwards compatibility
            }

            if (clientCredentialsMode == ClientCredentialsMode.BASIC_HEADER) {
                if (StringUtils.isEmpty(consumerKey)) {
                    this.consumerKey = configuration.getConsumerKey();
                }
                if (StringUtils.isEmpty(consumerKey)) {
                    throw new IllegalArgumentException(String.format(
                            "The consumer key variable was not found for application %s. Please check your configuration.",
                            configuration.getApplication()));
                }
                if (StringUtils.isEmpty(consumerSecret)) {
                    this.consumerSecret = configuration.getConsumerSecret();
                }
                if (StringUtils.isEmpty(consumerSecret)) {
                    throw new IllegalArgumentException(String.format(
                            "The consumer secret variable was not found for application %s. Please check your configuration.",
                            configuration.getApplication()));
                }
            }
            if (this.grant == null) {
                this.grant = configuration.getDefaultGrant();
            }
            if (this.grant == null) {
                this.grant = new ClientCredentialBuilder.ClientCredentialGrantBuilder()
                        .withClientCredentialsMode(clientCredentialsMode)
                        .withConsumerKey(consumerKey)
                        .withConsumerSecret(consumerSecret)
                        .build();
            }
            if (StringUtils.isEmpty(scope)) {
                this.scope = configuration.getDefaultScope();
            }

            if (validityPeriodInSeconds == null) {
                this.validityPeriodInSeconds = configuration.getDefaultValidityPeriod();
            }
        }
    }
}
