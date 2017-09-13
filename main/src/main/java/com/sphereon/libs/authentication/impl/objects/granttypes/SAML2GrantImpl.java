package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class SAML2GrantImpl extends AutoHashedObject implements Grant, RequestParameters, ConfigPersistence {

    private String assertion;


    public String getAssertion() {
        return assertion;
    }


    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.SAML2.getValue());
        parameterMap.put(RequestParameterKey.ASSERTION, getAssertion());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isEmpty(getAssertion())) {
            setAssertion(configManager.readProperty(PropertyKey.SAML2_ASSERTION));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.SAML2_ASSERTION, getAssertion());
    }
}
