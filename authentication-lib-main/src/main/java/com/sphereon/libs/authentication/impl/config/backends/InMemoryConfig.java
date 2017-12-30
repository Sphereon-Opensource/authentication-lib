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
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

public class InMemoryConfig extends AbstractCommonsConfig {

    private static final Map<String, Configuration> inMemoryConfiguration = new HashMap<>();


    public InMemoryConfig(ApiConfiguration configuration) {
        super(configuration, PersistenceMode.READ_WRITE);

        this.propertyConfig = inMemoryConfiguration.get(configuration.getApplication());
        if (propertyConfig == null) {
            BasicConfigurationBuilder<PropertiesConfiguration> builder = new BasicConfigurationBuilder<>(PropertiesConfiguration.class);
            try {
                this.propertyConfig = builder.getConfiguration();
                inMemoryConfiguration.put(configuration.getApplication(), propertyConfig);
            } catch (Exception e) {
                throw new RuntimeException("Could not initialize PropertyFileBackend / InMemoryConfig for application " + configuration.getApplication(), e);
            }
        }
    }
}