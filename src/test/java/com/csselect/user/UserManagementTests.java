package com.csselect.user;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.configuration.Configuration;
import com.csselect.user.management.OrganiserManagement;
import com.csselect.user.management.PlayerManagement;
import org.junit.Assert;
import org.junit.Test;

public class UserManagementTests extends TestClass {
    PlayerManagement pm;
    Player darth;
    Player luke;
    OrganiserManagement om;
    Organiser harry;
    Organiser voldemort;
    String globalPassword;

    @Override
    public void setUp() {
        pm = new PlayerManagement();
        om = new OrganiserManagement();
        globalPassword = Injector.getInjector().getInstance(Configuration.class).getOrganiserPassword();
    }

    @Override
    public void reset() {
        darth = null;
        luke = null;
        harry = null;
        voldemort = null;
    }

    @Test
    public void testValidRegistrationPlayer() {
        String[] args = new String[3];
        args[0] = "skywalker1@csselect.com";
        args[1] = "Nein!11!!1";
        args[2] = "Luke1";

        luke = pm.register(args);
        Assert.assertNotNull(luke);

        Assert.assertTrue(luke.isLoggedIn());
        Assert.assertTrue(luke.getGames().isEmpty());
        Assert.assertNotNull(luke.getStats());
        Assert.assertNull(luke.getActiveRound());
        Assert.assertTrue(luke.getRounds().isEmpty());
    }

    @Test
    public void testTwoValidRegistrationsPlayer() {
        String[] args1 = new String[3];
        args1[0] = "vader1@csselect.com";
        args1[1] = "IchBinDeinVater1968";
        args1[2] = "Darth1";

        String[] args2 = new String[3];
        args2[0] = "skywalker2@csselect.com";
        args2[1] = "Nein!11!!1";
        args2[2] = "Luke2";

        darth = pm.register(args1);
        Assert.assertNotNull(darth);
        luke = pm.register(args2);
        Assert.assertNotNull(luke);
        Assert.assertNotSame(darth, luke);
    }

    @Test
    public void testDuplicateEmailRegistrationPlayer() {
        String[] args1 = new String[3];
        args1[0] = "vader2@csselect.com";
        args1[1] = "IchBinDeinVater1968";
        args1[2] = "Darth2";

        String[] args2 = new String[3];
        args2[0] = "vader2@csselect.com";
        args2[1] = "Nein!11!!1";
        args2[2] = "Luke3";

        darth = pm.register(args1);
        luke = pm.register(args2);

        Assert.assertNotNull(darth);
        Assert.assertNull(luke);
    }

    @Test
    public void testDuplicateUsernameRegistrationPlayer() {
        String[] args1 = new String[3];
        args1[0] = "vader3@csselect.com";
        args1[1] = "IchBinDeinVater1968";
        args1[2] = "Darth3";

        String[] args2 = new String[3];
        args2[0] = "luke3@csselect.com";
        args2[1] = "Nein!11!!1";
        args2[2] = "Darth3";

        darth = pm.register(args1);
        luke = pm.register(args2);

        Assert.assertNotNull(darth);
        Assert.assertNull(luke);
    }

    @Test
    public void testValidRegistrationOrganiser() {
        String[] args = new String[3];
        args[0] = "voldi1@csselect.com";
        args[1] = "#ElDeRsTaB!#";
        args[2] = globalPassword;

        voldemort = om.register(args);
        Assert.assertNotNull(voldemort);

        Assert.assertTrue(voldemort.isLoggedIn());
        Assert.assertNotNull(voldemort.getActiveGames());
        Assert.assertTrue(voldemort.getActiveGames().isEmpty());
        Assert.assertNotNull(voldemort.getTerminatedGames());
        Assert.assertTrue(voldemort.getTerminatedGames().isEmpty());
        Assert.assertNotNull(voldemort.getPatterns());
        Assert.assertTrue(voldemort.getPatterns().isEmpty());
    }

    @Test
    public void testTwoValidRegistrationsOrganiser() {
        String[] args1 = new String[3];
        args1[0] = "voldi2@csselect.com";
        args1[1] = "#ElDeRsTaB!#";
        args1[2] = globalPassword;

        String[] args2 = new String[3];
        args2[0] = "harry1@csselect.com";
        args2[1] = "#FiniteIncantatem!#";
        args2[2] = globalPassword;

        voldemort = om.register(args1);
        Assert.assertNotNull(voldemort);
        harry = om.register(args2);
        Assert.assertNotNull(harry);
        Assert.assertNotSame(harry, voldemort);
    }

    @Test
    public void testDuplicateEmailRegistrationOrganiser() {
        String[] args1 = new String[3];
        args1[0] = "voldi3@csselect.com";
        args1[1] = "#ElDeRsTaB!#";
        args1[2] = globalPassword;

        String[] args2 = new String[3];
        args2[0] = "voldi3@csselect.com";
        args2[1] = "#FiniteIncantatem!#";
        args2[2] = globalPassword;

        voldemort = om.register(args1);
        harry = om.register(args2);

        Assert.assertNotNull(voldemort);
        Assert.assertNull(harry);
    }
}
