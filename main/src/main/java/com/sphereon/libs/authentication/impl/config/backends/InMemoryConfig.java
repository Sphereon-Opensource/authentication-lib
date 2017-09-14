package com.sphereon.libs.authentication.impl.config.backends;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;

public class InMemoryConfig extends AbstractCommonsConfig {

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
