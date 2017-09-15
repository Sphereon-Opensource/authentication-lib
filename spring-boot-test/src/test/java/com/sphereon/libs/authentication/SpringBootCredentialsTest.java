package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringBootCredentialsTest {

    protected static final String APPLICATION_NAME = "springboot-test";

    private static final Duration VALIDITY_PERIOD = Duration.ofSeconds(10);

    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public SpringBootCredentialsTest() {
    }


    @Test
    public void springbootTest_10_UserPassword() {

        createAppPropetiesFileConfiguration();
        ApiConfiguration configuration2 = loadAppPropetiesFileConfiguration();

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration2)
                .build();


        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
    }


    private ApiConfiguration createAppPropetiesFileConfiguration() {
        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withDefaultGrant(grant)
                .build();
    }


    private ApiConfiguration loadAppPropetiesFileConfiguration() {
        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

    }
}