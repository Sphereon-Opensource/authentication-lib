package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.TokenRequestFactory;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.objects.GenerateTokenRequestImpl;
import com.sphereon.libs.tokenapi.impl.objects.RevokeTokenRequestImpl;

public class TokenRequestFactoryImpl implements TokenRequestFactory {

    private final ConfigManager configManager;


    public TokenRequestFactoryImpl(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public GenerateTokenRequest constructGenerateTokenRequest(Grant grant) {
        GenerateTokenRequestImpl generateTokenRequest = new GenerateTokenRequestImpl(grant);
        configManager.loadTokenRequest(generateTokenRequest);
        return generateTokenRequest;
    }


    @Override
    public RevokeTokenRequest constructRevokeTokenRequest() {
        RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl();
        configManager.loadTokenRequest(revokeTokenRequest);
        return revokeTokenRequest;
    }
}
