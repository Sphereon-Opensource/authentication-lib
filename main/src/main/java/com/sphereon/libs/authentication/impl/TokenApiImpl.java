package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.GenerateTokenRequestBuilder;
import com.sphereon.libs.authentication.api.RevokeTokenRequestBuilder;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

@SuppressWarnings("PackageAccessibility")
class TokenApiImpl implements TokenApi {


    private final TokenApiConfiguration tokenApiConfiguration;


    TokenApiImpl(TokenApiConfiguration tokenApiConfiguration) {
        this.tokenApiConfiguration = tokenApiConfiguration;
    }


    @Override
    public GenerateTokenRequestBuilder.Builder requestToken() {
        return new GenerateTokenRequestBuilderPrivate.Builder(tokenApiConfiguration);
    }


    @Override
    public RevokeTokenRequestBuilder.Builder revokeToken() {
        return new RevokeTokenRequestBuilderPrivate.Builder(tokenApiConfiguration);
    }
}


