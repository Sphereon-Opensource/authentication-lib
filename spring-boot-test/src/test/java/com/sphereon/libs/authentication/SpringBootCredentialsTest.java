package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.TokenApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.config.TestConfig;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringBootCredentialsTest extends AbstractTest {

    private static final Duration VALIDITY_PERIOD = Duration.ofSeconds(10);

    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public SpringBootCredentialsTest() {
    }



    @Test
    public void test_10_UserPassword() {

        TokenApiConfiguration configuration1 = createAppPropetiesFileConfiguration();
        TokenApiConfiguration configuration2 = loadAppPropetiesFileConfiguration();

        TokenApi tokenApi = new TokenApi.Builder()
                .withConfiguration(configuration2)
                .build();


        TokenRequest tokenRequest = tokenApi.requestToken()
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


    private TokenApiConfiguration createAppPropetiesFileConfiguration() {
        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        return new TokenApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withDefaultGrant(grant)
                .build();
    }

    private TokenApiConfiguration loadAppPropetiesFileConfiguration() {
        return new TokenApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

    }
}
