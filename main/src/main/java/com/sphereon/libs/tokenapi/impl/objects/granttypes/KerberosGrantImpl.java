package com.sphereon.libs.tokenapi.impl.objects.granttypes;

import com.sphereon.commons.objects.AutoHashedObject;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.granttypes.KerberosGrant;
import com.sphereon.libs.tokenapi.impl.objects.BodyParameterKey;
import com.sphereon.libs.tokenapi.impl.BodyParameters;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.config.ConfigPersistence;
import com.sphereon.libs.tokenapi.impl.config.PropertyKey;

import java.util.Map;

public class KerberosGrantImpl extends AutoHashedObject implements KerberosGrant, BodyParameters, ConfigPersistence {

    private String kerberosRealm;

    private String kerberosToken;


    @Override
    public String getKerberosRealm() {
        return kerberosRealm;
    }


    @Override
    public void setKerberosRealm(String kerberosRealm) {
        this.kerberosRealm = kerberosRealm;
    }


    @Override
    public String getKerberosToken() {
        return kerberosToken;
    }


    @Override
    public void setKerberosToken(String kerberosToken) {
        this.kerberosToken = kerberosToken;
    }


    @Override
    public void loadParameters(Map<BodyParameterKey, String> parameterMap) {
        parameterMap.put(BodyParameterKey.GRANT_TYPE, GrantTypeKey.KERBEROS.getValue());
        parameterMap.put(BodyParameterKey.KERBEROS_REALM, getKerberosRealm());
        parameterMap.put(BodyParameterKey.KERBEROS_TOKEN, getKerberosToken());
    }


    @Override
    public void loadConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        setKerberosRealm(configManager.readProperty(tokenApiConfiguration, PropertyKey.KERBEROS_REALM));
        setKerberosToken(configManager.readProperty(tokenApiConfiguration, PropertyKey.KERBEROS_TOKEN));
    }


    @Override
    public void persistConfig(TokenApiConfiguration tokenApiConfiguration, ConfigManager configManager) {
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.KERBEROS_REALM, getKerberosRealm());
        configManager.saveProperty(tokenApiConfiguration, PropertyKey.KERBEROS_TOKEN, getKerberosToken());
    }
}
