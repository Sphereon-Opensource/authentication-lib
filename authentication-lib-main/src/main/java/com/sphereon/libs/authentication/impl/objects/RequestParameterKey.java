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

package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.commons.interfaces.StringValueEnum;

public enum RequestParameterKey implements StringValueEnum {

    GRANT_TYPE("grant_type"),
    USER_NAME("username"),
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    WINDOWS_TOKEN("windows_token"),
    KERBEROS_REALM("kerberos_realm"),
    KERBEROS_TOKEN("kerberos_token"),
    ASSERTION("assertions"),
    VALIDITY_PERIOD("validity_period"),
    SCOPE("scope"),
    AUTHORIZATION("Authorization"),
    TOKEN("token");


    private final String value;


    RequestParameterKey(String value) {
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
