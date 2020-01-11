package cz.vratislavjindra.rukovoditel.selenium.login;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import cz.vratislavjindra.rukovoditel.selenium.utils.User;
import org.junit.Test;

/**
 * Class for testing the 'unsuccessful login' test case.
 *
 * @author Vratislav Jindra
 * @version 202001111513
 */
public class InvalidLoginTest extends BaseLoginTest {

    // The user who attempts to log in.
    User user;

    @Override
    public void init() {
        super.init();
        user = User.SYSTEM_ADMINISTRATOR;
    }

    /**
     * Tests that when we enter invalid credentials, we won't log in.
     */
    @Test
    public void shouldNotLoginUsingInvalidCredentials() {
        // GIVEN we are on the website we're testing
        driver.get(Constants.TESTING_URL);
        // AND valid username is entered
        enterUsername(true, user);
        // AND invalid password is entered
        enterPassword(false, user);
        // WHEN we click on the login button
        clickOnLoginButton();
        // THEN user is not logged in
        checkLoginResult(false, user);
    }
}