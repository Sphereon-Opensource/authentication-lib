package com.sphereon.libs.tokenapi.impl;


import com.sphereon.libs.tokenapi.TokenRequest;

public abstract class TokenRequestImpl implements TokenRequest {

    protected final String application;

    protected String consumerKey;

    protected transient String consumerSecret;


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
}
