package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigTest extends AbstractTest {

    protected static final String UI_TEST_PROPERTIES = "sphereon-ui-test.properties";


    @Test
    public void test_10_ConfigSaveLoad() {
        TokenApiConfiguration savedConfig = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        TokenApiConfiguration loadedConfig = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        Assert.assertEquals(savedConfig.getApplication(), loadedConfig.getApplication());
        Assert.assertEquals(savedConfig.getPersistenceType(), loadedConfig.getPersistenceType());
        Assert.assertEquals(savedConfig.getPersistenceMode(), loadedConfig.getPersistenceMode());
        Assert.assertEquals(savedConfig.getGatewayBaseUrl(), loadedConfig.getGatewayBaseUrl());
        Assert.assertEquals(savedConfig.getConsumerKey(), loadedConfig.getConsumerKey());
        Assert.assertEquals(savedConfig.getConsumerSecret(), loadedConfig.getConsumerSecret());
        Assert.assertEquals(savedConfig.getStandalonePropertyFilePath(), loadedConfig.getStandalonePropertyFilePath());
    }

    @Test
    public void test_15_ConfigSaveLoadXml() {
        TokenApiConfiguration savedConfig = createPropertyFileConfiguration(SPHEREON_AUTH_XML);
        TokenApiConfiguration loadedConfig = loadPropertyFileConfiguration(SPHEREON_AUTH_XML);
        Assert.assertEquals(savedConfig.getApplication(), loadedConfig.getApplication());
        Assert.assertEquals(savedConfig.getPersistenceType(), loadedConfig.getPersistenceType());
        Assert.assertEquals(savedConfig.getPersistenceMode(), loadedConfig.getPersistenceMode());
        Assert.assertEquals(savedConfig.getGatewayBaseUrl(), loadedConfig.getGatewayBaseUrl());
        Assert.assertEquals(savedConfig.getConsumerKey(), loadedConfig.getConsumerKey());
        Assert.assertEquals(savedConfig.getConsumerSecret(), loadedConfig.getConsumerSecret());
        Assert.assertEquals(savedConfig.getStandalonePropertyFilePath(), loadedConfig.getStandalonePropertyFilePath());
    }


    @Test
    public void test_20_ConfigUseCaseUI() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(UI_TEST_PROPERTIES);
        PasswordGrant passwordGrant = new Grant.PasswordGrantBuilder()
                .withUserName("initialUser")
                .withPassword("initalPassword")
                .build();
        config1.setDefaultGrant(passwordGrant);
        config1.persist();

        TokenApiConfiguration config2 = loadPropertyFileConfiguration(UI_TEST_PROPERTIES);
        Assert.assertNotNull(config2.getDefaultGrant());
        changeGrantValues(config2);
        config2.setGatewayBaseUrl("blah blah");
        config2.persist();

        TokenApiConfiguration config3 = loadPropertyFileConfiguration(UI_TEST_PROPERTIES);
        Assert.assertEquals(config2.getApplication(), config3.getApplication());
        Assert.assertEquals(config2.getPersistenceType(), config3.getPersistenceType());
        Assert.assertEquals(config2.getPersistenceMode(), config3.getPersistenceMode());
        Assert.assertNotEquals(config1.getGatewayBaseUrl(), config3.getGatewayBaseUrl());
        Assert.assertEquals(config2.getGatewayBaseUrl(), config3.getGatewayBaseUrl());
        Assert.assertEquals(config2.getConsumerKey(), config3.getConsumerKey());
        Assert.assertEquals(config2.getConsumerSecret(), config3.getConsumerSecret());
        Assert.assertEquals(config2.getStandalonePropertyFilePath(), config3.getStandalonePropertyFilePath());

        PasswordGrant loadedPasswordGrant = (PasswordGrant) config3.getDefaultGrant();
        Assert.assertEquals(passwordGrant.getUserName(), loadedPasswordGrant.getUserName());
        Assert.assertNotEquals(passwordGrant.getPassword(), loadedPasswordGrant.getPassword());
    }


    @Test
    public void test_30_PasswordGrant() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        PasswordGrant initialGrant = new Grant.PasswordGrantBuilder()
                .withUserName("initialUser")
                .withPassword("initialPassword")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        TokenApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        PasswordGrant loadedGrant = (PasswordGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getUserName(), loadedGrant.getUserName());
        Assert.assertEquals(initialGrant.getPassword(), loadedGrant.getPassword());
    }


    @Test
    public void test_32_RefreshTokenGrant() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        RefreshTokenGrant initialGrant = new Grant.RefreshTokenGrantBuilder()
                .withRefreshToken("initialRefreshToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        TokenApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        RefreshTokenGrant loadedGrant = (RefreshTokenGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getRefreshToken(), loadedGrant.getRefreshToken());
    }


    @Test
    public void test_34_NtlmGrant() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        NtlmGrant initialGrant = new Grant.NtlmGrantBuilder()
                .withWindowsToken("initialWindowsToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        TokenApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        NtlmGrant loadedGrant = (NtlmGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getWindowsToken(), loadedGrant.getWindowsToken());
    }


    @Test
    public void test_36_KerberosGrant() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        KerberosGrant initialGrant = new Grant.KerberosGrantBuilder()
                .withKerberosRealm("initialRealm")
                .withKerberosToken("initialToken")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        TokenApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        KerberosGrant loadedGrant = (KerberosGrant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getKerberosRealm(), loadedGrant.getKerberosRealm());
        Assert.assertEquals(initialGrant.getKerberosToken(), loadedGrant.getKerberosToken());
    }


    @Test
    public void test_34_Saml2Grant() {
        TokenApiConfiguration config1 = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        SAML2Grant initialGrant = new Grant.Saml2GrantBuilder()
                .withAssertion("initialAssertion")
                .build();
        config1.setDefaultGrant(initialGrant);
        config1.persist();
        TokenApiConfiguration config2 = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);
        SAML2Grant loadedGrant = (SAML2Grant) config2.getDefaultGrant();
        Assert.assertEquals(initialGrant.getAssertion(), loadedGrant.getAssertion());
    }
}