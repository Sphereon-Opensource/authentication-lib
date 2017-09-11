package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.*;
import com.sphereon.libs.tokenapi.config.PersistenceMode;
import com.sphereon.libs.tokenapi.config.PersistenceType;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.objects.TokenResponseImpl;
import okhttp3.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("PackageAccessibility")
public class TokenApiImpl implements TokenApi {


    private static final HttpDataBuilder httpDataBuilder = new HttpDataBuilder();

    private OkHttpClient okHttpClient = new OkHttpClient();

    private ConfigManager configManager = new ConfigManager();

    private TokenApiConfiguration tokenApiConfiguration;


    @Override
    public TokenResponse generateToken(GenerateTokenRequest tokenRequest) {
        Assert.notNull(tokenRequest, "No tokenRequest was supplied");
        createDefaultConfig();
        FormBody requestBody = httpDataBuilder.buildBody(tokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(tokenRequest);
        Request httpRequest = httpDataBuilder.newTokenRequest(tokenApiConfiguration.getGatewayBaseUrl(), headers, requestBody);
        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            String responseBody = getResponseBodyContent(response);
            Map<String, String> parameters = httpDataBuilder.parseJsonResponseBody(responseBody);
            return new TokenResponseImpl(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getResponseBodyContent(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        Assert.notNull(responseBody, String.format("The remote endpoint did not return a response body. The response code was %d", response.code()));
        String responseBodyString = responseBody.string();
        Assert.isTrue(!"application/json".equals(responseBody.contentType()),
                String.format("The remote endpoint responded with content type %s while application/json is expected. Content:\r\n%s",
                        responseBody.contentType(), responseBodyString));
        return responseBodyString;
    }


    @Override
    public void revokeToken(RevokeTokenRequest revokeTokenRequest) {
        createDefaultConfig();
        FormBody requestBody = httpDataBuilder.buildBody(revokeTokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(revokeTokenRequest);
        Request httpRequest = httpDataBuilder.newRevokeRequest(tokenApiConfiguration.getGatewayBaseUrl(), headers, requestBody);
        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            if (response.code() != 200) {
                throw new RuntimeException("Revoke request failed with http code " + response.code());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public TokenRequestFactory getTokenRequestFactory(String applicationName) {
        return new TokenRequestFactoryImpl(applicationName, configManager, tokenApiConfiguration);
    }


    @Override
    public GrantFactory getGrantFactory(String applicationName) {
        return new GrantFactoryImpl(applicationName, configManager, tokenApiConfiguration);
    }


    private void createDefaultConfig() {
        if (tokenApiConfiguration == null) {
            configure(PersistenceType.DISABLED, PersistenceMode.READ_ONLY);
        }
    }


    @Override
    public TokenApiConfiguration configure(PersistenceType persistenceType, PersistenceMode persistenceMode) {
        tokenApiConfiguration = configManager.loadConfig(persistenceType, persistenceMode);
        return tokenApiConfiguration;
    }


    @Override
    public void persistConfiguration(TokenApiConfiguration tokenApiConfiguration) {
        configManager.persist(tokenApiConfiguration);
    }
}
