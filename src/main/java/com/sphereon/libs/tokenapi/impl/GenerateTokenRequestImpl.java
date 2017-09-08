package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.granttypes.Grant;

import java.time.Duration;

public class GenerateTokenRequestImpl extends TokenRequestImpl implements GenerateTokenRequest {

    protected final Grant grant;

    protected Duration validityPeriod;

    protected String scope;

    private boolean preconfigured;


    public GenerateTokenRequestImpl(String application, Grant grant) {
        super(application);
        this.grant = grant;
    }


    @Override
    public Grant getGrant() {
        return grant;
    }


    @Override
    public Duration getValidityPeriod() {
        return validityPeriod;
    }


    @Override
    public void setValidityPeriod(Duration validityPeriod) {
        this.validityPeriod = validityPeriod;
    }


    @Override
    public String getScope() {
        return scope;
    }


    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }


    @Override
    public boolean isPreconfigured() {
        return preconfigured;
    }


    void setPreconfigured(boolean preconfigured) {
        this.preconfigured = preconfigured;
    }
}
