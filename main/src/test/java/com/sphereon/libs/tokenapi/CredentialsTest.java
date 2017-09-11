package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.tokenapi.granttypes.PasswordGrant;
import com.sphereon.libs.tokenapi.granttypes.RefreshTokenGrant;
import com.sphereon.libs.tokenapi.impl.TokenApiImpl;
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

    private TokenApi tokenApi = new TokenApiImpl();

    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    @Test
    public void test_10_ClientCredentials() {

        ClientCredentialsGrant grant = tokenApi.getGrantFactory(APPLICATION_NAME).clientCredentialsGrant();
        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory(APPLICATION_NAME).constructGenerateTokenRequest(grant);
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
//        tokenApi.configure(PersistenceType.SYSTEM_ENVIRONMENT, PersistenceMode.READ_ONLY);
        PasswordGrant grant = tokenApi.getGrantFactory(APPLICATION_NAME).passwordGrant();
        grant.setUserName("SphereonTest");
        grant.setPassword("K@A$yG@Vwpq4Ow1W@Q2b");
        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory(APPLICATION_NAME).constructGenerateTokenRequest(grant);
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
        this.refreshToken.set(refreshToken);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());

//        tokenApi.configure(PersistenceType.SYSTEM_ENVIRONMENT, PersistenceMode.READ_ONLY);
        RefreshTokenGrant grant = tokenApi.getGrantFactory(APPLICATION_NAME).refreshTokenGrant();
        grant.setRefreshToken(refreshToken.get());
        GenerateTokenRequest tokenRequest = tokenApi.getTokenRequestFactory(APPLICATION_NAME).constructGenerateTokenRequest(grant);
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
