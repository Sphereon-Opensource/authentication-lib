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
import com.sphereon.libs.authentication.api.granttypes.KerberosGrant;

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
