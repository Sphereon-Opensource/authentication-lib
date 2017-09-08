package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

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


    @Override
    public Map<String, String> getParameters() {
        Map<String, String> map = new TreeMap<>();
        if (StringUtils.isNotBlank(getToken())) {
            map.put("token", getToken());
        }
        return map;
    }
}
