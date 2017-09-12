package com.sphereon.libs.authentication.api;

public interface TokenRequest {
    String getConsumerKey();

    void setConsumerKey(String consumerKey);

    String getConsumerSecret();

    void setConsumerSecret(String consumerSecret);
}
