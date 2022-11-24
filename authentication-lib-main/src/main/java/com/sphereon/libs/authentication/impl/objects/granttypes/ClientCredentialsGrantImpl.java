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

import com.sphereon.libs.authentication.api.config.ClientCredentialsMode;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialGrant;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

class ClientCredentialGrantImpl implements ClientCredentialGrant, RequestParameters, ConfigPersistence {

    private ClientCredentialsMode clientCredentialsMode;
    private String clientId;

    private String clientSecret;


    ClientCredentialGrantImpl() {
    }

    public ClientCredentialGrantImpl(final ClientCredentialsMode clientCredentialsMode, final String clientId, final String clientSecret) {
        setClientCredentialsMode(clientCredentialsMode);
        setClientId(clientId);
        setClientSecret(clientSecret);
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.CLIENT_CREDENTIALS.getValue());
        if(clientCredentialsMode == ClientCredentialsMode.BODY) {
            final String clientId = getClientId();
            if (StringUtils.isNotBlank(clientId)) {
                parameterMap.put(RequestParameterKey.CLIENT_ID, clientId);
            }
            if (StringUtils.isNotBlank(clientSecret)) {
                parameterMap.put(RequestParameterKey.CLIENT_SECRET, getClientSecret());
            }
        }
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
    }


    @Override
    public GrantType getGrantType() {
        return GrantType.CLIENT_CREDENTIAL;
    }

    public ClientCredentialsMode getClientCredentialsMode() {
        return clientCredentialsMode;
    }

    public void setClientCredentialsMode(final ClientCredentialsMode clientCredentialsMode) {
        this.clientCredentialsMode = clientCredentialsMode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientCredentialGrantImpl)) return false;

        final ClientCredentialGrantImpl that = (ClientCredentialGrantImpl) o;

        if (!Objects.equals(clientId, that.clientId)) return false;
        return Objects.equals(clientSecret, that.clientSecret);
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        return result;
    }
}
