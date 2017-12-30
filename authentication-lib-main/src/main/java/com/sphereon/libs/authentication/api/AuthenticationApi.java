package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.impl.AuthenticationApiBuilder;

@SuppressWarnings("unused")
public interface AuthenticationApi extends AuthenticationApiBuilder {

    GenerateTokenRequestBuilder.Builder requestToken();

    RevokeTokenRequestBuilder.Builder revokeToken();

}