package cz.vratislavjindra.rukovoditel.selenium.project;

import cz.vratislavjindra.rukovoditel.selenium.BaseTest;
import cz.vratislavjindra.rukovoditel.selenium.login.ValidLoginTest;
import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base class for testing the projects functionality of the app.
 *
 * @author Vratislav Jindra
 * @version 202001111517
 */
public abstract class BaseProjectTest extends BaseTest {

    ValidLoginTest loginTest;

    @Override
    public void init() {
        super.init();
        loginTest = new ValidLoginTest();
        loginTest.setChromeDriver(driver);
    }

    /**
     * Checks the result of the save project operation.
     *
     * @param shouldBeSaved True if the expected save project operations is expected to be successful (if project is
     *                      expected to be saved), false otherwise.
     */
    void checkSaveProjectResult(boolean shouldBeSaved, String projectName) {
        if (shouldBeSaved && projectName != null) {
            WebElement newProjectNameLink = driver.findElement(By.linkText(projectName));
            Assert.assertNotNull(newProjectNameLink);
            Assert.assertEquals(projectName, newProjectNameLink.getText());
            Assert.assertNotNull(driver.findElement(By.linkText("Project Info")));
        } else {
            Assert.assertEquals("display: inline-block;", driver.findElement(By.id("fields_158-error"))
                    .getAttribute("style"));
        }
    }

    /**
     * Performs click on the 'Add Project' button.
     */
    void clickOnAddProjectButton() {
        driver.findElement(By.xpath("//button[@class='btn btn-primary'][.='Add Project']")).click();
    }

    /**
     * Performs click on the 'Delete Project' button.
     */
    void clickOnDeleteProjectButton() {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement deleteButton = driver.findElement(By.xpath("//a[@title='Delete']"));
            if (deleteButton != null) {
                deleteButton.click();
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * Confirms deletion of a project with the given name.
     *
     * @param projectName Name of the project which we want to delete.
     */
    void confirmDeletion(@Nonnull String projectName) {
        // Sometimes we have to wait until the confirmation dialog appears.
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement alertMessage = driver.findElement(By.className("modal-body"));
            if (alertMessage != null) {
                Assert.assertTrue(alertMessage.getText().contains("Are you sure you want to delete \"" + projectName
                        + "\"?"));
                driver.findElement(By.id("delete_confirm")).click();
                driver.findElement(By.xpath("//button[@class='btn btn-primary btn-primary-modal-action'][.='Delete']"))
                        .click();
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * Enters the given date to the date field.
     *
     * @param date The date to be entered to the date field.
     */
    void enterDate(@Nonnull String date) {
        driver.findElement(By.id("fields_159")).sendKeys(date);
    }

    /**
     * Enters the given project name to the project name input.
     *
     * @param projectName The project name to be entered to the project name input field. If it's null or empty,
     *                    everything is removed from the project name text area.
     */
    void enterProjectName(@Nullable String projectName) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement projectNameInput = driver.findElement(By.id("fields_158"));
            if (projectNameInput != null) {
                if (projectName != null && !projectName.isEmpty()) {
                    projectNameInput.sendKeys(projectName);
                } else {
                    projectNameInput.clear();
                }
                return true;
            } else {
                return false;
            }
        });
    }
}