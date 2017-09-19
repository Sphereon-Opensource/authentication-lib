package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilder;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilder;

class AuthenticationApiImpl implements AuthenticationApi {


    private final ApiConfiguration configuration;


    AuthenticationApiImpl(ApiConfiguration configuration) {
        this.configuration = configuration;
    }


    @Override
    public com.sphereon.libs.authentication.api.GenerateTokenRequestBuilder.Builder requestToken() {
        return new GenerateTokenRequestBuilder.Builder(configuration);
    }


    @Override
    public com.sphereon.libs.authentication.api.RevokeTokenRequestBuilder.Builder revokeToken() {
        return new RevokeTokenRequestBuilder.Builder(configuration);
    }
}


