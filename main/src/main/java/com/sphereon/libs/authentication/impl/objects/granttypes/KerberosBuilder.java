package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface KerberosBuilder {

    final class KerberosGrantBuilder implements GrantBuilder {

        private String kerberosRealm;
        private String kerberosToken;


        public KerberosGrantBuilder withKerberosRealm(String kerberosRealm) {
            this.kerberosRealm = kerberosRealm;
            return this;
        }


        public KerberosGrantBuilder withKerberosToken(String kerberosToken) {
            this.kerberosToken = kerberosToken;
            return this;
        }


        public Grant build() {
            KerberosGrantImpl kerberosGrant = new KerberosGrantImpl();
            kerberosGrant.setKerberosRealm(kerberosRealm);
            kerberosGrant.setKerberosToken(kerberosToken);
            return kerberosGrant;
        }
    }
}
