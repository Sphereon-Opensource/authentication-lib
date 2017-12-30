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
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class PasswordGrantImpl implements PasswordGrant, RequestParameters, ConfigPersistence {

    private String userName;

    private String password;


    PasswordGrantImpl() {
    }


    @Override
    public String getUserName() {
        return userName;
    }


    @Override
    public PasswordGrant setUserName(String userName) {
        this.userName = userName;
        return this;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public PasswordGrant setPassword(String password) {
        this.password = password;
        return this;
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {
    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.PASSWORD.getValue());
        parameterMap.put(RequestParameterKey.USER_NAME, getUserName());
        parameterMap.put(RequestParameterKey.PASSWORD, getPassword());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isEmpty(getUserName())) {
            setUserName(configManager.readProperty(PropertyKey.USER_NAME));
        }
        if (StringUtils.isEmpty(getPassword())) {
            setPassword(configManager.readProperty(PropertyKey.PASSWORD));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.USER_NAME, getUserName());
        configManager.saveProperty(PropertyKey.PASSWORD, getPassword());
    }


    @Override
    public GrantType getGrantType() {
        return GrantType.PASSWORD;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PasswordGrantImpl)) {
            return false;
        }

        PasswordGrantImpl that = (PasswordGrantImpl) o;

        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null) {
            return false;
        }
        return getPassword() != null ? getPassword().equals(that.getPassword()) : that.getPassword() == null;
    }


    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }
}
