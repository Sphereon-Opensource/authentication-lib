package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.KerberosBuilder;

public interface KerberosGrant extends KerberosBuilder {

    void setKerberosRealm(String kerberosRealm);

    void setKerberosToken(String kerberosToken);
}
