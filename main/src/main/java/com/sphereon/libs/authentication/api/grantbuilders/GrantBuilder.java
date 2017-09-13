package com.sphereon.libs.authentication.api.grantbuilders;

import com.sphereon.libs.authentication.api.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.granttypes.*;

public interface GrantBuilder {

    Grant build();


    final class Builder {

        private final ConfigManager configManager;


        public Builder(ConfigManager configManager) {
            this.configManager = configManager;
        }


        public ClientCredentialsBuilder.Builder clientCredentialsGrantBuilder() {
            return new ClientCredentialsGrantBuilder.Builder(configManager);
        }


        public PasswordBuilder.Builder passwordGrantBuilder() {
            return new PasswordBuilder.Builder(configManager);
        }


        public RefreshTokenBuilder.Builder refreshTokenGrantBuilder() {
            return new RefreshTokenBuilder.Builder(configManager);
        }


        public NtlmGrantBuilder.Builder ntlmGrantBuilder() {
            return new NtlmGrantBuilder.Builder(configManager);
        }


        public KerberosGrantBuilder.Builder kerberosGrantBuilder() {
            return new KerberosBuilder.Builder(configManager);
        }


        public SAML2GrantBuilder.Builder saml2GrantBuilder() {
            return new Saml2Builder.Builder(configManager);
        }
    }

}
