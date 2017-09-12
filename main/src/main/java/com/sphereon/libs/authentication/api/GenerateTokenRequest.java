package com.sphereon.libs.authentication.api;


import com.sphereon.libs.authentication.api.granttypes.Grant;

import java.time.Duration;

public interface GenerateTokenRequest extends TokenRequest {

    Grant getGrant();

    Duration getValidityPeriod();

    void setValidityPeriod(Duration validityPeriod);

    String getScope();

    void setScope(String scope);
}
