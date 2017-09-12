package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.GenerateTokenRequest;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CredentialsTest extends TestCase {

    private static final String APPLICATION_NAME = "ExpiringTokens";


    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    @Test
    public void test_10_ClientCredentials() {
        TokenApi tokenApi = new TokenApi.Builder(APPLICATION_NAME).build();
        ClientCredentialsGrant grant = tokenApi.getGrantFactory().createClientCredentialsGrant();
        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory().createGenerateTokenRequest(grant);
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.generateToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        wait(duration);
    }


    @Test
    public void test_20_UserPassword() {
        TokenApi tokenApi = new TokenApi.Builder(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath("./config/sphereon-auth.config")
                .withPersistenceMode(PersistenceMode.READ_WRITE).build();

        PasswordGrant grant = tokenApi.getGrantFactory().createPasswordGrant();
        grant.setUserName("SphereonTest");
        grant.setPassword("K@A$yG@Vwpq4Ow1W@Q2b");

        PasswordGrant grant2 = tokenApi.getGrantFactory().createPasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b").build();

        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory().createGenerateTokenRequest(grant2);
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.generateToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        wait(duration);

        PasswordGrant grant3 = tokenApi.getGrantFactory().createPasswordGrantBuilder().build();
        Assert.assertEquals(grant2, grant3);
        this.refreshToken.set(refreshToken);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());
        TokenApi tokenApi = new TokenApi.Builder(APPLICATION_NAME).configure(configuration -> {
            configuration.setPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE);
            configuration.setStandalonePropertyFilePath("./config/oauth2.config");
            configuration.setPersistenceMode(PersistenceMode.READ_WRITE);
        }).build();
        RefreshTokenGrant grant = tokenApi.getGrantFactory().createRefreshTokenGrant();
        grant.setRefreshToken(refreshToken.get());
        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory().createGenerateTokenRequest(grant);
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.generateToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
        wait(duration);
    }


    private void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis() + 1000);
        } catch (InterruptedException e) {
        }
    }
}
