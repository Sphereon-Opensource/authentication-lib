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

        String value = System.getenv(propertyPrefix + key.getPropertyKey());
        if (StringUtils.isEmpty(value)) {
            value = System.getProperty(propertyPrefix + key.getPropertyKey());
        }
        if (StringUtils.isEmpty(value)) {
            if (apiConfiguration.getPersistenceMode() == PersistenceMode.READ_WRITE && StringUtils.isNotBlank(defaultValue)) {
                saveProperty(propertyPrefix, key, defaultValue);
            }
            return super.readProperty(propertyPrefix, key, defaultValue);
        }
        return value;
    }


    @Override
    public void saveProperty(String propertyPrefix, PropertyKey key, String value) {
        if (apiConfiguration.getPersistenceMode() == PersistenceMode.READ_WRITE) {
            if (key.isEncrypt() && apiConfiguration.isAutoEncryptSecrets() && !PropertyValueEncryptionUtils.isEncryptedValue(value)) {
                String encryptedValue = PropertyValueEncryptionUtils.encrypt(value, encryptor);
                System.out.printf("Can't auto-encrypt system environment variables. The encrypted value of %s%s is %s%n", propertyPrefix, key.getPropertyKey(), encryptedValue);
            }
        }
        super.saveProperty(propertyPrefix, key, value);
    }
}