package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.TokenApi;
import com.sphereon.libs.tokenapi.TokenResponse;
import com.sphereon.libs.tokenapi.granttypes.*;
import com.sphereon.libs.tokenapi.impl.granttypes.*;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class TokenApiImpl implements TokenApi {

    private static final String TEMP_URL = "https://gw.api.cloud.sphereon.com/";

    private static final HttpDataBuilder httpDataBuilder = new HttpDataBuilder();

    private OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    public TokenResponse generateToken(GenerateTokenRequest tokenRequest) {
        FormBody requestBody = httpDataBuilder.buildBody(tokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(tokenRequest);
        Request httpRequest = httpDataBuilder.newTokenRequest(TEMP_URL, headers, requestBody);
        try {
            Response response = okHttpClient.newCall(httpRequest).execute();
            ResponseBody reponseBody = response.body();
            // TODO: check content type
            Map<String, String> parameters = httpDataBuilder.parseJsonResponseBody(reponseBody.string());
            return new TokenResponseImpl(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void revokeToken(RevokeTokenRequest revokeTokenRequest) {
        FormBody requestBody = httpDataBuilder.buildBody(revokeTokenRequest);
        Headers headers = httpDataBuilder.buildHeaders(revokeTokenRequest);
        Request httpRequest = httpDataBuilder.newRevokeRequest(TEMP_URL, headers, requestBody);
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
    public GenerateTokenRequest newGenerateTokenRequest(String application, Grant grant) {
        GenerateTokenRequestImpl generateTokenRequest = new GenerateTokenRequestImpl(application, grant);
        // TODO: preload by history
        return generateTokenRequest;
    }


    @Override
    public RevokeTokenRequest newRevokeTokenRequest(String application) {
        return new RevokeTokenRequestImpl(application);
    }


    @Override
    public ClientCredentialsGrant newClientCredentialsGrant() {
        return new ClientCredentialsGrantImpl();
    }


    @Override
    public PasswordGrant newPasswordGrant() {
        return new PasswordGrantImpl();
    }


    @Override
    public RefreshTokenGrant newRefreshTokenGrant() {
        return new RefreshTokenGrantImpl();
    }


    @Override
    public NtlmGrant newNtlmGrant() {
        return new NtlmGrantImpl();
    }


    @Override
    public KerberosGrant newKerberosGrant() {
        return new KerberosGrantImpl();
    }


    @Override
    public SAML2Grant newSAML2Grant() {
        return new SAML2GrantImpl();
    }
}
