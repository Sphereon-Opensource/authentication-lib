package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.KerberosGrant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface KerberosBuilder extends Grant {

    final class Builder implements GrantBuilder<KerberosGrant> {

        private final ConfigManager configManager;
        private String kerberosRealm;
        private String kerberosToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withKerberosRealm(String kerberosRealm) {
            this.kerberosRealm = kerberosRealm;
            return this;
        }


        public Builder withKerberosToken(String kerberosToken) {
            this.kerberosToken = kerberosToken;
            return this;
        }


        public KerberosGrant build() {
            KerberosGrantImpl kerberosGrant = new KerberosGrantImpl();
            kerberosGrant.setKerberosRealm(kerberosRealm);
            kerberosGrant.setKerberosToken(kerberosToken);
            configManager.loadGrant(kerberosGrant);
            return kerberosGrant;
        }
    }
}
