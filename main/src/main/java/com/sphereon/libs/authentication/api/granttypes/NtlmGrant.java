package com.sphereon.libs.authentication.api.granttypes;

public interface NtlmGrant extends Grant {

    String getWindowsToken();

    void setWindowsToken(String windowsToken);
}
