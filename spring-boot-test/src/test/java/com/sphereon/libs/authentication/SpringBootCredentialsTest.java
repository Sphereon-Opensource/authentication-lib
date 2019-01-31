package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

//@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class SpringBootCredentialsTest {

    protected static final String APPLICATION_NAME = "springboot-test";

    private static final Long VALIDITY_PERIOD = 10L;

    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public SpringBootCredentialsTest() {
        System.setProperty("sphereon.testing", "true");
    }


//    @Test
    @Ignore
    public void springbootTest_10_UserPassword() throws Exception {

        createAppPropetiesFileConfiguration();
        ApiConfiguration configuration2 = loadAppPropertiesFileConfiguration();

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


    @Test
    @Ignore
    public void springbootTest_05_PreConfiguredUserPassword() throws Exception {

        File appPropsFile = new File("./config/application.properties");
        loadPreconfiguredAppPropertiesFileConfiguration(appPropsFile);
        assertPropertyValues(appPropsFile, "authentication-api." + APPLICATION_NAME + ".consumer-secret=ENC(*)",
                "authentication-api." + APPLICATION_NAME + "password=ENC(*)");

        ApiConfiguration configuration2 = loadAppPropertiesFileConfiguration();
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


    private ApiConfiguration createAppPropetiesFileConfiguration() throws Exception {
        File appPropsFile = new File("./config/application.properties");
        appPropsFile.delete();
        Files.copy(Paths.get(getClass().getResource("/application.properties").toURI()), appPropsFile.toPath());

        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withAutoEncryptSecrets(true)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withDefaultGrant(grant)
                .build();
    }


    private ApiConfiguration loadPreconfiguredAppPropertiesFileConfiguration(File appPropsFile) throws Exception {
        appPropsFile.delete();
        Files.copy(Paths.get(getClass().getResource("/application-preconfigured.properties").toURI()), appPropsFile.toPath());

        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .build();
    }


    private ApiConfiguration loadAppPropertiesFileConfiguration() {
        return new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SPRING_APPLICATION_PROPERTIES)
                .withAutoEncryptSecrets(true)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

    }

    protected void assertPropertyValues(File propertiesFile, String... keyValues) throws Exception {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFile(propertiesFile));
        FileBasedConfiguration config = builder.getConfiguration();
        for (String keyValue : keyValues) {
            int firstEqToken = keyValue.indexOf("=");
            Assert.assertTrue(firstEqToken > -1);
            String key = keyValue.substring(0, firstEqToken);
            String value = keyValue.substring(firstEqToken + 1);
            if (value.contains("ENC(*)")) {
                if (!config.getString(key).contains("ENC(")) {
                    Assert.assertEquals("The result for key " + key + " does not match", "ENC(*)", config.getString(key));
                }
            } else {
                Assert.assertEquals("The result for key " + key + " does not match", value, config.getString(key));
            }
        }
    }


}