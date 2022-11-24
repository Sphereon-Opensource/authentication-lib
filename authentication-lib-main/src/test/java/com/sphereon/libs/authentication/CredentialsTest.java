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

import com.sphereon.libs.authentication.api.AuthenticationApi;
import com.sphereon.libs.authentication.api.TokenRequest;
import com.sphereon.libs.authentication.api.TokenResponse;
import com.sphereon.libs.authentication.api.config.ApiConfiguration;
import com.sphereon.libs.authentication.api.config.ClientCredentialsMode;
import com.sphereon.libs.authentication.api.config.PersistenceMode;
import com.sphereon.libs.authentication.api.config.PersistenceType;
import com.sphereon.libs.authentication.api.granttypes.Grant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.atomic.AtomicReference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CredentialsTest extends AbstractTest {

    private static final Long VALIDITY_PERIOD = 10L;
    private static final Long SAFETY_MARGIN_SECONDS = 30L;

    private static final AtomicReference<String> prevToken = new AtomicReference<>();
    private static final AtomicReference<String> refreshToken = new AtomicReference<>();


    public CredentialsTest() {

    }


    @Test
    public void test_10_ClientCredential() {
        AuthenticationApi authenticationApi = new AuthenticationApi.Builder().build();
        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withClientCredentialsMode(ClientCredentialsMode.BASIC_HEADER)
                .withConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca")
                .withConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa")
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD + SAFETY_MARGIN_SECONDS)
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        this.prevToken.set(tokenResponse.getAccessToken());
        waitSeconds(2L);
        Assert.assertFalse(tokenResponse.isExpired());
        tokenResponse = tokenRequest.execute();
        Assert.assertFalse(tokenResponse.isExpired());
        waitSeconds(tokenResponse.getExpiresInSeconds());
        Assert.assertTrue(tokenResponse.isExpired());
        tokenResponse = tokenRequest.execute();
        Assert.assertFalse(tokenResponse.isExpired());
    }

    @Test
    public void test_12_ClientCredentialBodyAzure() {
        ApiConfiguration configuration = new ApiConfiguration.Builder()
                .withGatewayBaseUrl("https://login.microsoftonline.com/e2a42b2f-7460-4499-afc2-425315ef058a/oauth2/v2.0")
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.IN_MEMORY)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withClientCredentialsMode(ClientCredentialsMode.BODY)
                .withConsumerKey("6b2b3ffe-cc9a-4e5a-968a-613a847237e5")
                .withConsumerSecret("URb8Q~jFhn8KKw08s5izc4cxCdhcEmRy4s0e0cLG")
                .withScope("https://graph.microsoft.com/.default")
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
    }

    @Test
    public void test_14_ClientCredentialBodySharepoint() {
        ApiConfiguration configuration = new ApiConfiguration.Builder()
                .withGatewayBaseUrl("https://accounts.accesscontrol.windows.net/e2a42b2f-7460-4499-afc2-425315ef058a")
                .withTokenEndpointPath("tokens/OAuth/2")
                .withApplication(APPLICATION_NAME)
                .withPersistenceType(PersistenceType.IN_MEMORY)
                .withPersistenceMode(PersistenceMode.READ_WRITE)
                .build();

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withClientCredentialsMode(ClientCredentialsMode.BODY)
                .withConsumerKey("9a78b885-4d8c-4a3a-a409-6989d6711d8a@e2a42b2f-7460-4499-afc2-425315ef058a")
                .withConsumerSecret("iuDRQ1MrLJY1Uqyf8pMjWbk3xd9kfEH4y9THe3mNgZM=")
                .withResource("00000003-0000-0ff1-ce00-000000000000/sphereon.sharepoint.com@e2a42b2f-7460-4499-afc2-425315ef058a")
                .build();
        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
    }


    @Test
    public void test_20_UserPassword() {

        ApiConfiguration configuration = createPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.PasswordGrantBuilder()
                .withUserName("SphereonTest")
                .withPassword("K@A$yG@Vwpq4Ow1W@Q2b")
                .build();

        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        String refreshToken = tokenResponse.getRefreshToken();
        Assert.assertNotNull(refreshToken);
        Assert.assertNotEquals(this.prevToken.get(), tokenResponse.getAccessToken());
        this.prevToken.set(tokenResponse.getAccessToken());
        this.refreshToken.set(refreshToken);
        waitSeconds(VALIDITY_PERIOD);
    }


    @Test
    public void test_30_RefreshToken() {
        Assert.assertNotNull(refreshToken.get());
        ApiConfiguration configuration = loadPropertyFileConfiguration(SPHEREON_AUTH_PROPERTIES);

        AuthenticationApi authenticationApi = new AuthenticationApi.Builder()
                .withConfiguration(configuration)
                .build();

        Grant grant = new Grant.RefreshTokenGrantBuilder()
                .withRefreshToken(refreshToken.get())
                .build();


        TokenRequest tokenRequest = authenticationApi.requestToken()
                .withGrant(grant)
                .withScope("UnitTest")
                .withValidityPeriod(VALIDITY_PERIOD)
                .build();

        TokenResponse tokenResponse = tokenRequest.execute();
        Assert.assertNotNull(tokenResponse.getAccessToken());
        Assert.assertNotNull(tokenResponse.getRefreshToken());
        Assert.assertNotEquals(this.prevToken.get(), tokenResponse.getAccessToken());
    }
}
