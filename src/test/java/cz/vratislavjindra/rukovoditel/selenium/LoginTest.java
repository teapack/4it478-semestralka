package cz.vratislavjindra.rukovoditel.selenium;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class for testing login to the application.
 *
 * @author Vratislav Jindra
 * @version 202001102005
 */
public class LoginTest {

    public ChromeDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    /**
     * Tests that we can log in using valid credentials.
     */
    @Test
    public void shouldLoginUsingValidCredentials() {
        // GIVEN we are on the website we're testing
        driver.get(Constants.URL_PREFIX);
        // AND valid username is entered
        enterValidUsername();
        // AND valid password is entered
        enterPassword(true);
        // WHEN we click on the login button
        clickOnLoginButton();
        // THEN user is logged in
        checkLoginResult(true);
    }

    /**
     * Tests that when we enter invalid credentials, we won't log in.
     */
    @Test
    public void shouldNotLoginUsingInvalidCredentials() {
        // GIVEN we are on the website we're testing
        driver.get(Constants.URL_PREFIX);
        // AND valid username is entered
        enterValidUsername();
        // AND invalid password is entered
        enterPassword(false);
        // WHEN we click on the login button
        clickOnLoginButton();
        // THEN user is not logged in
        checkLoginResult(false);
    }

    /**
     * Tests that we can sign out.
     */
    @Test
    public void shouldSignOut() {
        // GIVEN we are logged in
        shouldLoginUsingValidCredentials();
        // AND we have the user drop down menu displayed
        WebElement userDropDown = driver.findElement(By.cssSelector(".dropdown.user"));
        userDropDown.click();
        // WHEN we click on the sign out button
        WebElement signOutButton = driver.findElement(By.cssSelector(".fa.fa-sign-out"));
        signOutButton.click();
        // THEN user is signed out (the login form is displayed)
        WebElement loginFormTitle = driver.findElement(By.className("form-title"));
        Assert.assertEquals(loginFormTitle.getText(), "Login");
    }

    /**
     * Checks the login result. Pass true as the parameter if the user should be logged in, false otherwise.
     *
     * @param shouldBeLoggedIn True as the parameter if the user should be logged in, false otherwise.
     */
    private void checkLoginResult(boolean shouldBeLoggedIn) {
        if (shouldBeLoggedIn) {
            WebElement userNameText = driver.findElement(By.className("username"));
            Assert.assertEquals(userNameText.getText(), "System Administrator");
        } else {
            WebElement alertMessage = driver.findElement(By.cssSelector(".alert.alert-danger"));
            Assert.assertTrue(alertMessage.getText().contains("No match for Username and/or Password."));
        }
    }

    /**
     * Performs click on the 'login' button.
     */
    private void clickOnLoginButton() {
        WebElement loginButton = driver.findElement(By.cssSelector(".btn.btn-info.pull-right"));
        loginButton.click();
    }

    /**
     * Enters password to the 'password' field.
     *
     * @param validPassword True if we want to enter valid password, false otherwise.
     */
    private void enterPassword(boolean validPassword) {
        WebElement passwordInput = driver.findElement(By.name("password"));
        if (validPassword) {
            passwordInput.sendKeys("vse456ru");
        } else {
            passwordInput.sendKeys("invalid password");
        }
    }

    /**
     * Enters valid username to the 'username' field.
     */
    private void enterValidUsername() {
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
    }
}