package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.PasswordGrant;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PasswordGrantImpl extends AutoHashedObject implements PasswordGrant, BodyParameters, ConfigPersistence {

    private String userName;

    private String password;


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
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.PASSWORD.getValue());
        parameterMap.put(BodyParameterKey.USER_NAME, getUserName());
        parameterMap.put(BodyParameterKey.PASSWORD, getPassword());
    }


    @Override
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        if (StringUtils.isNotEmpty(getUserName())) {
            setUserName(configManager.readProperty(tokenApiConfiguration, PropertyKey.USER_NAME));
        }
        if (StringUtils.isNotEmpty(getPassword())) {
            setPassword(configManager.readProperty(tokenApiConfiguration, PropertyKey.PASSWORD));
        }
    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.USER_NAME, getUserName());
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.PASSWORD, getPassword());
    }
}
