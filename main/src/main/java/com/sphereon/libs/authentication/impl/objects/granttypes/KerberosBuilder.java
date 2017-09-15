package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.KerberosGrant;
import com.sphereon.libs.authentication.impl.commons.assertions.Assert;

public interface KerberosBuilder {

    final class KerberosGrantBuilder implements GrantBuilder {

        private String kerberosRealm;
        private String kerberosToken;


        public static KerberosGrantBuilder newInstance() {
            return new KerberosGrantBuilder();
        }


        public KerberosGrantBuilder withKerberosRealm(String kerberosRealm) {
            this.kerberosRealm = kerberosRealm;
            return this;
        }


        public KerberosGrantBuilder withKerberosToken(String kerberosToken) {
            this.kerberosToken = kerberosToken;
            return this;
        }


        public KerberosGrant build() {
            return build(true);
        }


        public KerberosGrant build(boolean validate) {
            if (validate) {
                validate();
            }
            KerberosGrantImpl kerberosGrant = new KerberosGrantImpl();
            kerberosGrant.setKerberosRealm(kerberosRealm);
            kerberosGrant.setKerberosToken(kerberosToken);
            return kerberosGrant;
        }


        private void validate() {
            Assert.notNull(kerberosRealm, "Kerberos realm is not set in KerberosGrantBuilder");
            Assert.notNull(kerberosToken, "Kerberos token is not set in KerberosGrantBuilder");
        }

    }
}
