package cz.vratislavjindra.rukovoditel.selenium.login;

import cz.vratislavjindra.rukovoditel.selenium.BaseTest;
import cz.vratislavjindra.rukovoditel.selenium.utils.User;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Base class for testing login functionality of the app.
 *
 * @author Vratislav Jindra
 * @version 202001111454
 */
public abstract class BaseLoginTest extends BaseTest {

    /**
     * Checks the login result. Pass true as the parameter if the user should be logged in, false otherwise.
     *
     * @param shouldBeLoggedIn True as the parameter if the user should be logged in, false otherwise.
     */
    void checkLoginResult(boolean shouldBeLoggedIn, User loggedInUser) {
        if (shouldBeLoggedIn) {
            Assert.assertEquals(loggedInUser.getDisplayedName(), driver.findElement(By.className("username"))
                    .getText());
        } else {
            Assert.assertTrue(driver.findElement(By.cssSelector(".alert.alert-danger")).getText()
                    .contains("No match for Username and/or Password."));
        }
    }

    /**
     * Performs click on the 'Login' button.
     */
    void clickOnLoginButton() {
        // TODO Find a better way to locate the element.
        driver.findElement(By.cssSelector(".btn.btn-info.pull-right")).click();
    }

    /**
     * Enters password to the 'password' field.
     *
     * @param validPassword True if we want to enter valid password, false otherwise.
     * @param user          The user whose password we want to enter.
     */
    void enterPassword(boolean validPassword, User user) {
        WebElement passwordInput = driver.findElement(By.name("password"));
        if (validPassword) {
            passwordInput.sendKeys(user.getPassword());
        } else {
            passwordInput.sendKeys("_" + user.getPassword());
        }
    }

    /**
     * Enters username to the 'username' field.
     *
     * @param validUsername True if we want to enter valid username, false otherwise.
     * @param user          The user whose username we want to enter.
     */
    void enterUsername(boolean validUsername, User user) {
        WebElement usernameInput = driver.findElement(By.name("username"));
        if (validUsername) {
            usernameInput.sendKeys(user.getUsername());
        } else {
            usernameInput.sendKeys("_" + user.getUsername());
        }
    }
}