package com.sphereon.libs.tokenapi.impl;

import com.sphereon.libs.tokenapi.GrantFactory;
import com.sphereon.libs.tokenapi.granttypes.*;
import com.sphereon.libs.tokenapi.impl.config.ConfigManager;
import com.sphereon.libs.tokenapi.impl.objects.granttypes.*;

public class GrantFactoryImpl implements GrantFactory {

    private final ConfigManager configManager;


    public GrantFactoryImpl(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public ClientCredentialsGrant clientCredentialsGrant() {
        return new ClientCredentialsGrantImpl();
    }


    @Override
    public PasswordGrant passwordGrant() {
        PasswordGrantImpl grant = new PasswordGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public RefreshTokenGrant refreshTokenGrant() {
        RefreshTokenGrantImpl grant = new RefreshTokenGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public NtlmGrant ntlmGrant() {
        NtlmGrantImpl grant = new NtlmGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public KerberosGrant kerberosGrant() {
        KerberosGrantImpl grant = new KerberosGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public SAML2Grant saml2Grant() {
        SAML2GrantImpl grant = new SAML2GrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }
}
