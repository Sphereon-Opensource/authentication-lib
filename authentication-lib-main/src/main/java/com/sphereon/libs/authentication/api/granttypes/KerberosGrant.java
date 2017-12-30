package com.sphereon.libs.authentication.api.granttypes;

public interface KerberosGrant extends Grant {

    String getKerberosRealm();

    KerberosGrant setKerberosRealm(String kerberosRealm);

    String getKerberosToken();

    KerberosGrant setKerberosToken(String kerberosToken);
}
