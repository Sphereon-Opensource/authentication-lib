package com.sphereon.libs.authentication.api.granttypes;

public interface KerberosGrant extends Grant {

    void setKerberosRealm(String kerberosRealm);

    void setKerberosToken(String kerberosToken);
}
