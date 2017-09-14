package com.sphereon.libs.authentication;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.*;

import java.io.File;
import java.time.Duration;

public abstract class AbstractTest {

    protected static final String APPLICATION_NAME = "expiring-tokens";

    protected static final String SPHEREON_AUTH_PROPERTIES = "sphereon-auth.properties";
    protected static final String SPHEREON_AUTH_XML = "sphereon-auth.xml";


    protected TokenApiConfiguration createPropertyFileConfiguration(final String configFile) {
        String standaloneConfigPath = "./config/" + configFile;
        File f = new File(standaloneConfigPath);
        f.delete();
        return new TokenApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath(standaloneConfigPath)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .build();
    }


    protected TokenApiConfiguration loadPropertyFileConfiguration(final String configFile) {
        return new TokenApiConfiguration.Builder()
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.STANDALONE_PROPERTY_FILE)
                .setStandaloneConfigPath("./config/" + configFile)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();
    }


    protected void changeGrantValues(TokenApiConfiguration loadedConfig) {
        switch (loadedConfig.getDefaultGrant().getGrantType()) {
            case CLIENT_CREDENTIALS:
                break;
            case REFRESH_TOKEN:
                RefreshTokenGrant refreshTokenGrant = (RefreshTokenGrant) loadedConfig.getDefaultGrant();
                refreshTokenGrant.setRefreshToken("New refreshTokenGrant");
                break;
            case PASSWORD:
                PasswordGrant passwordGrant = (PasswordGrant) loadedConfig.getDefaultGrant();
                passwordGrant.setPassword("New password");
                break;
            case NTLM:
                NtlmGrant ntlmGrant = (NtlmGrant) loadedConfig.getDefaultGrant();
                ntlmGrant.setWindowsToken("New windows token");
                break;
            case KERBEROS:
                KerberosGrant kerberosGrant = (KerberosGrant) loadedConfig.getDefaultGrant();
                kerberosGrant.setKerberosToken("New kerberos token");
                break;
            case SAML2:
                SAML2Grant saml2Grant = (SAML2Grant) loadedConfig.getDefaultGrant();
                saml2Grant.setAssertion("New assertion");
                break;
        }
    }


    protected void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis() + 1000);
        } catch (InterruptedException e) {
        }
    }

}
