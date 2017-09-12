package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;

import java.util.Map;

public interface BodyParameters {

    void loadParameters(Map<BodyParameterKey, String> parameterMap);
}
