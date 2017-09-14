package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface ClientCredentialsBuilder {

    final class ClientCredentialsGrantBuilder implements GrantBuilder {

        public Grant build() {
            return new ClientCredentialsGrantImpl();
        }
    }
}
