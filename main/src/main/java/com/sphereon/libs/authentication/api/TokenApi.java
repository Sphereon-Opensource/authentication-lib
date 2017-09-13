package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.grantbuilders.GrantBuilder;
import com.sphereon.libs.authentication.impl.TokenApiImpl;
import com.sphereon.libs.authentication.impl.config.TokenApiConfigurationImpl;

@SuppressWarnings("unused")
public interface TokenApi {

    TokenResponse requestToken(TokenRequest tokenRequest);

    void revokeToken(TokenRequest revokeTokenRequest);

    TokenApiConfiguration getConfiguration();

    void persistConfiguration();

    GrantBuilder.Builder grantBuilder();

    TokenRequestBuilder.Builder tokenRequestBuilder();

    interface ConfigurationUpdate {
        void update(TokenApiConfiguration tokenApiConfiguration);
    }


    final class Builder {
        private final TokenApiConfiguration tokenApiConfiguration;


        public Builder() {
            tokenApiConfiguration = new TokenApiConfigurationImpl();
            tokenApiConfiguration.setPersistenceType(PersistenceType.DISABLED);
            tokenApiConfiguration.setPersistenceMode(PersistenceMode.READ_ONLY);
        }


        public TokenApi.Builder configure(ConfigurationUpdate configurationUpdate) {
            configurationUpdate.update(tokenApiConfiguration);
            return this;
        }


        public TokenApi.Builder withApplication(String application) {
            tokenApiConfiguration.setApplication(application);
            return this;
        }


        public TokenApi.Builder withGatewayBaseUrl(String gatewayBaseUrl) {
            tokenApiConfiguration.setGatewayBaseUrl(gatewayBaseUrl);
            return this;
        }


        public TokenApi.Builder withPersistenceType(PersistenceType persistenceType) {
            tokenApiConfiguration.setPersistenceType(persistenceType);
            return this;
        }


        public TokenApi.Builder withPersistenceMode(PersistenceMode persistenceMode) {
            tokenApiConfiguration.setPersistenceMode(persistenceMode);
            return this;
        }


        public TokenApi.Builder setStandaloneConfigPath(String standaloneConfigPath) {
            tokenApiConfiguration.setStandalonePropertyFilePath(standaloneConfigPath);
            return this;
        }


        public TokenApi build() {
            validate();
            return new TokenApiImpl(tokenApiConfiguration);
        }


        private void validate() {
            // TODO
        }
    }
}