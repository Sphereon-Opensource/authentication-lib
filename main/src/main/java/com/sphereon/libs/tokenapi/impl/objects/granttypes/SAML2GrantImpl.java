package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.SAML2Grant;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

import java.util.Map;

public class SAML2GrantImpl extends AutoHashedObject implements SAML2Grant, BodyParameters, ConfigPersistence {

    private String assertion;


    @Override
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
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        setAssertion(configManager.readProperty(tokenApiConfiguration, PropertyKey.SAML2_ASSERTION));
    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.SAML2_ASSERTION, getAssertion());
    }
}
