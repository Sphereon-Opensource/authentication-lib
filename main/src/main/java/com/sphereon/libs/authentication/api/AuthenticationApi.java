package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.impl.AuthenticationApiPrivate;

@SuppressWarnings("unused")
public interface AuthenticationApi extends AuthenticationApiPrivate {

    GenerateTokenRequestBuilder.Builder requestToken();

    RevokeTokenRequestBuilder.Builder revokeToken();

}