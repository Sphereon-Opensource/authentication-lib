package com.sphereon.libs.tokenapi;

import java.util.Map;

public interface TokenRequest {
    String getApplication();

    String getConsumerKey();

    void setConsumerKey(String consumerKey);

    String getConsumerSecret();

    void setConsumerSecret(String consumerSecret);

    boolean isPreconfigured();

    Map<String, String> getParameters();
}
