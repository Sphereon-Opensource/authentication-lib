package com.sphereon.libs.tokenapi.impl.objects;


import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.TokenRequest;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

import java.util.Map;

public abstract class TokenRequestImpl extends AutoHashedObject implements TokenRequest, BodyParameters, ConfigPersistence {

    private final String application;

    private String consumerKey;

    private transient String consumerSecret;


    protected TokenRequestImpl(String application) {
        this.application = application;
    }


    @Override
    public String getApplication() {
        return application;
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
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {

    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.CONSUMER_KEY, getConsumerKey());
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.CONSUMER_SECRET, getConsumerSecret());
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {

    }
}
