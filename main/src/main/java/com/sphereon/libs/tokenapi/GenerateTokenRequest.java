package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.Grant;

import java.time.Duration;

public interface GenerateTokenRequest extends TokenRequest {

    Grant getGrant();

    Duration getValidityPeriod();

    void setValidityPeriod(Duration validityPeriod);

    String getScope();

    void setScope(String scope);
}
