package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.*;

public interface Grant extends ClientCredentialBuilder, PasswordBuilder, RefreshTokenBuilder,
        NtlmBuilder, KerberosBuilder, Saml2Builder {

    GrantType getGrantType();

}
