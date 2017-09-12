package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.api.granttypes.NtlmGrant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;

public interface NtlmGrantBuilder extends Grant {

    final class Builder implements GrantBuilder<NtlmGrant> {

        private final ConfigManager configManager;

        private String windowsToken;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public Builder withWindowsToken(String windowsToken) {
            this.windowsToken = windowsToken;
            return this;
        }


        public NtlmGrant build() {
            NtlmGrantImpl ntlmGrant = new NtlmGrantImpl();
            ntlmGrant.setWindowsToken(windowsToken);
            configManager.loadGrant(ntlmGrant);
            return ntlmGrant;
        }
    }
}
