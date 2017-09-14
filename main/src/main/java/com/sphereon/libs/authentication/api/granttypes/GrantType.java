package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.commons.exceptions.EnumParseException;
import org.apache.commons.lang3.StringUtils;

public enum GrantType {
    CLIENT_CREDENTIALS, REFRESH_TOKEN, PASSWORD, NTLM, KERBEROS, SAML2;


    public static GrantType fromString(String value) throws EnumParseException {
        if (StringUtils.isBlank(value)) {
            throw new EnumParseException("Could not parse an empty value as GrantType");
        }

        for (GrantType grantType : GrantType.values()) {
            if (reformat(grantType.name()).equals(reformat(value))) {
                return grantType;
            }
        }
        throw new EnumParseException("Could not parse " + value + " as GrantType");
    }


    public String getFormattedValue() {
        return reformat(this.name());
    }


    private static String reformat(String value) {
        return value.replace("_", "")
                .replace("-", "")
                .replace("_", "")
                .toLowerCase();
    }
}
