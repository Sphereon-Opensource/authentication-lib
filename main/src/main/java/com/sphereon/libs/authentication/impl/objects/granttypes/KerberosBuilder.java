package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface KerberosBuilder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;
        private String kerberosRealm;
        private String kerberosToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public KerberosBuilder.Builder withKerberosRealm(String kerberosRealm) {
            this.kerberosRealm = kerberosRealm;
            return this;
        }


        public KerberosBuilder.Builder withKerberosToken(String kerberosToken) {
            this.kerberosToken = kerberosToken;
            return this;
        }


        public Grant build() {
            KerberosGrantImpl kerberosGrant = new KerberosGrantImpl();
            kerberosGrant.setKerberosRealm(kerberosRealm);
            kerberosGrant.setKerberosToken(kerberosToken);
            configManager.loadGrant(kerberosGrant);
            return kerberosGrant;
        }
    }
}
