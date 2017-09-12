package com.sphereon.libs.authentication.impl.objects;


import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;

import java.util.Map;

public abstract class TokenRequestImpl extends AutoHashedObject implements TokenRequest, BodyParameters, ConfigPersistence {

    private String consumerKey;

    private transient String consumerSecret;


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
    public void loadConfig(ConfigManager configManager) {

    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.CONSUMER_KEY, getConsumerKey());
        configManager.saveProperty(PropertyKey.CONSUMER_SECRET, getConsumerSecret());
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
    }
}
