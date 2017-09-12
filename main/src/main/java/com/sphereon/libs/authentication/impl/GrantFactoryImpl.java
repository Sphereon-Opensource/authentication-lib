package com.sphereon.libs.authentication.impl;

import com.sphereon.libs.authentication.api.GrantFactory;
import com.sphereon.libs.authentication.api.granttypes.*;
import com.sphereon.libs.authentication.impl.config.ConfigManager;
import com.sphereon.libs.authentication.impl.objects.granttypes.*;

public class GrantFactoryImpl implements GrantFactory {

    private final ConfigManager configManager;


    public GrantFactoryImpl(ConfigManager configManager) {
        this.configManager = configManager;
    }


    @Override
    public ClientCredentialsGrant createClientCredentialsGrant() {
        return new ClientCredentialsGrantImpl();
    }


    @Override
    public PasswordGrant createPasswordGrant() {
        PasswordGrant grant = new PasswordGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public PasswordGrant.Builder createPasswordGrantBuilder() {
        return new PasswordGrant.Builder(configManager);
    }


    @Override
    public RefreshTokenGrant createRefreshTokenGrant() {
        RefreshTokenGrantImpl grant = new RefreshTokenGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public NtlmGrant createNtlmGrant() {
        NtlmGrantImpl grant = new NtlmGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public KerberosGrant createKerberosGrant() {
        KerberosGrantImpl grant = new KerberosGrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }


    @Override
    public SAML2Grant createSaml2Grant() {
        SAML2GrantImpl grant = new SAML2GrantImpl();
        configManager.loadGrant(grant);
        return grant;
    }
}
