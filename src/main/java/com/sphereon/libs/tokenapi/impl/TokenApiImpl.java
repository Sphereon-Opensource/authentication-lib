package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GenerateTokenRequest;
import com.sphereon.libs.tokenapi.RevokeTokenRequest;
import com.sphereon.libs.tokenapi.TokenApi;
import com.sphereon.libs.tokenapi.TokenResponse;
import com.sphereon.libs.tokenapi.granttypes.*;
import com.sphereon.libs.tokenapi.impl.granttypes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Component
@Named("Sphereon.tokenApi")
public class TokenApiImpl implements TokenApi {

    @Autowired
    @Inject
    private HttpRequestGenerator httpRequestGenerator;

    @Autowired
    @Inject
    private OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory;


    @Override
    public TokenResponse generateToken(GenerateTokenRequest tokenRequest) {
        ClientHttpRequest request = okHttp3ClientHttpRequestFactory.createRequest(null, HttpMethod.GET);
        httpRequestGenerator.buildHeaders(request.getHeaders(), tokenRequest);

    }


    @Override
    public void revokeToken(RevokeTokenRequest revokeTokenRequest) {

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
