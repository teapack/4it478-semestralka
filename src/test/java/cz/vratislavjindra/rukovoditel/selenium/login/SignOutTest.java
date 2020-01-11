package cz.vratislavjindra.rukovoditel.selenium.login;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Class for testing the 'sign out' test case.
 *
 * @author Vratislav Jindra
 * @version 202001111515
 */
public class SignOutTest extends BaseLoginTest {

    private ValidLoginTest loginTest;

    @Override
    public void init() {
        super.init();
        loginTest = new ValidLoginTest();
        loginTest.setChromeDriver(driver);
    }

    /**
     * Tests that we can sign out.
     */
    @Test
    public void shouldSignOut() {
        // GIVEN we are logged in
        loginTest.shouldLoginUsingValidCredentials();
        // AND we have the user drop down menu displayed
        WebElement userDropDown = driver.findElement(By.cssSelector(".dropdown.user"));
        userDropDown.click();
        // WHEN we click on the sign out button
        WebElement signOutButton = driver.findElement(By.cssSelector(".fa.fa-sign-out"));
        signOutButton.click();
        // THEN user is signed out (the login form is displayed)
        WebElement loginFormTitle = driver.findElement(By.className("form-title"));
        Assert.assertEquals("Login", loginFormTitle.getText());
    }
}