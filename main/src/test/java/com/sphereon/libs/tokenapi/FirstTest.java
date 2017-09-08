package com.sphereon.libs.tokenapi;

import com.sphereon.libs.tokenapi.granttypes.ClientCredentialsGrant;
import com.sphereon.libs.tokenapi.impl.TokenApiImpl;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class FirstTest extends TestCase {

    private TokenApi tokenApi = new TokenApiImpl();


    @Test
    public void test() {
        ClientCredentialsGrant grant = tokenApi.newClientCredentialsGrant();
        GenerateTokenRequest tokenRequest = tokenApi.newGenerateTokenRequest("ExpiringTokens", grant);
        tokenRequest.setValidityPeriod(Duration.ofMinutes(1));
        tokenRequest.setScope("UnitTest");
        tokenRequest.setConsumerKey("gJ33aNcX3Zj3iqMQhyfQc4AIpfca");
        tokenRequest.setConsumerSecret("v1XDT6Mdh_5xcCod1fnyUMYsZXsa");
        TokenResponse tokenResponse = tokenApi.generateToken(tokenRequest);
        Assert.assertNotNull(tokenResponse.getAccessToken());
    }
}
