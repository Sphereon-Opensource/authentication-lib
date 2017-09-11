package com.sphereon.libs.tokenapi.granttypes;

public interface NtlmGrant extends Grant {
    String getWindowsToken();

    void setWindowsToken(String windowsToken);
}
