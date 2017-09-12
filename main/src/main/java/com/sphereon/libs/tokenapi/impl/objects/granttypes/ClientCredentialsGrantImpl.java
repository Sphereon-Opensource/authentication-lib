package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;

import java.util.Map;

public class ClientCredentialsGrantImpl extends AutoHashedObject implements ClientCredentialsGrant, BodyParameters, ConfigPersistence {

    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.CLIENT_CREDENTIALS.getValue());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
    }
}
