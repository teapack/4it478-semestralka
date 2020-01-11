package cz.vratislavjindra.rukovoditel.selenium.login;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import cz.vratislavjindra.rukovoditel.selenium.utils.User;
import org.junit.Test;

/**
 * Class for testing the 'successful login' test case.
 *
 * @author Vratislav Jindra
 * @version 202001111512
 */
public class ValidLoginTest extends BaseLoginTest {

    // The user who attempts to log in.
    User user;

    @Override
    public void init() {
        super.init();
        user = User.SYSTEM_ADMINISTRATOR;
    }

    /**
     * Tests that we can log in using valid credentials.
     */
    @Test
    public void shouldLoginUsingValidCredentials() {
        // GIVEN we are on the website we're testing
        driver.get(Constants.TESTING_URL);
        // AND valid username is entered
        enterUsername(true, user);
        // AND valid password is entered
        enterPassword(true, user);
        // WHEN we click on the login button
        clickOnLoginButton();
        // THEN user is logged in
        checkLoginResult(true, user);
    }
}