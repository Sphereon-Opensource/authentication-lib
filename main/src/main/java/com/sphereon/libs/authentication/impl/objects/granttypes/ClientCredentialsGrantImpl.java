package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.ClientCredentialGrant;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;

import java.util.Map;

class ClientCredentialGrantImpl implements ClientCredentialGrant, RequestParameters, ConfigPersistence {

    ClientCredentialGrantImpl() {
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.CLIENT_CREDENTIALS.getValue());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
    }


    @Override
    public GrantType getGrantType() {
        return GrantType.CLIENT_CREDENTIALS;
    }

}
