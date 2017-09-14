package com.sphereon.libs.authentication.api.granttypes;

public interface KerberosGrant extends Grant {

    String getKerberosRealm();

    void setKerberosRealm(String kerberosRealm);

    String getKerberosToken();

    void setKerberosToken(String kerberosToken);
}
