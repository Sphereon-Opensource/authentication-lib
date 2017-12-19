package com.sphereon.libs.authentication.api;

public interface TokenRequest {

    TokenResponse execute();

    void addTokenResponseListener(TokenResponseListener listener);

    void removeTokenResponseListener(TokenResponseListener listener);

    interface TokenResponseListener {
        void tokenResponse(TokenResponse tokenResponse);
    }
}
