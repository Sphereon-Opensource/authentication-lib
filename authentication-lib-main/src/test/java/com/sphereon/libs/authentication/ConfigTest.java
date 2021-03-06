/*
 * Copyright (c) 2017 Sphereon BV https://sphereon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.*;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.InputStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigTest extends AbstractTest {

    protected static final String UI_TEST_PROPERTIES = "sphereon-ui-test.properties";
    protected static final String MANUAL_TEMPLATE_PROPERTIES = "/manual-template.properties";
    protected static final String MANUAL_PROPERTIES = "manual.properties";


    public ConfigTest() {
        System.setProperty("sphereon.testing", "true");
    }


    @Test
    public void test_10_ConfigSaveLoad() {
        ApiConfiguration savedConfig = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        ApiConfiguration loadedConfig = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        Assert.assertEquals(savedConfig.getApplication(), loadedConfig.getApplication());
        Assert.assertEquals(savedConfig.getPersistenceType(), loadedConfig.getPersistenceType());
        Assert.assertEquals(savedConfig.getPersistenceMode(), loadedConfig.getPersistenceMode());
        Assert.assertEquals(savedConfig.getGatewayBaseUrl(), loadedConfig.getGatewayBaseUrl());
        Assert.assertEquals(savedConfig.getConsumerKey(), loadedConfig.getConsumerKey());
        Assert.assertEquals(savedConfig.getConsumerSecret(), loadedConfig.getConsumerSecret());
        Assert.assertEquals(savedConfig.getStandalonePropertyFile(), loadedConfig.getStandalonePropertyFile());
    }


    @Test
    public void test_12_ConfigSaveLoadNoApplication() {
        File standaloneConfigFile = new File("./config/" + SPHEREON_AUTH_PROPERTIES);
        standaloneConfigFile.delete();
        ApiConfiguration savedConfig = new ApiConfiguration.Builder()
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .withStandaloneConfigFile(standaloneConfigFile)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("UnitTestPassword")
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .build();
        ApiConfiguration loadedConfig = new ApiConfiguration.Builder()
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .withStandaloneConfigFile(new File("./config/" + SPHEREON_AUTH_PROPERTIES))
                .withAutoEncryptSecrets(true)
                .withAutoEncryptionPassword("UnitTestPassword")
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();
        Assert.assertEquals(savedConfig.getApplication(), loadedConfig.getApplication());
        Assert.assertEquals(savedConfig.getPersistenceType(), loadedConfig.getPersistenceType());
        Assert.assertEquals(savedConfig.getPersistenceMode(), loadedConfig.getPersistenceMode());
        Assert.assertEquals(savedConfig.getGatewayBaseUrl(), loadedConfig.getGatewayBaseUrl());
        Assert.assertEquals(savedConfig.getConsumerKey(), loadedConfig.getConsumerKey());
        Assert.assertEquals(savedConfig.getConsumerSecret(), loadedConfig.getConsumerSecret());
        Assert.assertEquals(savedConfig.getStandalonePropertyFile(), loadedConfig.getStandalonePropertyFile());
    }

    @Test
    public void test_15_ConfigSaveLoadXml() {
        ApiConfiguration savedConfig = createPropertyFileConfiguration(SPHEREON_AUTH_XML);
        ApiConfiguration loadedConfig = loadPropertyFileConfiguration(SPHEREON_AUTH_XML);
        Assert.assertEquals(savedConfig.getApplication(), loadedConfig.getApplication());
        Assert.assertEquals(savedConfig.getPersistenceType(), loadedConfig.getPersistenceType());
        Assert.assertEquals(savedConfig.getPersistenceMode(), loadedConfig.getPersistenceMode());
        Assert.assertEquals(savedConfig.getGatewayBaseUrl(), loadedConfig.getGatewayBaseUrl());
        Assert.assertEquals(savedConfig.getConsumerKey(), loadedConfig.getConsumerKey());
        Assert.assertEquals(savedConfig.getConsumerSecret(), loadedConfig.getConsumerSecret());
        Assert.assertEquals(savedConfig.getStandalonePropertyFile(), loadedConfig.getStandalonePropertyFile());
    }


    @Test
    public void test_20_ConfigUseCaseUI() {
        ApiConfiguration config1 = createPropertyFileConfiguration(UI_TEST_PROPERTIES);
        PasswordGrant passwordGrant = new Grant.PasswordGrantBuilder()
                .withUserName("initialUser")
                .withPassword("initalPassword")
                .build();
        config1.setDefaultGrant(passwordGrant);
        config1.persist();

        ApiConfiguration config2 = loadPropertyFileConfiguration(UI_TEST_PROPERTIES);
        Assert.assertNotNull(config2.getDefaultGrant());
        changeGrantValues(config2);
        config2.setGatewayBaseUrl("blah blah");
        config2.persist();

        ApiConfiguration config3 = loadPropertyFileConfiguration(UI_TEST_PROPERTIES);
        Assert.assertEquals(config2.getApplication(), config3.getApplication());
        Assert.assertEquals(config2.getPersistenceType(), config3.getPersistenceType());
        Assert.assertEquals(config2.getPersistenceMode(), config3.getPersistenceMode());
        Assert.assertNotEquals(config1.getGatewayBaseUrl(), config3.getGatewayBaseUrl());
        Assert.assertEquals(config2.getGatewayBaseUrl(), config3.getGatewayBaseUrl());
        Assert.assertEquals(config2.getConsumerKey(), config3.getConsumerKey());
        Assert.assertEquals(config2.getConsumerSecret(), config3.getConsumerSecret());
        Assert.assertEquals(config2.getStandalonePropertyFile(), config3.getStandalonePropertyFile());

        PasswordGrant loadedPasswordGrant = (PasswordGrant) config3.getDefaultGrant();
        Assert.assertEquals(passwordGrant.getUserName(), loadedPasswordGrant.getUserName());
        Assert.assertNotEquals(passwordGrant.getPassword(), loadedPasswordGrant.getPassword());
    }


    @Test
    public void test_30_PasswordGrant() {
        ApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        PasswordGrant initialGrant = new Grant.PasswordGrantBuilder()
                .withUserName("initialUser")
                .withPassword("initialPassword")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        ApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        PasswordGrant loadedGrant = (PasswordGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getUserName(), loadedGrant.getUserName());
        Assert.assertEquals(initialGrant.getPassword(), loadedGrant.getPassword());
    }


    @Test
    public void test_32_RefreshTokenGrant() {
        ApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        RefreshTokenGrant initialGrant = new Grant.RefreshTokenGrantBuilder()
                .withRefreshToken("initialRefreshToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        ApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        RefreshTokenGrant loadedGrant = (RefreshTokenGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getRefreshToken(), loadedGrant.getRefreshToken());
    }


    @Test
    public void test_34_NtlmGrant() {
        ApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        NtlmGrant initialGrant = new Grant.NtlmGrantBuilder()
                .withWindowsToken("initialWindowsToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        ApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        NtlmGrant loadedGrant = (NtlmGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getWindowsToken(), loadedGrant.getWindowsToken());
    }


    @Test
    public void test_36_KerberosGrant() {
        ApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        KerberosGrant initialGrant = new Grant.KerberosGrantBuilder()
                .withKerberosRealm("initialRealm")
                .withKerberosToken("initialToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        ApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        KerberosGrant loadedGrant = (KerberosGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getKerberosRealm(), loadedGrant.getKerberosRealm());
        Assert.assertEquals(initialGrant.getKerberosToken(), loadedGrant.getKerberosToken());
    }


    @Test
    public void test_38_Saml2Grant() {
        ApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        SAML2Grant initialGrant = new Grant.Saml2GrantBuilder()
                .withAssertion("initialAssertion")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        ApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        SAML2Grant loadedGrant = (SAML2Grant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getAssertion(), loadedGrant.getAssertion());
    }


    @Test
    public void test_40_TestAutoEncrypt() throws Exception {
        File manualPropertiesFile = new File("./config/" + MANUAL_PROPERTIES);
        manualPropertiesFile.delete();
        InputStream templateStream = getClass().getResourceAsStream(MANUAL_TEMPLATE_PROPERTIES);
        FileUtils.copyInputStreamToFile(templateStream, manualPropertiesFile);
        ApiConfiguration loadedConfig = loadPropertyFileConfiguration(MANUAL_PROPERTIES);
        Assert.assertNotNull(loadedConfig);
        assertPropertyValues(manualPropertiesFile, "authentication-api.expiring-tokens.consumer-secret=ENC(*)",
                "authentication-api.expiring-tokens.password=ENC(*)");
    }


    @Test
    public void test_45_TestNoAutoEncrypt() throws Exception {
        File manualPropertiesFile = new File("./config/" + MANUAL_PROPERTIES);
        manualPropertiesFile.delete();
        InputStream templateStream = getClass().getResourceAsStream(MANUAL_TEMPLATE_PROPERTIES);
        FileUtils.copyInputStreamToFile(templateStream, manualPropertiesFile);
        ApiConfiguration loadedConfig = new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .withStandaloneConfigFile(new File("./config/" + MANUAL_PROPERTIES))
                .withAutoEncryptSecrets(false)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

        Assert.assertNotNull(loadedConfig);
        assertPropertyValues(manualPropertiesFile, "authentication-api.expiring-tokens.consumer-secret=v1XDT6Mdh_5xcCod1fnyUMYsZXsa",
                "authentication-api.expiring-tokens.password=K@A$yG@Vwpq4Ow1W@Q2b");
    }


    @Test
    public void test_50_InMemoryConfig() {
        PasswordGrant grant = new Grant.PasswordGrantBuilder()
                .withUserName("testUser")
                .withPassword("testPassword")
                .build();

        ApiConfiguration config1 = new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.IN_MEMORY)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withDefaultGrant(grant)
                .build();

        ApiConfiguration config2 = new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.IN_MEMORY).build();
        Assert.assertEquals(config1.getConsumerKey(), config2.getConsumerKey());
        Assert.assertEquals(config1.getConsumerSecret(), config2.getConsumerSecret());

        PasswordGrant loadedGrant = (PasswordGrant) config2.getDefaultGrant();
        Assert.assertEquals(grant.getUserName(), loadedGrant.getUserName());
        Assert.assertEquals(grant.getPassword(), loadedGrant.getPassword());
    }


    @Test
    public void test_60_SystemEnvConfig() throws Exception {
        final String testUserName = "testUserName";
        final String testPassword = "ENC(8DBFLfsbf8WhjmBZjDQJWQqJKs9+wCww)";
        final String consumerSecret = "v1XDT6Mdh_5xcCod1fnyUMYsZXsa";
        final String consumerKey = "gJ33aNcX3Zj3iqMQhyfQc4AIpfca";
        setEnv("AUTHENTICATION-API_EXPIRING-TOKENS_GRANT-TYPE", "password");
        setEnv("AUTHENTICATION-API_EXPIRING-TOKENS_USERNAME", testUserName);
        setEnv("AUTHENTICATION-API_EXPIRING-TOKENS_PASSWORD", testPassword);
        System.setProperty("authentication-api.expiring-tokens.consumer-key", consumerKey);
        System.setProperty("authentication-api.expiring-tokens.consumer-secret", consumerSecret);

        ApiConfiguration config = new ApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.SYSTEM_ENVIRONMENT)
                .withAutoEncryptSecrets(true)
                .build();
        Assert.assertEquals(consumerKey, config.getConsumerKey());
        Assert.assertEquals(consumerSecret, config.getConsumerSecret());

        PasswordGrant loadedGrant = (PasswordGrant) config.getDefaultGrant();
        Assert.assertNotNull(loadedGrant);
        Assert.assertEquals(testUserName, loadedGrant.getUserName());
        Assert.assertEquals(testPassword, loadedGrant.getPassword());
    }


}
