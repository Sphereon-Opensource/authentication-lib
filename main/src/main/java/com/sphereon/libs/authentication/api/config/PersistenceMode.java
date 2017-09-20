package com.sphereon.libs.authentication.api.config;

import com.sphereon.commons.exceptions.EnumParseException;
import org.apache.commons.lang3.StringUtils;

public enum PersistenceMode {
    READ_ONLY, READ_WRITE;


    public static PersistenceMode fromString(String value) {
        if (StringUtils.isNotEmpty(value)) {
            for (PersistenceMode persistenceMode : PersistenceMode.values()) {
                if (reformat(persistenceMode.name()).equals(reformat(value))) {
                    return persistenceMode;
                }
            }
        }
        throw new EnumParseException("Could not parse " + value + " as PersistenceMode");
    }


    private static String reformat(String value) {
        return value.replace("_", "")
                .replace("-", "")
                .toLowerCase();
    }

}
