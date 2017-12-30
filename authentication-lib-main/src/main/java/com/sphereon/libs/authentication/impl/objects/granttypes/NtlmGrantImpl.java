/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.api.granttypes.NtlmGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class NtlmGrantImpl implements NtlmGrant, RequestParameters, ConfigPersistence {

    private String windowsToken;


    NtlmGrantImpl() {
    }


    @Override
    public String getWindowsToken() {
        return windowsToken;
    }


    @Override
    public NtlmGrant setWindowsToken(String windowsToken) {
        this.windowsToken = windowsToken;
        return this;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtlmGrantImpl)) {
            return false;
        }

        NtlmGrantImpl ntlmGrant = (NtlmGrantImpl) o;

        return getWindowsToken() != null ? getWindowsToken().equals(ntlmGrant.getWindowsToken()) : ntlmGrant.getWindowsToken() == null;
    }


    @Override
    public int hashCode() {
        return getWindowsToken() != null ? getWindowsToken().hashCode() : 0;
    }
}