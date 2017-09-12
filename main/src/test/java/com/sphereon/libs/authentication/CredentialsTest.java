package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.GenerateTokenRequest;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
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
        TokenApi tokenApi = new TokenApi.Builder().withApplication(APPLICATION_NAME).build();
        GenerateTokenRequest tokenRequest = tokenApi.tokenRequestBuilder(TokenApi.TokenRequestBuilders.GENERATE).build();
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        wait(duration);
    }


    @Test
    public void test_20_UserPassword() {
        TokenApi tokenApi = new TokenApi.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath("./config/sphereon-auth.config")
                .withPersistenceMode(PersistenceMode.READ_WRITE).build();

        Grant grant = tokenApi.grantBuilder(TokenApi.GrantBuilders.PASSWORD_GRANT)
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b").build();


        GenerateTokenRequest tokenRequest = tokenApi.tokenRequestBuilder(TokenApi.TokenRequestBuilders.GENERATE)
                .withGrant(grant).build();
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        wait(duration);

        Grant grantPersisted = tokenApi.grantBuilder(TokenApi.GrantBuilders.PASSWORD_GRANT).build();
        Assert.assertEquals(grant, grantPersisted);
        this.refreshToken.set(refreshToken);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());
        TokenApi tokenApi = new TokenApi.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath("./config/sphereon-auth.config")
                .withPersistenceMode(PersistenceMode.READ_WRITE).build();
        RefreshTokenGrant grant = tokenApi.grantBuilder(TokenApi.GrantBuilders.REFRESH_TOKEN_GRANT)
                .withRefreshToken(refreshToken.get())
                .build();
        GenerateTokenRequest tokenRequest = tokenApi.tokenRequestBuilder(TokenApi.TokenRequestBuilders.GENERATE)
                .withGrant(grant).build();
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        tokenRequest.setScope("UnitTest");
        Duration duration = Duration.ofSeconds(10);
        tokenRequest.setValidityPeriod(duration);
        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
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
