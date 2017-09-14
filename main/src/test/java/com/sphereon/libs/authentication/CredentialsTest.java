package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CredentialsTest extends AbstractTest {

    private static final Duration VALIDITY_PERIOD = Duration.ofSeconds(10);

    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public CredentialsTest() {
    }


    @Test
    public void test_10_ClientCredentials() {
        TokenApi tokenApi = new TokenApi.Builder().build();
        TokenRequest tokenRequest = tokenApi.requestToken()
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        wait(VALIDITY_PERIOD);
    }


    @Test
    public void test_20_UserPassword() {

        TokenApiConfiguration configuration = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        TokenApi tokenApi = new TokenApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        TokenRequest tokenRequest = tokenApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        wait(VALIDITY_PERIOD);
        this.refreshToken.set(refreshToken);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());
        TokenApiConfiguration configuration = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        TokenApi tokenApi = new TokenApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.RefreshTokenGrantBuilder()
                .withRefreshToken(refreshToken.get())
                .build();


        TokenRequest tokenRequest = tokenApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
        wait(VALIDITY_PERIOD);
    }
}
