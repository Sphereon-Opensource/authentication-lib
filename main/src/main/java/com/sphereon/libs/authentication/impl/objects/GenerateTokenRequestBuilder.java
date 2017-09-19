package com.sphereon.libs.authentication.impl.objects;

import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenRequestBuilder;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.config.ConfigManagerProvider;
import com.sphereon.libs.authentication.impl.config.ConfigPersistence;
import com.sphereon.libs.authentication.impl.objects.granttypes.ClientCredentialBuilder;
import org.apache.commons.lang3.StringUtils;


public interface GenerateTokenRequestBuilder {

    class Builder implements TokenRequestBuilder {

        private final ApiConfiguration configuration;

        private String consumerKey;
        private String consumerSecret;
        private Grant grant;
        private String scope;
        private Long validityPeriodInSeconds;


        public Builder(ApiConfiguration configuration) {
            this.configuration = configuration;
        }


        public GenerateTokenRequestBuilder.Builder withConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withGrant(Grant grant) {
            this.grant = grant;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withScope(String scope) {
            this.scope = scope;
            return this;
        }


        public GenerateTokenRequestBuilder.Builder withValidityPeriod(Long validityPeriodInSeconds) {
            this.validityPeriodInSeconds = validityPeriodInSeconds;
            return this;
        }


        @Override
        public TokenRequest build() {
            validate();
            ConfigManagerProvider configManagerProvider = (ConfigManagerProvider) configuration;
            ConfigManager configManager = configManagerProvider.getConfigManager();
            ConfigPersistence configPersistence = (ConfigPersistence) grant;
            configPersistence.loadConfig(configManager);
            GenerateTokenRequestImpl tokenRequest = new GenerateTokenRequestImpl(configuration);
            tokenRequest.setGrant(grant);
            tokenRequest.setConsumerKey(consumerKey);
            tokenRequest.setConsumerSecret(consumerSecret);
            tokenRequest.setScope(scope);
            tokenRequest.setValidityPeriodInSeconds(validityPeriodInSeconds);
            return tokenRequest;
        }


        private void validate() {
            if (this.grant == null) {
                this.grant = configuration.getDefaultGrant();
            }
            if (this.grant == null) {
                this.grant = new ClientCredentialBuilder.ClientCredentialGrantBuilder().build();
            }

            if (StringUtils.isEmpty(consumerKey)) {
                this.consumerKey = configuration.getConsumerKey();
            }
            if (StringUtils.isEmpty(consumerSecret)) {
                this.consumerSecret = configuration.getConsumerSecret();
            }
            if (StringUtils.isEmpty(scope)) {
                this.scope = configuration.getDefaultScope();
            }

            if (validityPeriodInSeconds == null) {
                this.validityPeriodInSeconds = configuration.getDefaultValidityPeriod();
            }
        }
    }
}