package com.sphereon.libs.authentication.impl.objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sphereon.commons.assertions.Assert;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.impl.RequestParameters;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

class HttpRequestHandler {

    private static final Type GSON_TYPE_TOKEN = new TypeToken<Map<String, String>>() {}.getType();
    private static final Gson gson = new Gson();

    private final OkHttpClient okHttpClient;


    public HttpRequestHandler(ApiConfiguration configuration) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        // TODO: Add proxy support
        okHttpClient = clientBuilder.build();
    }


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


    public FormBody buildBody(RequestParameters requestParameters) {
        Assert.notNull(requestParameters, "No request parameters specified");

        Map<RequestParameterKey, String> parameterMap = new LinkedHashMap<>();
        requestParameters.bodyParameters(parameterMap);
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<RequestParameterKey, String> entry : parameterMap.entrySet()) {
            Assert.notNull(entry.getValue(), "Body parameter " + entry.getKey() + " not set.");
            bodyBuilder.add(entry.getKey().getValue(), entry.getValue());
        }
        return bodyBuilder.build();
    }


    public Headers buildHeaders(RequestParameters requestParameters) {

        Map<RequestParameterKey, String> parameterMap = new LinkedHashMap<>();
        requestParameters.headerParameters(parameterMap);

        Headers.Builder headerBuilder = new Headers.Builder();
        for (Map.Entry<RequestParameterKey, String> entry : parameterMap.entrySet()) {
            Assert.notNull(entry.getValue(), "Header parameter " + entry.getKey() + " not set.");
            headerBuilder.add(entry.getKey().getValue(), entry.getValue());

        }
        return headerBuilder.build();
    }


    public Map<String, String> parseJsonResponseBody(String jsonResponse) {
        return gson.fromJson(jsonResponse, GSON_TYPE_TOKEN);
    }


    public String getResponseBodyContent(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (response.code() != 200) {
            throw new RuntimeException(String.format("Generate request failed with http code %d, message:%s", response.code(), response.message()));
        }
        Assert.notNull(responseBody, "The remote endpoint did not return a response body.");
        String responseBodyString = responseBody.string();
        Assert.isTrue(!"application/json".equals(responseBody.contentType()),
                String.format("The remote endpoint responded with content type %s while application/json is expected. Content:%n%s",
                        responseBody.contentType(), responseBodyString));
        return responseBodyString;
    }


    public Response execute(Request httpRequest) throws IOException {
        return okHttpClient.newCall(httpRequest).execute();
    }
}
