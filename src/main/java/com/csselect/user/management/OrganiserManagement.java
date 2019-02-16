package com.csselect.user.management;

import com.csselect.inject.Injector;
import com.csselect.configuration.Configuration;
import com.csselect.user.Organiser;
import com.csselect.user.User;
import com.csselect.user.management.safety.Encrypter;

/**
 * Management class for registration and login of {@link Organiser}
 */
public final class OrganiserManagement extends UserManagement {
    /**
     * Error String if Email is in use (registration)
     */
    public static final String EMAIL_IN_USE = "Email is already in use";

    /**
     * Error string if master password is incorrect (registration)
     */
    public static final String MASTER_PASSWORD_INCORRECT = "Wrong master password";

    /**
     * Register an organiser with 3 parameters email, password and global password
     * @param parameters Registration parameters
     * @return {@link Organiser} object
     * @throws IllegalArgumentException if email is in use or masterpassword is incorrect
     */
    public Organiser register(String[] parameters) throws IllegalArgumentException {
        assert parameters.length == 3;
        Configuration config = Injector.getInstance().getConfiguration();
        String email = parameters[0];
        String password = parameters[1];
        String globalPassword = parameters[2];

        if (config.getOrganiserPassword().equals(globalPassword)) {
            String salt = Encrypter.getRandomSalt();
            String encryptedPassword = Encrypter.encrypt(password, salt);
            Organiser organiser = Injector.getInstance().getDatabaseAdapter().createOrganiser(email, encryptedPassword, salt);
            if (organiser != null) {
                organiser.login();
                return organiser;
            } else {
                throw new IllegalArgumentException(EMAIL_IN_USE);
            }
        } else {
            throw new IllegalArgumentException(MASTER_PASSWORD_INCORRECT);
        }
    }

    /**
     * To login an {@link Organiser}, we need to know the email and the password he typed in. If the
     * password is correct, return an organiser object, return null otherwise.
     * @param email Email of the organiser
     * @param password Password he typed in
     * @return {@link Organiser} object or null
     */
    public Organiser login(String email, String password) {
        Organiser organiser = Injector.getInstance().getDatabaseAdapter().getOrganiser(email);
        if (organiser == null) {
            return null; //email not found
        }
        String savedEncryptedPassword = Injector.getInstance().getDatabaseAdapter().getOrganiserHash(organiser.getId());
        String salt = Injector.getInstance().getDatabaseAdapter().getOrganiserSalt(organiser.getId());
        if (Encrypter.compareStringToHash(password, salt, savedEncryptedPassword)) {
            organiser.login();
            return organiser;
        } else {
            return null;
        }
    }

    @Override
    User getUser(String email) {
        return Injector.getInstance().getDatabaseAdapter().getOrganiser(email);
    }
}
