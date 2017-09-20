package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.atomic.AtomicReference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CredentialsTest extends AbstractTest {

    private static final Long VALIDITY_PERIOD = 10L;

    private static final AtomicReference<String> prevToken = new AtomicReference<>();
    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public CredentialsTest() {

    }


    @Test
    public void test_10_ClientCredential() {
        AuthenticationApi authenticationApi = new AuthenticationApi.Builder().build();
        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        this.prevToken.set(tokenResponse.getAccessToken());
        waitSeconds(2L);
        Assert.assertFalse(tokenResponse.isExpired());
        tokenResponse = tokenRequest.execute();
        Assert.assertFalse(tokenResponse.isExpired());
        waitSeconds(tokenResponse.getExpiresInSeconds());
        Assert.assertTrue(tokenResponse.isExpired());
        tokenResponse = tokenRequest.execute();
        Assert.assertFalse(tokenResponse.isExpired());
    }


    @Test
    public void test_20_UserPassword() {

        ApiConfiguration configuration = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        Assert.assertNotEquals(this.prevToken.get(), tokenResponse.getAccessToken());
        this.prevToken.set(tokenResponse.getAccessToken());
        this.refreshToken.set(refreshToken);
        waitSeconds(VALIDITY_PERIOD);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());
        ApiConfiguration configuration = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.RefreshTokenGrantBuilder()
                .withRefreshToken(refreshToken.get())
                .build();


        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
        Assert.assertNotEquals(this.prevToken.get(), tokenResponse.getAccessToken());
    }
}
