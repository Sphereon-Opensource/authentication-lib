package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
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
    protected static final Duration VALIDITY_PERIOD = Duration.ofSeconds(10);


    @Test
    public void test_10_ClientCredentials() {
        TokenApi tokenApi = new TokenApi.Builder().withApplication(APPLICATION_NAME).build();
        TokenRequest tokenRequest = tokenApi.tokenRequestBuilder().generateTokenRequestBuilder()
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();
        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        wait(VALIDITY_PERIOD);
    }


    @Test
    public void test_20_UserPassword() {
        TokenApi tokenApi = new TokenApi.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath("./config/sphereon-auth.config")
                .withPersistenceMode(PersistenceMode.READ_WRITE).build();

        Grant grant = tokenApi.grantBuilder().passwordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();


        TokenRequest tokenRequest = tokenApi.tokenRequestBuilder().generateTokenRequestBuilder()
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        wait(VALIDITY_PERIOD);

        Grant grantPersisted = tokenApi.grantBuilder().refreshTokenGrantBuilder()
                .withRefreshToken(this.refreshToken.get())
                .build();
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
        Grant grant = tokenApi.grantBuilder().refreshTokenGrantBuilder()
                .withRefreshToken(refreshToken.get())
                .build();


        TokenRequest tokenRequest = tokenApi.tokenRequestBuilder().generateTokenRequestBuilder()
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenApi.requestToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
        wait(VALIDITY_PERIOD);
    }


    private void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis() + 1000);
        } catch (InterruptedException e) {
        }
    }
}
