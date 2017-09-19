package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.GrantType;
import com.sphereon.libs.authentication.api.granttypes.KerberosGrant;
import com.sphereon.libs.authentication.impl.RequestParameters;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.config.PropertyKey;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

class KerberosGrantImpl implements KerberosGrant, RequestParameters, ConfigPersistence {

    private String kerberosRealm;

    private String kerberosToken;


    KerberosGrantImpl() {
    }


    @Override
    public String getKerberosRealm() {
        return kerberosRealm;
    }


    @Override
    public KerberosGrant setKerberosRealm(String kerberosRealm) {
        this.kerberosRealm = kerberosRealm;
        return this;
    }


    @Override
    public String getKerberosToken() {
        return kerberosToken;
    }


    @Override
    public KerberosGrant setKerberosToken(String kerberosToken) {
        this.kerberosToken = kerberosToken;
        return this;
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


    @Override
    public GrantType getGrantType() {
        return GrantType.KERBEROS;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KerberosGrantImpl)) {
            return false;
        }

        KerberosGrantImpl that = (KerberosGrantImpl) o;

        if (getKerberosRealm() != null ? !getKerberosRealm().equals(that.getKerberosRealm()) : that.getKerberosRealm() != null) {
            return false;
        }
        return getKerberosToken() != null ? getKerberosToken().equals(that.getKerberosToken()) : that.getKerberosToken() == null;
    }


    @Override
    public int hashCode() {
        int result = getKerberosRealm() != null ? getKerberosRealm().hashCode() : 0;
        result = 31 * result + (getKerberosToken() != null ? getKerberosToken().hashCode() : 0);
        return result;
    }
}
