package com.sphereon.libs.tokenapi;

public interface RevokeTokenRequest extends TokenRequest {

    void setToken(String token);

    String getToken();

}
