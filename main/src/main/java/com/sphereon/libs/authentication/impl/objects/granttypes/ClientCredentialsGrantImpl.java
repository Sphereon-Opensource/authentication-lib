package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;

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
