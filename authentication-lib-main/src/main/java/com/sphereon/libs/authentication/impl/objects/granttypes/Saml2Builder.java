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
import com.sphereon.libs.authentication.api.granttypes.SAML2Grant;

public interface Saml2Builder {

    final class Saml2GrantBuilder implements GrantBuilder {

        private String assertion;

        public static Saml2GrantBuilder newInstance() {
            return new Saml2Builder.Saml2GrantBuilder();
        }


        public Saml2GrantBuilder withAssertion(String assertion) {
            this.assertion = assertion;
            return this;
        }


        public SAML2Grant build() {
            return build(true);
        }


        public SAML2Grant build(boolean validate) {
            if (validate) {
                validate();
            }
            SAML2GrantImpl saml2Grant = new SAML2GrantImpl();
            saml2Grant.setAssertion(assertion);
            return saml2Grant;
        }


        private void validate() {
            Assert.notNull(assertion, "Assertion is not set in Saml2GrantBuilder");
        }
    }
}
