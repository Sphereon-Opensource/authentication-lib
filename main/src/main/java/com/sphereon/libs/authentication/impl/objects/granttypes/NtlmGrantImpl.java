package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.api.granttypes.NtlmGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class NtlmGrantImpl extends AutoHashedObject implements NtlmGrant, RequestParameters, ConfigPersistence {

    private String windowsToken;


    NtlmGrantImpl() {
    }


    @Override
    public String getWindowsToken() {
        return windowsToken;
    }


    @Override
    public void setWindowsToken(String windowsToken) {
        this.windowsToken = windowsToken;
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.NTLM.getValue());
        parameterMap.put(RequestParameterKey.WINDOWS_TOKEN, getWindowsToken());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isEmpty(getWindowsToken())) {
            setWindowsToken(configManager.readProperty(PropertyKey.WINDOWS_TOKEN));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.WINDOWS_TOKEN, getWindowsToken());
    }


    @Override
    public GrantType getGrantType() {
        return GrantType.NTLM;
    }
}