package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.*;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.TokenResponseImpl;
import okhttp3.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("PackageAccessibility")
public class TokenApiImpl implements TokenApi {


    private static final HttpDataBuilder httpDataBuilder = new HttpDataBuilder();

    private final ConfigManager configManager;

    private final OkHttpClient okHttpClient = new OkHttpClient();


    public TokenApiImpl(TokenApiConfiguration tokenApiConfiguration) {
        configManager = new ConfigManager(tokenApiConfiguration);
    }


    @Override
    public TokenResponse generateToken(GenerateTokenRequest tokenRequest) {
        Assert.notNull(tokenRequest, "No tokenRequest was supplied");
        FormBody requestBody = httpDataBuilder.buildBody(tokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(tokenRequest);
        Request httpRequest = httpDataBuilder.newTokenRequest(configManager.getConfiguration().getGatewayBaseUrl(), headers, requestBody);
        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            String responseBody = getResponseBodyContent(response);
            Map<String, String> parameters = httpDataBuilder.parseJsonResponseBody(responseBody);
            configManager.persist(tokenRequest);
            return new TokenResponseImpl(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void revokeToken(RevokeTokenRequest revokeTokenRequest) {
        FormBody requestBody = httpDataBuilder.buildBody(revokeTokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(revokeTokenRequest);
        Request httpRequest = httpDataBuilder.newRevokeRequest(configManager.getConfiguration().getGatewayBaseUrl(), headers, requestBody);
        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            if (response.code() != 200) {
                throw new RuntimeException(String.format("Revoke request failed with http code %d, message:%s", response.code(), response.message()));
            }
            configManager.persist(revokeTokenRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getResponseBodyContent(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (response.code() != 200) {
            throw new RuntimeException(String.format("Generate request failed with http code %d, message:%s", response.code(), response.message()));
        }
        Assert.notNull(responseBody, "The remote endpoint did not return a response body.");
        String responseBodyString = responseBody.string();
        Assert.isTrue(!"application/json".equals(responseBody.contentType()),
                String.format("The remote endpoint responded with content type %s while application/json is expected. Content:\r\n%s",
                        responseBody.contentType(), responseBodyString));
        return responseBodyString;
    }


    @Override
    public TokenRequestFactory getTokenRequestFactory() {
        return new TokenRequestFactoryImpl(configManager);
    }


    @Override
    public GrantFactory getGrantFactory() {
        return new GrantFactoryImpl(configManager);
    }


    @Override
    public TokenApiConfiguration getConfiguration() {
        return configManager.getConfiguration();
    }


    @Override
    public void persistConfiguration() {
        configManager.persist();
    }
}
