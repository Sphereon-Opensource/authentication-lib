package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.GrantBuilder;

public interface NtlmBuilder {

    final class NtlmGrantBuilder implements GrantBuilder {

        private String windowsToken;


        public NtlmGrantBuilder withWindowsToken(String windowsToken) {
            this.windowsToken = windowsToken;
            return this;
        }


        public Grant build() {
            NtlmGrantImpl ntlmGrant = new NtlmGrantImpl();
            ntlmGrant.setWindowsToken(windowsToken);
            return ntlmGrant;
        }
    }
}
