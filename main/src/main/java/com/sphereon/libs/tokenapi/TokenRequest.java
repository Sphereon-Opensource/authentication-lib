package com.sphereon.libs.tokenapi;

public interface TokenRequest {
    String getConsumerKey();

    void setConsumerKey(String consumerKey);

    String getConsumerSecret();

    void setConsumerSecret(String consumerSecret);
}
