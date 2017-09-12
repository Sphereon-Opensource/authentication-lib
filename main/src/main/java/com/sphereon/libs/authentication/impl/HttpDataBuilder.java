package com.sphereon.libs.authentication.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sphereon.libs.authentication.api.GenerateTokenRequest;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.objects.RequestParameterKey;
import com.sphereon.libs.authentication.impl.objects.TokenPathParameters;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("PackageAccessibility")
public class HttpDataBuilder {

    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Type GSON_TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Gson gson = new Gson();


    public Request newTokenRequest(String urlBase, Headers headers, FormBody requestBody) {
        Assert.notNull(urlBase, "No urlBase was specified");

        Request.Builder request = new Request.Builder()
                .url(urlBase + TokenPathParameters.TOKEN)
                .headers(headers)
                .post(requestBody);
        return request.build();
    }


    public Request newRevokeRequest(String urlBase, Headers headers, FormBody requestBody) {
        Request.Builder request = new Request.Builder()
                .url(urlBase + TokenPathParameters.REVOKE)
                .headers(headers)
                .post(requestBody);
        return request.build();
    }


    public FormBody buildBody(TokenRequest tokenRequest) {
        Assert.notNull(tokenRequest, "No tokenRequest was specified");

        Map<RequestParameterKey, String> parameterMap = new LinkedHashMap<>();
        if (tokenRequest instanceof GenerateTokenRequest) {
            Grant grant = ((GenerateTokenRequest) tokenRequest).getGrant();
            if (grant instanceof RequestParameters) {
                RequestParameters bodyParameters = (RequestParameters) grant;
                bodyParameters.bodyParameters(parameterMap);
            }
        }
        if (tokenRequest instanceof RequestParameters) {
            RequestParameters bodyParameters = (RequestParameters) tokenRequest;
            bodyParameters.bodyParameters(parameterMap);
        }

        FormBody.Builder bodyBuilder = new FormBody.Builder();
        parameterMap.forEach((key, value) -> {
            Assert.notNull(value, "Body parameter " + key + " not set.");
            bodyBuilder.add(key.getValue(), value);
        });
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
