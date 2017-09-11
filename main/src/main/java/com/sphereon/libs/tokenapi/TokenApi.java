package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.config.PersistenceMode;
import com.sphereon.libs.tokenapi.config.PersistenceType;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;

@SuppressWarnings("unused")
public interface TokenApi {

    TokenResponse generateToken(GenerateTokenRequest tokenRequest);

    void revokeToken(RevokeTokenRequest revokeTokenRequest);

    TokenRequestFactory getTokenRequestFactory(String applicationName);

    GrantFactory getGrantFactory(String applicationName);

    TokenApiConfiguration configure(PersistenceType persistenceType, PersistenceMode persistenceMode);

    void persistConfiguration(TokenApiConfiguration tokenApiConfiguration);

}