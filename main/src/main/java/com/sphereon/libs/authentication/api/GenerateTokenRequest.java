package com.sphereon.libs.authentication.api;


import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.objects.GenerateTokenRequestBuilder;

import java.time.Duration;

public interface GenerateTokenRequest extends GenerateTokenRequestBuilder {

    Grant getGrant();

    Duration getValidityPeriod();

    void setValidityPeriod(Duration validityPeriod);

    String getScope();

    void setScope(String scope);


}
