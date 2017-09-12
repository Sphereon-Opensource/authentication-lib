package com.sphereon.libs.authentication.impl.config.propertyio;

import com.sphereon.libs.authentication.api.config.PersistenceMode;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;

import java.io.File;
import java.net.URI;

public class PropertyFileIO extends AbstractCommonsConfig {


    public PropertyFileIO(PersistenceMode persistenceMode, URI fileUri) {
        super(persistenceMode);

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFile(new File(fileUri))
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        builder.setAutoSave(persistenceMode == PersistenceMode.READ_WRITE);
        try {
            this.config = builder.getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize PropertyFileIO for " + fileUri, e);
        }
    }


}
