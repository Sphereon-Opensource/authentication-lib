package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.SAML2Grant;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;

import java.util.Map;

public class SAML2GrantImpl extends AutoHashedObject implements SAML2Grant, BodyParameters, ConfigPersistence {

    private String assertion;


    public String getAssertion() {
        return assertion;
    }


    @Override
    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.SAML2.getValue());
        parameterMap.put(BodyParameterKey.ASSERTION, getAssertion());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        setAssertion(configManager.readProperty(PropertyKey.SAML2_ASSERTION));
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.SAML2_ASSERTION, getAssertion());
    }
}
