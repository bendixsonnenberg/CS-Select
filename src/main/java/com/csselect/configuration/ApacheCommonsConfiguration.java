package com.csselect.configuration;

import com.google.inject.Inject;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * Apache Commons Configuration based implementation of the {@link Configuration} interface
 */
public final class ApacheCommonsConfiguration implements Configuration {

    private static final String DEFAULT_CONFIG_PATH = "/";

    private FileBasedConfiguration configuration;

    @Inject
    private ApacheCommonsConfiguration() {
        this(DEFAULT_CONFIG_PATH);
    }

    /**
     * Constructor used to allow instantiation in test cases without dependency injection
     * @param configPath path to test config file
     */
    ApacheCommonsConfiguration(String configPath) {
        File configFile = new File(configPath);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased().setFile(configFile));
        try {
            this.configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            System.err.println("Reading of configuration-file failed! Stacktrace:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String getOrganiserPassword() {
        return configuration.getString("organiserpassword");
    }

    @Override
    public String getMLServerURL() {
        return configuration.getString("mlserverurl");
    }

    @Override
    public String getDatabaseType() {
        return configuration.getString("database.type");
    }

    @Override
    public String getDatabaseHostname() {
        return configuration.getString("database.hostname");
    }

    @Override
    public int getDatabasePort() {
        return configuration.getInt("database.port");
    }

    @Override
    public String getDatabaseUsername() {
        return configuration.getString("database.username");
    }

    @Override
    public String getDatabasePassword() {
        return configuration.getString("database.password");
    }

    @Override
    public String getHomeDirectory() {
        return configuration.getString("homedirectory");
    }
}
