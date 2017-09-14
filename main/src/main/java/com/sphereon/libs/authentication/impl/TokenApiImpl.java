package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.GenerateTokenRequestBuilder;
import com.sphereon.libs.authentication.api.RevokeTokenRequestBuilder;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilderPrivate;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilderPrivate;

@SuppressWarnings("PackageAccessibility")
class TokenApiImpl implements TokenApi {


    private final ConfigManager configManager;


    TokenApiImpl(TokenApiConfiguration tokenApiConfiguration) {
        configManager = new ConfigManager(tokenApiConfiguration);
    }


    @Override
    public GenerateTokenRequestBuilder.Builder requestToken() {
        return new GenerateTokenRequestBuilderPrivate.Builder(configManager);
    }


    @Override
    public RevokeTokenRequestBuilder.Builder revokeToken() {
        return new RevokeTokenRequestBuilderPrivate.Builder(configManager);
    }


    @Override
    public TokenApiConfiguration.Configurator reconfigure() {
        return new TokenApiConfiguration.Configurator(configManager);
    }

}


