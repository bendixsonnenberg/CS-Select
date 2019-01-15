package com.csselect.API;

import com.csselect.user.User;

/**
 * The facade for all calls to the system that are not specific to organisers or players
 */
@SuppressWarnings("WeakerAccess")

public abstract class APIFacadeUser {

    protected User user;
    /** Registers a new user.
     * This creates a new user in the database.
     * Password and email are later required to log into the system with {@link APIFacadeUser#login(String, String)}
     * @param args String array of arguments for registration
     */
    public abstract void register(String[] args);

    /** logs in a user for this api. This is the first method that has to be called before any other methods start
     *  making sense.
     *
     * @param email Email of the user to login
     * @param password Password of the user to login
     *
     * @return true if login successfull, false if not
     */
    public boolean login(String email, String password) {
        if (email.equals("bendix.sonnenberg@gmx.de") && password.equals("1234")) {
            return true;
        }
        return false;
    }

    /** logs out the current user from this {@link APIFacadeUser}
     * after calling logout this object will behave as if no user has been logged in
     *
     */
    public void logout() {

    }

    /** Changes the email address of the current user.
     *
     * @param email new email address for this user
     */
    public void changeEmail(String email) {

    }

    /** Changes the password for the current user
     *
     * @param password new password for the current user
     */
    public void changePassword(String password) {

    }

    /** Changes the language for the current user. This is only relevant for the front end.
     *
     * @param languageCode language code for the new language( i.e. de-de, en-us ...)
     */
    public void setLanguage(String languageCode) {

    }

    /** sends an email to the email address of the user to recover the password
     *
     * @param email email of the user that wants their account recovered
     */
    public void recoverPassword(String email) {

    }

    /** validates the email of the currently logged in user. This means that the system can confirm that the
     * user is in control of the email account they supplied in {@link APIFacadeUser#register(String, String, String)}
     *
     */
    public void validateEmail() {

    }


}
