package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.Grant;

public interface TokenRequestFactory {

    GenerateTokenRequest constructGenerateTokenRequest(Grant grant);

    RevokeTokenRequest constructRevokeTokenRequest();

}
