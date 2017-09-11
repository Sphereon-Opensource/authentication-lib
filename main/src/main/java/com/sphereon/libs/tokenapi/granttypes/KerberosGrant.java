package com.sphereon.libs.tokenapi.granttypes;

public interface KerberosGrant extends Grant {
    String getKerberosRealm();

    void setKerberosRealm(String kerberosRealm);

    String getKerberosToken();

    void setKerberosToken(String kerberosToken);
}
