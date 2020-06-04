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

package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.properties.PropertyValueEncryptionUtils;

public class SystemEnvPropertyBackend extends InMemoryConfig {

    public SystemEnvPropertyBackend(ApiConfiguration configuration) {
        super(configuration);
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        String propertyVarName = propertyPrefix + key.getValue();
        String value = getVarFromEnv(propertyVarName.replace('.', '_'));

        if (StringUtils.isEmpty(value)) {
            value = System.getProperty(propertyPrefix + key.getValue());
        }

        if (StringUtils.isEmpty(value)) {
            if (apiConfiguration.getPersistenceMode() == PersistenceMode.READ_WRITE && StringUtils.isNotBlank(defaultValue)) {
                saveProperty(propertyPrefix, key, defaultValue);
            }
            value = super.readProperty(propertyPrefix, key, defaultValue);
            if (StringUtils.isEmpty(value) && StringUtils.containsIgnoreCase(propertyVarName, "CONSUMER")) {
                String envVarKey = propertyVarName.toUpperCase().replace('.', '_').replace('-', '_');
                throw new IllegalArgumentException(String.format("Variable %s could not be found in the system environment nor %s in the java environment.", envVarKey, propertyVarName));
            }
            return value;
        }
        return value;
    }


    private String getVarFromEnv(String propertyVarName) {
        String value = null;

        try {
            value = System.getenv(propertyVarName.toUpperCase().replace('-', '_'));
        } catch (Throwable ignored) {
        }

        if (StringUtils.isEmpty(value)) {
            try {
                value = System.getenv(propertyVarName.replace('-', '_'));
            } catch (Throwable ignored) {
            }
        }

        if (StringUtils.isEmpty(value)) {
            try {
                value = System.getenv(propertyVarName.toUpperCase());
            } catch (Throwable ignored) {
            }
        }

        if (StringUtils.isEmpty(value)) {
            try {
                value = System.getenv(propertyVarName);
            } catch (Throwable ignored) {
            }
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (apiConfiguration.getPersistenceMode() == PersistenceMode.READ_WRITE) {
            if (key.isEncrypt() && apiConfiguration.isAutoEncryptSecrets() && !PropertyValueEncryptionUtils.isEncryptedValue(value)) {
                String encryptedValue = PropertyValueEncryptionUtils.encrypt(value, encryptor);
                System.out.printf("Can't auto-encrypt system environment variables. The encrypted value of %s%s is %s%n", propertyPrefix, key.getValue(), encryptedValue);
            }
        }
        super.saveProperty(propertyPrefix, key, value);
    }
}
