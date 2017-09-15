package com.sphereon.libs.authentication.api.config;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ApiConfigurator;

import java.time.Duration;

public interface ApiConfiguration extends ApiConfigurator {

    String getApplication();

    void setApplication(String defaultApplication);

    String getGatewayBaseUrl();

    void setGatewayBaseUrl(String gatewayBaseUrl);

    PersistenceType getPersistenceType();

    void setPersistenceType(PersistenceType persistenceType);

    PersistenceMode getPersistenceMode();

    void setPersistenceMode(PersistenceMode persistenceMode);

    String getStandalonePropertyFilePath();

    String getDefaultScope();

    void setDefaultScope(String scope);

    Duration getDefaultValidityPeriod();

    void setDefaultValidityPeriod(Duration validityPeriod);

    void setStandalonePropertyFilePath(String standaloneConfigPath);

    String getConsumerKey();

    void setConsumerKey(String consumerKey);

    String getConsumerSecret();

    void setConsumerSecret(String consumerSecret);

    Grant getDefaultGrant();

    void setDefaultGrant(Grant grant);

    boolean isAutoEncryptSecrets();

    void setAutoEncryptSecrets(boolean autoEncryptSecrets);

    void persist();
}
