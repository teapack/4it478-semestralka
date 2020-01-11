package cz.vratislavjindra.rukovoditel.selenium.utils;

/**
 * Enum with users definition.
 *
 * @author Vratislav Jindra
 * @version 202001111501
 */
public enum User {

    SYSTEM_ADMINISTRATOR("rukovoditel", "vse456ru", "System Administrator");

    private String username;
    private String password;
    private String displayedName;

    User(String username, String password, String displayedName) {
        this.username = username;
        this.password = password;
        this.displayedName = displayedName;
    }

    /**
     * Returns the user's user name.
     *
     * @return The user's user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's name that is displayed when the user logs in.
     *
     * @return The user's name that is displayed when the user logs in.
     */
    public String getDisplayedName() {
        return displayedName;
    }
}