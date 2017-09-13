package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.TokenApiPrivate;

@SuppressWarnings("unused")
public interface TokenApi extends TokenApiPrivate {

    GenerateTokenRequestBuilder.Builder requestToken();

    RevokeTokenRequestBuilder.Builder revokeToken();

    GrantBuilder.Builder grantBuilder();

    TokenApiConfiguration.Configurator reconfigure();

}