package com.sphereon.libs.authentication.api.granttypes;

import com.sphereon.libs.authentication.impl.objects.granttypes.NtlmGrantBuilder;

public interface NtlmGrant extends NtlmGrantBuilder {

    void setWindowsToken(String windowsToken);
}
