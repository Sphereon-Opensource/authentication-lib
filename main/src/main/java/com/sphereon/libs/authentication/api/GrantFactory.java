package com.sphereon.libs.authentication.api;


import com.sphereon.libs.authentication.api.granttypes.*;

public interface GrantFactory {

    ClientCredentialsGrant createClientCredentialsGrant();

    PasswordGrant createPasswordGrant();

    PasswordGrant.Builder createPasswordGrantBuilder(); // Example / tryout

    RefreshTokenGrant createRefreshTokenGrant();

    NtlmGrant createNtlmGrant();

    KerberosGrant createKerberosGrant();

    SAML2Grant createSaml2Grant();


}
