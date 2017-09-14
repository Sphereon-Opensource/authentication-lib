package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.salt.StringFixedSaltGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

public class PropertyFileBackend extends AbstractCommonsConfig {

    private static final char[] libKey = "p9Ep%MSzac%*2txW".toCharArray();

    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    private final String application;


    public PropertyFileBackend(PersistenceMode persistenceMode, URI fileUri, String application) {
        super(persistenceMode);
        this.application = application;
        initEncryptor();

        File configFile = new File(fileUri);
        if (configFile.getParentFile() != null && !configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }
        String ext = FilenameUtils.getExtension(configFile.getName());
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                if ("xml".equalsIgnoreCase(ext)) {
                    FileWriter writer = new FileWriter(configFile);
                    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                    writer.write("<properties></properties>\r\n");
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
        Parameters params = new Parameters();
        Class<? extends FileBasedConfiguration> configClass = "xml".equalsIgnoreCase(ext) ? XMLConfiguration.class : PropertiesConfiguration.class;
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(configClass)
                        .configure(params.properties()
                                .setFile(configFile)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        builder.setAutoSave(persistenceMode == PersistenceMode.READ_WRITE);
        try {
            this.config = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize PropertyFileBackend for " + fileUri, e);
        }
    }


    private void initEncryptor() {
        encryptor.setPassword(new String(libKey));
        encryptor.setSaltGenerator(new StringFixedSaltGenerator(application));
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

            } else if (key.isEncrypt()) {
                String encryptedValue = PropertyValueEncryptionUtils.encrypt(value, encryptor);
                super.saveProperty(propertyPrefix, key, encryptedValue);
            }
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        if (key.isEncrypt() && !PropertyValueEncryptionUtils.isEncryptedValue(value)) {
            value = PropertyValueEncryptionUtils.encrypt(value, encryptor);
        }
        super.saveProperty(propertyPrefix, key, value);
    }
}
