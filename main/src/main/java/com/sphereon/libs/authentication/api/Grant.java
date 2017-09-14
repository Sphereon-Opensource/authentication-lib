package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.impl.objects.granttypes.*;

public interface Grant  extends ClientCredentialsBuilder, PasswordBuilder, RefreshTokenBuilder,
        NtlmBuilder, KerberosBuilder {

}
