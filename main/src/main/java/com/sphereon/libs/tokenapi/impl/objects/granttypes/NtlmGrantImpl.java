package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.NtlmGrant;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

import java.util.Map;

public class NtlmGrantImpl extends AutoHashedObject implements NtlmGrant, BodyParameters, ConfigPersistence {

    private String windowsToken;


    @Override
    public String getWindowsToken() {
        return windowsToken;
    }


    @Override
    public void setWindowsToken(String windowsToken) {
        this.windowsToken = windowsToken;
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.NTLM.getValue());
        parameterMap.put(BodyParameterKey.WINDOWS_TOKEN, getWindowsToken());
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