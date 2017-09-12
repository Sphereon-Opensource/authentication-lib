package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilder;

public interface RevokeTokenRequest extends RevokeTokenRequestBuilder {

    void setToken(String token);

    String getToken();

}
