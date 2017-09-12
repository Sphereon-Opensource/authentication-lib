package com.sphereon.libs.tokenapi.impl.objects;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.Map;

public class GenerateTokenRequestImpl extends TokenRequestImpl implements GenerateTokenRequest, BodyParameters {

    protected final Grant grant;

    protected Duration validityPeriod;

    protected String scope;


    public GenerateTokenRequestImpl(Grant grant) {
        this.grant = grant;
    }


    @Override
    public Grant getGrant() {
        return grant;
    }


    @Override
    public Duration getValidityPeriod() {
        return validityPeriod;
    }


    @Override
    public void setValidityPeriod(Duration validityPeriod) {
        this.validityPeriod = validityPeriod;
    }


    @Override
    public String getScope() {
        return scope;
    }


    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        setScope(configManager.readProperty(PropertyKey.SCOPE));
        setValidityPeriod(Duration.parse(configManager.readProperty(PropertyKey.VALIDITY_PERIOD)));
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        super.loadConfig(configManager);
        configManager.saveProperty(PropertyKey.SCOPE, getScope());
        if (getValidityPeriod() != null) {
            configManager.saveProperty(PropertyKey.VALIDITY_PERIOD, getValidityPeriod().toString());
        }
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        super.loadParameters(parameterMap);
        if (StringUtils.isNotBlank(getScope())) {
            parameterMap.put(BodyParameterKey.SCOPE, getScope());
        }
        if (getValidityPeriod() != null) {
            parameterMap.put(BodyParameterKey.VALIDITY_PERIOD, "" + getValidityPeriod().getSeconds());
        }
    }
}