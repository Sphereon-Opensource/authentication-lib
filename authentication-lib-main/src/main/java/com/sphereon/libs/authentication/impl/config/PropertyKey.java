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

package com.sphereon.libs.authentication.impl.config;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum PropertyKey implements StringValueEnum {

    GATEWAY_BASE_URL("gateway-base-url", false),
    PERSISTENCE_MODE("persistence-mode", false),
    USER_NAME("username", false),
    PASSWORD("password", true),
    CONSUMER_KEY("consumer-key", false),
    CONSUMER_SECRET("consumer-secret", true),
    SCOPE("scope", false),
    VALIDITY_PERIOD("validity-period", false),
    KERBEROS_REALM("kerberos-realm", true),
    KERBEROS_TOKEN("kerberos-token", true),
    CURRENT_TOKEN("current-token", true),
    WINDOWS_TOKEN("windows-token", true),
    REFRESH_TOKEN("refresh-token", true),
    SAML2_ASSERTION("saml2-assertion", true),
    GRANT_TYPE("grant-type", false),
    AUTO_ENCRYPT_SECRETS("auto-encrypt", false);


    private final String propertyKey;
    private final boolean encrypt;


    PropertyKey(String value, boolean encrypt) {
        this.propertyKey = value;
        this.encrypt = encrypt;
    }


    @Override
    public String getValue() {
        return propertyKey;
    }


    public boolean isEncrypt() {
        return encrypt;
    }


    @Override
    public String toString() {
        return getValue();
    }
}
