package com.sphereon.libs.authentication.api;

public interface TokenRequestBuilder<T extends TokenRequest> {

    T build();

}
