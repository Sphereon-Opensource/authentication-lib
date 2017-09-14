package com.sphereon.libs.authentication.api.granttypes;

public interface PasswordGrant extends Grant {

    String getUserName();

    void setUserName(String userName);

    String getPassword();

    void setPassword(String password);


}
