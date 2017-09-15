package com.sphereon.libs.authentication.impl.config;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.impl.commons.exceptions.EnumParseException;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

class ApiConfigurationImpl implements ApiConfiguration, ConfigPersistence, ConfigManagerProvider {

    private static final String DEFAULT_GATEWAY_URL = "https://gw.api.cloud.sphereon.com/";

    private String application = "default-application";

    private PersistenceType persistenceType;

    private PersistenceMode persistenceMode = PersistenceMode.READ_ONLY;

    private String gatewayBaseUrl = DEFAULT_GATEWAY_URL;

    private String standalonePropertyFilePath;

    private Grant defaultGrant;

    private String consumerKey;

    private String consumerSecret;

    private String defaultScope;

    private Duration defaultValidityPeriod;

    private boolean autoEncryptSecrets;

    private ConfigManager configManager;


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
    public String getStandalonePropertyFilePath() {
        return standalonePropertyFilePath;
    }


    @Override
    public void setStandalonePropertyFilePath(String standalonePropertyFilePath) {
        this.standalonePropertyFilePath = standalonePropertyFilePath;
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
    public Duration getDefaultValidityPeriod() {
        return this.defaultValidityPeriod;
    }


    @Override
    public void setDefaultValidityPeriod(Duration validityPeriod) {
        this.defaultValidityPeriod = validityPeriod;
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
                case CLIENT_CREDENTIALS:
                    this.defaultGrant = new Grant.ClientCredentialsGrantBuilder().build(false);
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

        } catch (EnumParseException ignored) {
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
