package com.sphereon.libs.authentication.api;


import com.sphereon.libs.authentication.api.granttypes.Grant;

public interface TokenRequestFactory {

    GenerateTokenRequest createGenerateTokenRequest(Grant grant);

    RevokeTokenRequest createRevokeTokenRequest();

}
