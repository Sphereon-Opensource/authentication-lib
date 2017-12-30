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
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;

abstract class AbstractCommonsConfig implements PropertyConfigBackend {

    private static final char[] libKey = "p9Ep%MSzac%*2txW".toCharArray();

    protected final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    protected final ApiConfiguration apiConfiguration;

    private final PersistenceMode persistenceMode;

    protected Configuration propertyConfig;


    protected AbstractCommonsConfig(ApiConfiguration configuration, PersistenceMode persistenceMode) {
        this.apiConfiguration = configuration;
        this.persistenceMode = persistenceMode;
        initEncryptor();
    }


    private void initEncryptor() {
        StringBuilder password = new StringBuilder();
        if (StringUtils.isNotEmpty(apiConfiguration.getAutoEncryptionPassword())) {
            password.append(apiConfiguration.getAutoEncryptionPassword()).append('.');
        }
        password.append(libKey);
        encryptor.setPassword(password.toString());
        encryptor.setSaltGenerator(new RandomSaltGenerator());
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        String value = propertyConfig.getString(propertyPrefix + key.getValue());
        if (StringUtils.isEmpty(value)) {
            if (StringUtils.isNotBlank(defaultValue)) {
                saveProperty(propertyPrefix, key, defaultValue);
            }
            return defaultValue;
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (persistenceMode == PersistenceMode.READ_WRITE) {
            propertyConfig.setProperty(propertyPrefix + key.getValue(), value);
        }
    }


    public void tryForcedSaveProperty(String propertyPrefix, PropertyKey key, String value) {
        try {
            propertyConfig.setProperty(propertyPrefix + key.getValue(), value);
        } catch (Exception ignored) {
        }
    }
}
