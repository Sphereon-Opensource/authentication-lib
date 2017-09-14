package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.commons.assertions.Assert;
import com.sphereon.libs.authentication.api.GrantBuilder;
import com.sphereon.libs.authentication.api.granttypes.NtlmGrant;

public interface NtlmBuilder {

    final class NtlmGrantBuilder implements GrantBuilder {

        private String windowsToken;


        public static NtlmGrantBuilder newInstance() {
            return new NtlmGrantBuilder();
        }


        public NtlmGrantBuilder withWindowsToken(String windowsToken) {
            this.windowsToken = windowsToken;
            return this;
        }


        public NtlmGrant build() {
            return build(true);
        }


        public NtlmGrant build(boolean validate) {
            if (validate) {
                validate();
            }
            NtlmGrantImpl ntlmGrant = new NtlmGrantImpl();
            ntlmGrant.setWindowsToken(windowsToken);
            return ntlmGrant;
        }


        private void validate() {
            Assert.notNull(windowsToken, "Windows token is not set in NtlmGrantBuilder");
        }


    }
}
