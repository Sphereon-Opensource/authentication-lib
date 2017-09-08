package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.RevokeTokenRequest;

public class RevokeTokenRequestImpl extends TokenRequestImpl implements RevokeTokenRequest {

    protected String token;


    public RevokeTokenRequestImpl(String application) {
        super(application);
    }


    @Override
    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String getToken() {
        return token;
    }


    @Override
    public boolean isPreconfigured() {
        return false;
    }
}
