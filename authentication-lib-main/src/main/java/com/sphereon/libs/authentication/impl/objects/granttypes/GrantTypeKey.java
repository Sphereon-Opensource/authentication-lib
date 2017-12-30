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

import com.sphereon.commons.interfaces.StringValueEnum;

public enum GrantTypeKey implements StringValueEnum {

    KERBEROS("kerberos"), CLIENT_CREDENTIALS("client_credentials"), NTLM("iwa:ntlm"), PASSWORD("password"), REFRESH_TOKEN("refresh_token"),
    SAML2("urn:ietf:params:oauth:grant-type:saml2-bearer");

    private final String value;


    GrantTypeKey(String value) {
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return getValue();
    }
}
