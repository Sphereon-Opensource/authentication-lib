package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.TokenRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
@Named("TokenApi.httpRequestGenerator")
public class HttpRequestGenerator {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();


    public String buildHeaders(HttpHeaders headers, TokenRequest tokenRequest) {
        String clientParameters = String.format("%s:%s", tokenRequest.getConsumerKey(), tokenRequest.getConsumerSecret());
        try {
            String authorizationHeader = String.format("Basic %s", ENCODER.encodeToString(clientParameters.getBytes("UTF-8")));
            headers.add("Authorization", authorizationHeader);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
