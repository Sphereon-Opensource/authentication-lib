package com.sphereon.libs.authentication.api.granttypes;

public interface NtlmGrant extends Grant {

    String getWindowsToken();

    NtlmGrant setWindowsToken(String windowsToken);
}
