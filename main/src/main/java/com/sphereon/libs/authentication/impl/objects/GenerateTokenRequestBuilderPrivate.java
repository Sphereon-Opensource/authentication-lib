package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.TokenApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigManagerProvider;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialsBuilder;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

public interface GenerateTokenRequestBuilderPrivate {

    class Builder implements TokenRequestBuilder {

        private final TokenApiConfiguration tokenApiConfiguration;

        private String consumerKey;
        private String consumerSecret;
        private Grant grant;
        private String scope;
        private Duration validityPeriod;


        public Builder(TokenApiConfiguration tokenApiConfiguration) {
            this.tokenApiConfiguration = tokenApiConfiguration;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withGrant(Grant grant) {
            this.grant = grant;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }


        public GenerateTokenRequestBuilderPrivate.Builder withValidityPeriod(Duration validityPeriod) {
            this.validityPeriod = validityPeriod;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            ConfigManagerProvider configManagerProvider = (ConfigManagerProvider) tokenApiConfiguration;
            ConfigManager configManager = configManagerProvider.getConfigManager();
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(configManager);
            GenerateTokenRequestImpl tokenRequest = new GenerateTokenRequestImpl(tokenApiConfiguration);
            tokenRequest.setGrant(grant);
            tokenRequest.setConsumerKey(consumerKey);
            tokenRequest.setConsumerSecret(consumerSecret);
            tokenRequest.setScope(scope);
            tokenRequest.setValidityPeriod(validityPeriod);
            return tokenRequest;
        }


        private void validate() {
            if (this.grant == null) {
                this.grant = tokenApiConfiguration.getDefaultGrant();
            }
            if (this.grant == null) {
                this.grant = new ClientCredentialsBuilder.ClientCredentialsGrantBuilder().build();
            }

            if (StringUtils.isEmpty(consumerKey)) {
                this.consumerKey = tokenApiConfiguration.getConsumerKey();
            }
            if (StringUtils.isEmpty(consumerSecret)) {
                this.consumerSecret = tokenApiConfiguration.getConsumerSecret();
            }
            if (StringUtils.isEmpty(scope)) {
                this.scope = tokenApiConfiguration.getDefaultScope();
            }

            if (validityPeriod == null) {
                this.validityPeriod = tokenApiConfiguration.getDefaultValidityPeriod();
            }
        }
    }
}