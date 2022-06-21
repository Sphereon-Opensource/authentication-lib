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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.configuration2.io.CombinedLocationStrategy;
import org.apache.commons.configuration2.io.FileSystemLocationStrategy;
import org.apache.commons.configuration2.io.ProvidedURLLocationStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;

public class PropertyFileBackend extends AbstractCommonsConfig {

    private Log log = LogFactory.getLog(PropertyFileBackend.class);

    @Deprecated
    public PropertyFileBackend(ApiConfiguration configuration, URI fileUri) {
        super(configuration, configuration.getPersistenceMode());

        initConfig(configuration, fileUrl);
    }

    private void initConfig(final ApiConfiguration configuration, final URL fileUrl) {
        log.info(String.format("Loading configuration from %s...", fileUrl));
        final boolean jar = fileUrl.toString().startsWith("jar:");
        final boolean xml = "xml".equalsIgnoreCase(FilenameUtils.getExtension(fileUrl.getFile()));
        if (jar) {
            log.info(String.format("Configuration %s will be loaded from classpath, meaning default values", fileUrl));
            if (configuration.getPersistenceMode() == PersistenceMode.READ_WRITE) {
                log.warn("Setting configuration persistence mode to read-only given we got a configuration from the classpath: " + fileUrl);
                configuration.setPersistenceMode(PersistenceMode.READ_ONLY);
            }
        } else {
            ensureConfigFileExists(fileUrl, xml);
        }

        try {
            initConfig(configuration, fileUri.toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public PropertyFileBackend(ApiConfiguration configuration, File file) {
        this(configuration, file.toURI());
    }

    public PropertyFileBackend(ApiConfiguration configuration, URL fileUrl) {
        super(configuration, configuration.getPersistenceMode());

        initConfig(configuration, fileUrl);
    }

    private void initConfig(final ApiConfiguration configuration, final URL fileUrl) {
        log.info(String.format("Loading configuration from %s...", fileUrl));
        final boolean jar = fileUrl.toString().startsWith("jar:");
        final boolean xml = "xml".equalsIgnoreCase(FilenameUtils.getExtension(fileUrl.getFile()));
        if (jar) {
            log.info(String.format("Configuration %s will be loaded from classpath, meaning default values", fileUrl));
            if (configuration.getPersistenceMode() == PersistenceMode.READ_WRITE) {
                log.warn("Setting configuration persistence mode to read-only given we got a configuration from the classpath: " + fileUrl);
                configuration.setPersistenceMode(PersistenceMode.READ_ONLY);
            }
        } else {
            ensureConfigFileExists(fileUrl, xml);
        }

        try {
            Parameters params = new Parameters();
            Class<? extends FileBasedConfiguration> configClass =
              xml ? XMLConfiguration.class : PropertiesConfiguration.class;

            final CombinedLocationStrategy locationStrategy = new CombinedLocationStrategy(
              Arrays.asList(new ProvidedURLLocationStrategy(), new FileSystemLocationStrategy(), new ClasspathLocationStrategy()));

            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
              new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(configClass)
                .configure(params.properties().setLocationStrategy(locationStrategy)
                  .setURL(fileUrl));
            builder.setAutoSave(configuration.getPersistenceMode() == PersistenceMode.READ_WRITE && !jar);
            this.propertyConfig = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize PropertyFileBackend for " + fileUrl, e);
        }
        log.info(String.format("Property file %s loaded", fileUrl.getFile()));
    }


    private void ensureConfigFileExists(URL configUrl, boolean xml) {
        try {
            File configFile = new File(configUrl.toURI());
            if (configFile.getParentFile() != null && !configFile.getParentFile().exists()) {
                if (!configFile.getParentFile().mkdirs()) {
                    throw new IOException(
                      String.format("File path %s does not exist and could not be created.", configFile.getParentFile().getAbsolutePath()));
                }
            }
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) {
                    throw new IOException("Could not create new config file.");
                }
                if (xml) {
                    try (FileWriter writer = new FileWriter(configFile)) {
                        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                        writer.write("<properties></properties>\r\n");
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("A problem occurred whilst creating initial config file " + configUrl, e);
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
