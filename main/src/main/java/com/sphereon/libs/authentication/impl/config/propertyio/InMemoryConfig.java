package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;

public class InMemoryConfig extends AbstractCommonsConfig {

    private final PropertiesConfiguration config;


    public InMemoryConfig(PersistenceMode persistenceMode) {
        super(persistenceMode);
        BasicConfigurationBuilder<PropertiesConfiguration> builder = new BasicConfigurationBuilder<>(PropertiesConfiguration.class);
        try {
            this.config = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize PropertyFileBackend / InMemoryConfig", e);
        }
    }
}
