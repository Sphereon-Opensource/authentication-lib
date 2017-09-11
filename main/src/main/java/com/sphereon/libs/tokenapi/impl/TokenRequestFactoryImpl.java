package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.TokenRequestFactory;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.objects.GenerateTokenRequestImpl;
import com.sphereon.libs.tokenapi.impl.objects.RevokeTokenRequestImpl;

public class TokenRequestFactoryImpl implements TokenRequestFactory {

    private final String application;
    private final ConfigManager configManager;
    private final TokenApiConfiguration tokenApiConfiguration;


    public TokenRequestFactoryImpl(String application, ConfigManager configManager, TokenApiConfiguration tokenApiConfiguration) {
        this.application = application;
        this.configManager = configManager;
        this.tokenApiConfiguration = tokenApiConfiguration;
    }


    @Override
    public GenerateTokenRequest constructGenerateTokenRequest(Grant grant) {
        GenerateTokenRequestImpl generateTokenRequest = new GenerateTokenRequestImpl(application, grant);
        configManager.loadTokenRequest(tokenApiConfiguration, generateTokenRequest);
        return generateTokenRequest;
    }


    @Override
    public RevokeTokenRequest constructRevokeTokenRequest() {
        RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl(application);
        configManager.loadTokenRequest(tokenApiConfiguration, revokeTokenRequest);
        return revokeTokenRequest;
    }
}
