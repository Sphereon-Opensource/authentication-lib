package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.*;

public interface GrantFactory {

    ClientCredentialsGrant clientCredentialsGrant();

    PasswordGrant passwordGrant();

    RefreshTokenGrant refreshTokenGrant();

    NtlmGrant ntlmGrant();

    KerberosGrant kerberosGrant();

    SAML2Grant saml2Grant();
}
