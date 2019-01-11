package com.csselect.configuration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class ConfigurationTests {

    private Configuration config;
    private static final String TEST_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";

    @Before
    public void initialise() {
        config = new ApacheCommonsConfiguration(TEST_PATH);
    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(config);
    }

    @Test
    public void testOrganiserPassword() {
        testString("sicherespasswort", config.getOrganiserPassword());
    }

    @Test
    public void testMLServerURL() {
        testString("127.0.0.1", config.getMLServerURL());
    }

    @Test
    public void testDatabaseType() {
        testString("mysql", config.getDatabaseType());
    }

    @Test
    public void testDatabaseHostname() {
        testString("127.0.0.1", config.getDatabaseHostname());
    }

    @Test
    public void testDatabasePort() {
        Assert.assertEquals(3306, config.getDatabaseport());
    }

    @Test
    public void testDatabaseUsername() {
        testString("admin", config.getDatabaseUsername());
    }
    @Test
    public void testDatabasePassword() {
        testString("4dm1n", config.getDatabasePassword());
    }

    @Test
    public void testHomeDirectory() {
        testString("/test/CSSelect", config.getHomeDirectory());
    }

    private void testString(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }
}
