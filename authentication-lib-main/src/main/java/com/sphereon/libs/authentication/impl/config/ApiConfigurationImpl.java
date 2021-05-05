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

import com.sphereon.commons.exceptions.EnumParseException;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

class ApiConfigurationImpl implements ApiConfiguration, ConfigPersistence, ConfigManagerProvider {

    private static final String DEFAULT_GATEWAY_URL = "https://gw.api.cloud.sphereon.com/";

    private String application;

    private PersistenceType persistenceType;

    private PersistenceMode persistenceMode = PersistenceMode.READ_ONLY;

    private String gatewayBaseUrl = DEFAULT_GATEWAY_URL;

    private File standalonePropertyFile;

    private Grant defaultGrant;

    private String consumerKey;

    private String consumerSecret;

    private String defaultScope;

    private Long defaultValidityPeriod;

    private boolean autoEncryptSecrets;

    private String autoEncryptionPassword;

    private ConfigManager configManager;
    private String envVarPrefix;


    ApiConfigurationImpl() {
    }


    @Override
    public String getApplication() {
        return application;
    }


    @Override
    public void setApplication(String application) {
        this.application = application;
        configManagerReinit();
    }


    @Override
    public String getGatewayBaseUrl() {
        return gatewayBaseUrl;
    }


    @Override
    public void setGatewayBaseUrl(String gatewayBaseUrl) {
        this.gatewayBaseUrl = gatewayBaseUrl;
    }


    @Override
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }


    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
        configManagerReinit();
    }


    @Override
    public PersistenceMode getPersistenceMode() {
        return persistenceMode;
    }


    public void setPersistenceMode(PersistenceMode persistenceMode) {
        this.persistenceMode = persistenceMode;
        configManagerReinit();
    }


    @Override
    public File getStandalonePropertyFile() {
        return standalonePropertyFile;
    }


    @Override
    public void setStandalonePropertyFile(File standalonePropertyFile) {
        this.standalonePropertyFile = standalonePropertyFile;
        configManagerReinit();
    }


    @Override
    public String getConsumerKey() {
        return consumerKey;
    }


    @Override
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }


    @Override
    public String getConsumerSecret() {
        return consumerSecret;
    }


    @Override
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }


    @Override
    public Grant getDefaultGrant() {
        return defaultGrant;
    }


    @Override
    public void setDefaultGrant(Grant grant) {
        this.defaultGrant = grant;
    }


    @Override
    public String getDefaultScope() {
        return this.defaultScope;
    }


    @Override
    public void setDefaultScope(String scope) {
        this.defaultScope = scope;
    }


    @Override
    public Long getDefaultValidityPeriod() {
        return this.defaultValidityPeriod;
    }


    public void setDefaultValidityPeriod(Long validityPeriodInSeconds) {
        this.defaultValidityPeriod = validityPeriodInSeconds;
    }


    @Override
    public boolean isAutoEncryptSecrets() {
        return autoEncryptSecrets;
    }


    @Override
    public void setAutoEncryptSecrets(boolean autoEncryptSecrets) {
        this.autoEncryptSecrets = autoEncryptSecrets;
    }


    @Override
    public String getAutoEncryptionPassword() {
        return autoEncryptionPassword;
    }


    @Override
    public void setAutoEncryptionPassword(String autoEncryptionPassword) {
        this.autoEncryptionPassword = autoEncryptionPassword;
    }


    @Override
    public void setEnvVarPrefix(String envVarPrefix) {
        this.envVarPrefix = envVarPrefix;
    }

    @Override
    public String getEnvVarPrefix() {
        return envVarPrefix;
    }


    @Override
    public void persist() {
        persistConfig(configManager);
    }


    @Override
    public ConfigManager getConfigManager() {
        if (this.configManager == null) {
            configManager = new ConfigManager(this);
        }
        return this.configManager;
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        setGatewayBaseUrl(configManager.readProperty(PropertyKey.GATEWAY_BASE_URL, getGatewayBaseUrl()));
        setPersistenceMode(PersistenceMode.fromString(configManager.readProperty(PropertyKey.PERSISTENCE_MODE, getPersistenceMode().toString())));
        setConsumerKey(configManager.readProperty(PropertyKey.CONSUMER_KEY, getConsumerKey()));
        setConsumerSecret(configManager.readProperty(PropertyKey.CONSUMER_SECRET, getConsumerSecret()));
        String autoEncrypt = configManager.readProperty(PropertyKey.AUTO_ENCRYPT_SECRETS, Boolean.toString(isAutoEncryptSecrets()));
        setAutoEncryptSecrets(Boolean.parseBoolean(autoEncrypt));
        createGrantFromType(configManager);
        if (defaultGrant != null) {
            ConfigPersistence grantConfigPersistence = (ConfigPersistence) defaultGrant;
            grantConfigPersistence.loadConfig(configManager);
        }
    }


    private void createGrantFromType(ConfigManager configManager) {
        try {
            String grantTypeValue = configManager.readProperty(PropertyKey.GRANT_TYPE);
            if (StringUtils.isEmpty(grantTypeValue)) {
                return;
            }

            GrantType grantType = GrantType.fromString(grantTypeValue);
            switch (grantType) {
                case CLIENT_CREDENTIAL:
                    this.defaultGrant = new ClientCredentialBuilder.ClientCredentialGrantBuilder().build(false);
                    break;
                case REFRESH_TOKEN:
                    this.defaultGrant = new Grant.RefreshTokenGrantBuilder().build(false);
                    break;
                case PASSWORD:
                    this.defaultGrant = new Grant.PasswordGrantBuilder().build(false);
                    break;
                case NTLM:
                    this.defaultGrant = new Grant.NtlmGrantBuilder().build(false);
                    break;
                case KERBEROS:
                    this.defaultGrant = new Grant.KerberosGrantBuilder().build(false);
                    break;
                case SAML2:
                    this.defaultGrant = new Grant.Saml2GrantBuilder().build(false);
                    break;
            }

        } catch (EnumParseException e) {
            throw new RuntimeException("Could not create default grant", e);
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.GATEWAY_BASE_URL, getGatewayBaseUrl());
        configManager.saveProperty(PropertyKey.PERSISTENCE_MODE, getPersistenceMode().toString());
        configManager.saveProperty(PropertyKey.CONSUMER_KEY, getConsumerKey());
        configManager.saveProperty(PropertyKey.CONSUMER_SECRET, getConsumerSecret());
        configManager.saveProperty(PropertyKey.AUTO_ENCRYPT_SECRETS, Boolean.toString(isAutoEncryptSecrets()));
        if (defaultGrant != null) {
            configManager.saveProperty(PropertyKey.GRANT_TYPE, defaultGrant.getGrantType().getFormattedValue());
        }

        if (getDefaultGrant() != null) {
            ConfigPersistence grantConfigPersistence = (ConfigPersistence) getDefaultGrant();
            grantConfigPersistence.persistConfig(configManager);
        }
    }


    private void configManagerReinit() {
        if (configManager != null) {
            configManager.setReinit();
        }
    }
}
