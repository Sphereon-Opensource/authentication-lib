package com.sphereon.libs.authentication.api;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.authentication.api.granttypes.PasswordGrant;
import com.sphereon.libs.authentication.api.granttypes.RefreshTokenGrant;
import com.sphereon.libs.authentication.impl.TokenApiImpl;
import com.sphereon.libs.authentication.impl.config.TokenApiConfigurationImpl;
import com.sphereon.libs.authentication.impl.objects.RevokeTokenRequestBuilder;
import com.sphereon.libs.authentication.impl.objects.granttypes.GrantBuilder;

@SuppressWarnings("unused")
public interface TokenApi {

    TokenResponse requestToken(GenerateTokenRequest tokenRequest);

    void revokeToken(RevokeTokenRequest revokeTokenRequest);

    TokenApiConfiguration getConfiguration();

    void persistConfiguration();

    <T extends GrantBuilder> T grantBuilder(Class<T> grantBuilderClass);

    <T extends TokenRequestBuilder> T tokenRequestBuilder(Class<T> tokenRequestBuilderClass);

    interface ConfigurationUpdate {
        void update(TokenApiConfiguration tokenApiConfiguration);
    }


    final class TokenRequestBuilders {
        public static final Class<GenerateTokenRequest.Builder> GENERATE = GenerateTokenRequest.Builder.class;
        public static final Class<RevokeTokenRequestBuilder.Builder> REVOKE_TOKEN = RevokeTokenRequestBuilder.Builder.class;
    }


    final class GrantBuilders {
        public static final Class<ClientCredentialsGrant.Builder> CLIENT_CREDENTIALS = ClientCredentialsGrant.Builder.class;
        public static final Class<PasswordGrant.Builder> PASSWORD_GRANT = PasswordGrant.Builder.class;
        public static final Class<RefreshTokenGrant.Builder> REFRESH_TOKEN_GRANT = RefreshTokenGrant.Builder.class;
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