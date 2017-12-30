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

package com.sphereon.libs.authentication.impl.config;

import com.sphereon.commons.assertions.Assert;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;

import java.io.File;

public interface ApiConfigurator {

    final class Builder {

        final ApiConfigurationImpl configuration;


        public Builder() {
            this.configuration = new ApiConfigurationImpl();
            configuration.setPersistenceType(PersistenceType.DISABLED);
            configuration.setPersistenceMode(PersistenceMode.READ_ONLY);
        }


        public Builder withApplication(String applicationName) {
            configuration.setApplication(applicationName);
            return this;
        }


        public Builder withConsumerKey(String consumerKey) {
            configuration.setConsumerKey(consumerKey);
            return this;
        }


        public Builder withConsumerSecret(String consumerSecret) {
            configuration.setConsumerSecret(consumerSecret);
            return this;
        }


        public Builder withPersistenceType(PersistenceType persistenceType) {
            configuration.setPersistenceType(persistenceType);
            configuration.setPersistenceMode(PersistenceMode.READ_WRITE);
            return this;
        }


        public Builder withPersistenceMode(PersistenceMode persistenceMode) {
            configuration.setPersistenceMode(persistenceMode);
            return this;
        }


        public Builder withAutoEncryptSecrets(boolean autoEncryptSecrets) {
            configuration.setAutoEncryptSecrets(autoEncryptSecrets);
            return this;
        }


        public Builder withAutoEncryptionPassword(String autoEncryptionPassword) {
            configuration.setAutoEncryptionPassword(autoEncryptionPassword);
            return this;
        }


        public Builder withAutoEncryptionPassword(char[] autoEncryptionPassword) {
            configuration.setAutoEncryptionPassword(new String(autoEncryptionPassword));
            return this;
        }


        public Builder withStandaloneConfigFile(File standaloneConfigPath) {
            configuration.setStandalonePropertyFile(standaloneConfigPath);
            return this;
        }


        public Builder withDefaultGrant(Grant grant) {
            configuration.setDefaultGrant(grant);
            return this;
        }


        public Builder withGatewayBaseUrl(String gatewayBaseUrl) {
            configuration.setGatewayBaseUrl(gatewayBaseUrl);
            return this;
        }


        public ApiConfiguration build() {
            validate();
            ConfigPersistence configPersistence = configuration;
            configPersistence.loadConfig(configuration.getConfigManager());
            configPersistence.persistConfig(configuration.getConfigManager());
            return configuration;
        }


        private void validate() {
            switch (configuration.getPersistenceType()) {
                case DISABLED:
                    Assert.isTrue(configuration.getPersistenceMode() == PersistenceMode.READ_ONLY, "PersistenceMode.READ_ONLY is not valid for PersistentType.DISABLED");
                    break;
                case STANDALONE_PROPERTY_FILE:
                    Assert.notNull(configuration.getStandalonePropertyFile(), "In PersistentType.STANDALONE_PROPERTY_FILE the method setStandalonePropertyFile(File file) must be used.");
                    break;
                case IN_MEMORY:
                    Assert.isTrue(configuration.getPersistenceMode() == PersistenceMode.READ_WRITE, "PersistenceMode.READ_ONLY is not valid for PersistentType.IN_MEMORY");
                    break;
                default:
                    ;// No validations needed
            }
        }
    }

}
