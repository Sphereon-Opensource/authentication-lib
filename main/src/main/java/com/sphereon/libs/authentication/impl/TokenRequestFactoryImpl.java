package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.GenerateTokenRequest;
import com.sphereon.libs.authentication.api.RevokeTokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestFactory;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestImpl;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestImpl;

public class TokenRequestFactoryImpl implements TokenRequestFactory {

    private final ConfigManager configManager;


    public TokenRequestFactoryImpl(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public GenerateTokenRequest createGenerateTokenRequest(Grant grant) {
        GenerateTokenRequestImpl generateTokenRequest = new GenerateTokenRequestImpl(grant);
        configManager.loadTokenRequest(generateTokenRequest);
        return generateTokenRequest;
    }


    @Override
    public RevokeTokenRequest createRevokeTokenRequest() {
        RevokeTokenRequestImpl revokeTokenRequest = new RevokeTokenRequestImpl();
        configManager.loadTokenRequest(revokeTokenRequest);
        return revokeTokenRequest;
    }
}
