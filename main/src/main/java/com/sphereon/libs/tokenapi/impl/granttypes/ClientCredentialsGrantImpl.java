package com.sphereon.libs.tokenapi.impl.granttypes;

import com.sphereon.libs.tokenapi.granttypes.ClientCredentialsGrant;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientCredentialsGrantImpl implements ClientCredentialsGrant {
    @Override
    public Map<String, String> getParameters() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("grant_type", "client_credentials");
        return map;
    }
}
