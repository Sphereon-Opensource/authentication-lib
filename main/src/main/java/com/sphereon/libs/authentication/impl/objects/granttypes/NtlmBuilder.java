package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface NtlmBuilder {

    final class Builder implements GrantBuilder {

        private final ConfigManager configManager;

        private String windowsToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public NtlmBuilder.Builder withWindowsToken(String windowsToken) {
            this.windowsToken = windowsToken;
            return this;
        }


        public Grant build() {
            NtlmGrantImpl ntlmGrant = new NtlmGrantImpl();
            ntlmGrant.setWindowsToken(windowsToken);
            configManager.loadGrant(ntlmGrant);
            return ntlmGrant;
        }
    }
}
