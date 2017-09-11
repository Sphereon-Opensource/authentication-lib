package com.sphereon.libs.tokenapi.granttypes;

public interface PasswordGrant extends Grant {
    String getUserName();

    void setUserName(String userName);

    String getPassword();

    void setPassword(String password);
}
