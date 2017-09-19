package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;

abstract class AbstractCommonsConfig implements PropertyConfigBackend {

    private static final char[] libKey = "p9Ep%MSzac%*2txW".toCharArray();

    private static final boolean isInUnitTest = "true".equalsIgnoreCase(System.getProperty("sphereon.testing"));

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
        if (isInUnitTest && StringUtils.isNotEmpty(apiConfiguration.getApplication())) {
            encryptor.setSaltGenerator(new StringFixedSaltGenerator(apiConfiguration.getApplication()));
        } else {
            encryptor.setSaltGenerator(new RandomSaltGenerator());
        }
    }


    @Override
    public String readProperty(String propertyPrefix, PropertyKey key, String defaultValue) {
        String value = propertyConfig.getString(propertyPrefix + key.getPropertyKey());
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
            propertyConfig.setProperty(propertyPrefix + key.getPropertyKey(), value);
        }
    }


    public void tryForcedSaveProperty(String propertyPrefix, PropertyKey key, String value) {
        try {
            propertyConfig.setProperty(propertyPrefix + key.getPropertyKey(), value);
        } catch (Exception ignored) {
        }
    }
}
