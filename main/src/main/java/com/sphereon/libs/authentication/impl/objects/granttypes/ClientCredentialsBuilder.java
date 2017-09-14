package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialsGrant;

public interface ClientCredentialsBuilder {

    final class ClientCredentialsGrantBuilder implements GrantBuilder {

        public static ClientCredentialsGrantBuilder newInstance() {
            return new ClientCredentialsGrantBuilder();
        }


        public ClientCredentialsGrant build() {
            return build(true);
        }


        public ClientCredentialsGrant build(boolean validate) {
            return new ClientCredentialsGrantImpl();
        }
    }
}
