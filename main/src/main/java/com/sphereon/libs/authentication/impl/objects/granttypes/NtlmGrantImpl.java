package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;

import java.util.Map;

class NtlmGrantImpl extends AutoHashedObject implements Grant, RequestParameters, ConfigPersistence {

    private String windowsToken;


    NtlmGrantImpl() {
    }


    public String getWindowsToken() {
        return windowsToken;
    }


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
        setWindowsToken(configManager.readProperty(PropertyKey.WINDOWS_TOKEN));
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.WINDOWS_TOKEN, getWindowsToken());
    }
}