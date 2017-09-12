package com.sphereon.libs.authentication.api.granttypes;

public interface NtlmGrant extends Grant {

    void setWindowsToken(String windowsToken);
}
