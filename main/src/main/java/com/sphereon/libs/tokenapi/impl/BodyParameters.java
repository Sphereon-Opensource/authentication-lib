package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;

import java.util.Map;

public interface BodyParameters {

    void loadParameters(Map<BodyParameterKey, String> parameterMap);
}
