package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.PasswordGrantBuilder;

public interface PasswordGrant extends PasswordGrantBuilder {

    void setUserName(String userName);

    void setPassword(String password);


}
