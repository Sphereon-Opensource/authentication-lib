package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.GenerateTokenRequestBuilder;
import com.sphereon.libs.authentication.api.RevokeTokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

class AuthenticationApiImpl implements AuthenticationApi {


    private final ApiConfiguration configuration;


    AuthenticationApiImpl(ApiConfiguration configuration) {
        this.configuration = configuration;
    }


    @Override
    public GenerateTokenRequestBuilder.Builder requestToken() {
        return new GenerateTokenRequestBuilderPrivate.Builder(configuration);
    }


    @Override
    public RevokeTokenRequestBuilder.Builder revokeToken() {
        return new RevokeTokenRequestBuilderPrivate.Builder(configuration);
    }
}


