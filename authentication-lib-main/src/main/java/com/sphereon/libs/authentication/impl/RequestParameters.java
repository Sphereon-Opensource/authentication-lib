package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;

import java.util.Map;

public interface RequestParameters {

    void headerParameters(Map<RequestParameterKey, String> parameterMap);

    void bodyParameters(Map<RequestParameterKey, String> parameterMap);
}
