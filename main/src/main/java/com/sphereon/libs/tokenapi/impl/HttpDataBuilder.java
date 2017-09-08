package com.sphereon.libs.tokenapi.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.TokenRequest;
import com.sphereon.libs.tokenapi.granttypes.Grant;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;

public class HttpDataBuilder {

    private static final String TOKEN = "token";
    private static final String REVOKE = "revoke";
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Type GSON_TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Gson gson = new Gson();


    public Request newTokenRequest(String urlBase, Headers headers, FormBody requestBody) {
        Request.Builder request = new Request.Builder()
                .url(urlBase + TOKEN)
                .headers(headers)
                .post(requestBody);
        return request.build();
    }


    public Request newRevokeRequest(String urlBase, Headers headers, FormBody requestBody) {
        Request.Builder request = new Request.Builder()
                .url(urlBase + REVOKE)
                .headers(headers)
                .post(requestBody);
        return request.build();
    }


    public FormBody buildBody(TokenRequest tokenRequest) {
        // TODO: Assert
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (tokenRequest instanceof GenerateTokenRequest) {
            Grant grant = ((GenerateTokenRequest) tokenRequest).getGrant();
            grant.getParameters().forEach((key, value) ->
                    bodyBuilder.add(key, value));
        }
        if (tokenRequest.getParameters() != null) {
            tokenRequest.getParameters().forEach((key, value) ->
                    bodyBuilder.add(key, value));
        }
        return bodyBuilder.build();
    }


    public Headers buildHeaders(TokenRequest tokenRequest) {
        String clientParameters = String.format("%s:%s", tokenRequest.getConsumerKey(), tokenRequest.getConsumerSecret());
        try {
            String authorizationHeader = String.format("Basic %s", BASE64_ENCODER.encodeToString(clientParameters.getBytes("UTF-8")));
            Headers.Builder builder = new Headers.Builder()
                    .add("Authorization", authorizationHeader);
            return builder.build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public Map<String, String> parseJsonResponseBody(String jsonResponse) {
        return gson.fromJson(jsonResponse, GSON_TYPE_TOKEN);
    }
}
