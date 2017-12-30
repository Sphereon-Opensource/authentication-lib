/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
