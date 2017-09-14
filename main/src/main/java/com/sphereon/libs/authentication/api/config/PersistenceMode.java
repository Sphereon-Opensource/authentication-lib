package com.sphereon.libs.authentication.api.config;

public enum PersistenceMode {
    READ_ONLY, READ_WRITE;


    public static PersistenceMode fromString(String value) {
        for (PersistenceMode persistenceMode : PersistenceMode.values()) {
            if (reformat(persistenceMode.name()).equals(reformat(value))) {
                return persistenceMode;
            }
        }
        throw new RuntimeException("Could not parse " + value + " as PersistenceMode");
    }


    public static String reformat(String value) {
        return value.replace("_", "")
                .replace("-", "")
                .replace("_", "")
                .toLowerCase();
    }

}
