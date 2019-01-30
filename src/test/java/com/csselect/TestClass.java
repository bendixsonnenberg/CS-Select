package com.csselect;

import com.csselect.database.mock.MockPlayerAdapter;
import com.csselect.gamification.Leaderboard;
import org.junit.After;
import org.junit.Before;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public abstract class TestClass {

    @Before
    public void enableTestmode() {
        com.csselect.Injector.useTestMode();
        setUp();
    }

    /**
     * Method runs before every unit test
     * Annotated with @Before
     */
    public abstract void setUp();

    @After
    public void resetInjector() throws Exception {
        Injector.resetInjector();
        reset();
        resetStaticFinal(MockPlayerAdapter.class.getDeclaredField("PLAYERSTATS_ADAPTERS"), new HashMap<>());
        resetStaticFinal(Leaderboard.class.getDeclaredField("instance"), null);
    }

    /**
     * This method is run after every unit test
     * Annotated with @After
     */
    public abstract void reset();

    private void resetStaticFinal(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
