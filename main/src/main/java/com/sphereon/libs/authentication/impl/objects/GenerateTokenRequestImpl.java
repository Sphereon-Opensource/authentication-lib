package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.Map;

class GenerateTokenRequestImpl extends TokenRequestImpl implements TokenRequest, RequestParameters {

    protected final Grant grant;

    protected Duration validityPeriod;

    protected String scope;


    GenerateTokenRequestImpl(Grant grant) {
        this.grant = grant;
    }


    public Grant getGrant() {
        return grant;
    }


    public Duration getValidityPeriod() {
        return validityPeriod;
    }


    public void setValidityPeriod(Duration validityPeriod) {
        this.validityPeriod = validityPeriod;
    }


    public String getScope() {
        return scope;
    }


    public void setScope(String scope) {
        this.scope = scope;
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        setScope(configManager.readProperty(PropertyKey.SCOPE));
        String validityPeriod = configManager.readProperty(PropertyKey.VALIDITY_PERIOD);
        if (StringUtils.isNotBlank(validityPeriod)) {
            setValidityPeriod(Duration.parse(validityPeriod));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        configManager.saveProperty(PropertyKey.SCOPE, getScope());
        if (getValidityPeriod() != null) {
            configManager.saveProperty(PropertyKey.VALIDITY_PERIOD, getValidityPeriod().toString());
        }
        if (getGrant() != null && getGrant() instanceof ConfigPersistence) {
            ((ConfigPersistence) getGrant()).persistConfig(configManager);
        }
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        super.bodyParameters(parameterMap);
        if (StringUtils.isNotBlank(getScope())) {
            parameterMap.put(RequestParameterKey.SCOPE, getScope());
        }
        if (getValidityPeriod() != null) {
            parameterMap.put(RequestParameterKey.VALIDITY_PERIOD, "" + getValidityPeriod().getSeconds());
        }
    }
}