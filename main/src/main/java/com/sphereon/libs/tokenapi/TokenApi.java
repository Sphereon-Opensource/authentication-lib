package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.config.PersistenceMode;
import com.sphereon.libs.tokenapi.config.PersistenceType;
import com.sphereon.libs.tokenapi.config.TokenApiConfiguration;
import com.sphereon.libs.tokenapi.impl.TokenApiImpl;
import com.sphereon.libs.tokenapi.impl.config.TokenApiConfigurationImpl;

@SuppressWarnings("unused")
public interface TokenApi {

    TokenResponse generateToken(GenerateTokenRequest tokenRequest);

    void revokeToken(RevokeTokenRequest revokeTokenRequest);

    TokenRequestFactory getTokenRequestFactory();

    GrantFactory getGrantFactory();

    TokenApiConfiguration getConfiguration();

    void persistConfiguration();


    interface ConfigurationUpdate {
        void update(TokenApiConfiguration tokenApiConfiguration);
    }


    final class Builder {
        private final TokenApiConfiguration tokenApiConfiguration;


        public Builder(String application) {
            tokenApiConfiguration = new TokenApiConfigurationImpl(application);
            tokenApiConfiguration.setPersistenceType(PersistenceType.DISABLED);
            tokenApiConfiguration.setPersistenceMode(PersistenceMode.READ_ONLY);
        }


        public TokenApi.Builder configure(ConfigurationUpdate configurationUpdate) {
            configurationUpdate.update(tokenApiConfiguration);
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
            return new TokenApiImpl(tokenApiConfiguration);
        }
    }
}