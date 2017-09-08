package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.*;

@SuppressWarnings("unused")
public interface TokenApi {

    TokenResponse generateToken(GenerateTokenRequest tokenRequest);

    void revokeToken(RevokeTokenRequest revokeTokenRequest);

    GenerateTokenRequest newGenerateTokenRequest(String application, Grant grant);

    RevokeTokenRequest newRevokeTokenRequest(String application);

    ClientCredentialsGrant newClientCredentialsGrant();

    PasswordGrant newPasswordGrant();

    RefreshTokenGrant newRefreshTokenGrant();

    NtlmGrant newNtlmGrant();

    KerberosGrant newKerberosGrant();

    SAML2Grant newSAML2Grant();
}
