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
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

public class PropertyFileBackend extends AbstractCommonsConfig {


    public PropertyFileBackend(ApiConfiguration configuration, URI fileUri) {
        super(configuration, configuration.getPersistenceMode());

        try {
            File configFile = new File(fileUri);
            if (configFile.getParentFile() != null && !configFile.getParentFile().exists()) {
                if (!configFile.getParentFile().mkdirs()) {
                    throw new IOException(String.format("File path %s does not exist and could not be created.", configFile.getParentFile().getAbsolutePath()));
                }
            }
            String ext = FilenameUtils.getExtension(configFile.getName());
            ensureConfigFileExists(configFile, ext);
            Parameters params = new Parameters();
            Class<? extends FileBasedConfiguration> configClass = "xml".equalsIgnoreCase(ext) ? XMLConfiguration.class : PropertiesConfiguration.class;
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new ReloadingFileBasedConfigurationBuilder<>(configClass)
                            .configure(params.properties()
                                    .setFile(configFile));
            builder.setAutoSave(configuration.getPersistenceMode() == PersistenceMode.READ_WRITE || configuration.isAutoEncryptSecrets());
            this.propertyConfig = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize PropertyFileBackend for " + fileUri, e);
        }
    }


    private void ensureConfigFileExists(File configFile, String ext) {
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile()) {
                    throw new IOException("Could not create new config file.");
                }
                if ("xml".equalsIgnoreCase(ext)) {
                    try (FileWriter writer = new FileWriter(configFile)) {
                        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                        writer.write("<properties></properties>\r\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("A problem occurred whilst creating initial config file " + configFile.getAbsolutePath(), e);
            }
        }
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        String value = super.readProperty(propertyPrefix, key, defaultValue);
        if (StringUtils.isNotEmpty(value)) {
            if (PropertyValueEncryptionUtils.isEncryptedValue(value)) {
                try {
                    value = PropertyValueEncryptionUtils.decrypt(value, encryptor);
                } catch (EncryptionOperationNotPossibleException e) {
                    throw new RuntimeException("Could not decrypt " + key.toString() + "=" + value);
                }

            } else if (key.isEncrypt() && apiConfiguration.isAutoEncryptSecrets()) {
                String encryptedValue = PropertyValueEncryptionUtils.encrypt(value, encryptor);
                super.tryForcedSaveProperty(propertyPrefix, key, encryptedValue);
            }
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        if (key.isEncrypt() && apiConfiguration.isAutoEncryptSecrets() && !PropertyValueEncryptionUtils.isEncryptedValue(value)) {
            value = PropertyValueEncryptionUtils.encrypt(value, encryptor);
            if (apiConfiguration.getPersistenceMode() == PersistenceMode.READ_ONLY) {
                super.tryForcedSaveProperty(propertyPrefix, key, value);
            }
        }
        super.saveProperty(propertyPrefix, key, value);
    }
}
