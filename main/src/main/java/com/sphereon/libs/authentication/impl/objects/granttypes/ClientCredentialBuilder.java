package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialGrant;

public interface ClientCredentialBuilder {

    final class ClientCredentialGrantBuilder implements GrantBuilder {

        public static ClientCredentialGrantBuilder newInstance() {
            return new ClientCredentialGrantBuilder();
        }


        public ClientCredentialGrant build() {
            return build(true);
        }


        public ClientCredentialGrant build(boolean validate) {
            return new ClientCredentialGrantImpl();
        }
    }
}
