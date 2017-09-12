package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.NtlmGrant;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;

import java.util.Map;

public class NtlmGrantImpl extends AutoHashedObject implements NtlmGrant, BodyParameters, ConfigPersistence {

    private String windowsToken;


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