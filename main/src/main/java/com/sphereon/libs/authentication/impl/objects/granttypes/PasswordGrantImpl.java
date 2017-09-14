package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class PasswordGrantImpl extends AutoHashedObject implements PasswordGrant, RequestParameters, ConfigPersistence {

    private String userName;

    private String password;


    PasswordGrantImpl() {
    }


    @Override
    public String getUserName() {
        return userName;
    }


    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public void setPassword(String password) {
        this.password = password;
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
}
