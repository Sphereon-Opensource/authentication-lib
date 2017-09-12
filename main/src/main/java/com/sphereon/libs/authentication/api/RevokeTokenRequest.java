package com.sphereon.libs.authentication.api;

public interface RevokeTokenRequest extends TokenRequest {

    void setToken(String token);

    String getToken();

}
