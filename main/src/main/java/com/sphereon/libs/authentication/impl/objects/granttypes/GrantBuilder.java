package com.sphereon.libs.authentication.impl.objects.granttypes;

import com.sphereon.libs.authentication.api.granttypes.Grant;

public interface GrantBuilder<T extends Grant> {

    T build();

}
