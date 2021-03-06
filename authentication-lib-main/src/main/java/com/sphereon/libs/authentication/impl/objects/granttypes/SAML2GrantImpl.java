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
import com.sphereon.libs.authentication.api.granttypes.SAML2Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class SAML2GrantImpl implements SAML2Grant, RequestParameters, ConfigPersistence {

    private String assertion;


    @Override
    public String getAssertion() {
        return assertion;
    }


    @Override
    public SAML2Grant setAssertion(String assertion) {
        this.assertion = assertion;
        return this;
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


    @Override
    public GrantType getGrantType() {
        return GrantType.SAML2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SAML2GrantImpl)) {
            return false;
        }

        SAML2GrantImpl that = (SAML2GrantImpl) o;

        return getAssertion() != null ? getAssertion().equals(that.getAssertion()) : that.getAssertion() == null;
    }


    @Override
    public int hashCode() {
        return getAssertion() != null ? getAssertion().hashCode() : 0;
    }
}
