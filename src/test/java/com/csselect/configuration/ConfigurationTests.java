package com.csselect.configuration;

import com.csselect.TestClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ConfigurationTests extends TestClass {

    private Configuration config;
    private static final String TEST_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";

    @Override
    public void setUp() {
        config = new ApacheCommonsConfiguration(TEST_PATH);
    }

    @Override
    public void reset() {

    }

    @Test
    public void loadingTest() { Assert.assertNotNull(config); }

    @Test
    public void testOrganiserPassword() {
        testString("sicherespasswort", config.getOrganiserPassword());
    }

    @Test
    public void testMLServerURL() {
        testString("127.0.0.1:8000", config.getMLServerURL());
    }

    @Test
    public void testTimezone() {
        testString("CET", config.getTimezone());
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
        Assert.assertEquals(3306, config.getDatabasePort());
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
