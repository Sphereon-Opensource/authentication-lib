package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.impl.BodyParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.BodyParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PasswordGrantImpl extends AutoHashedObject implements PasswordGrant, BodyParameters, ConfigPersistence {

    private String userName;

    private String password;


    public String getUserName() {
        return userName;
    }


    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.PASSWORD.getValue());
        parameterMap.put(BodyParameterKey.USER_NAME, getUserName());
        parameterMap.put(BodyParameterKey.PASSWORD, getPassword());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isNotEmpty(getUserName())) {
            setUserName(configManager.readProperty(PropertyKey.USER_NAME));
        }
        if (StringUtils.isNotEmpty(getPassword())) {
            setPassword(configManager.readProperty(PropertyKey.PASSWORD));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.USER_NAME, getUserName());
        configManager.saveProperty(PropertyKey.PASSWORD, getPassword());
    }
}