package com.sphereon.libs.authentication.api.granttypes;

public interface PasswordGrant extends Grant {

    String getUserName();

    PasswordGrant setUserName(String userName);

    String getPassword();

    PasswordGrant setPassword(String password);


}
