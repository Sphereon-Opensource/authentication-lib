package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class KerberosGrantImpl extends AutoHashedObject implements Grant, RequestParameters, ConfigPersistence {

    private String kerberosRealm;

    private String kerberosToken;


    KerberosGrantImpl() {
    }


    public String getKerberosRealm() {
        return kerberosRealm;
    }


    public void setKerberosRealm(String kerberosRealm) {
        this.kerberosRealm = kerberosRealm;
    }


    public String getKerberosToken() {
        return kerberosToken;
    }


    public void setKerberosToken(String kerberosToken) {
        this.kerberosToken = kerberosToken;
    }


    @Override
    public void headerParameters(Map<RequestParameterKey, String> parameterMap) {

    }


    @Override
    public void bodyParameters(Map<RequestParameterKey, String> parameterMap) {
        parameterMap.put(RequestParameterKey.GRANT_TYPE, GrantTypeKey.KERBEROS.getValue());
        parameterMap.put(RequestParameterKey.KERBEROS_REALM, getKerberosRealm());
        parameterMap.put(RequestParameterKey.KERBEROS_TOKEN, getKerberosToken());
    }


    @Override
    public void loadConfig(ConfigManager configManager) {
        if (StringUtils.isEmpty(getKerberosRealm())) {
            setKerberosRealm(configManager.readProperty(PropertyKey.KERBEROS_REALM));
        }
        if (StringUtils.isEmpty(getKerberosToken())) {
            setKerberosToken(configManager.readProperty(PropertyKey.KERBEROS_TOKEN));
        }
    }


    @Override
    public void persistConfig(ConfigManager configManager) {
        configManager.saveProperty(PropertyKey.KERBEROS_REALM, getKerberosRealm());
        configManager.saveProperty(PropertyKey.KERBEROS_TOKEN, getKerberosToken());
    }
}
