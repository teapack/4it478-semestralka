package cz.vratislavjindra.rukovoditel.selenium.project;

import cz.vratislavjindra.rukovoditel.selenium.BaseTest;
import cz.vratislavjindra.rukovoditel.selenium.login.ValidLoginTest;
import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
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
    void checkSaveProjectResult(boolean shouldBeSaved) {
        if (shouldBeSaved) {

        } else {
            WebElement errorLabel = driver.findElement(By.id("fields_158-error"));
            Assert.assertEquals("display: inline-block;", errorLabel.getAttribute("style"));
        }
    }

    /**
     * Performs click on the 'Add Project' button.
     */
    void clickOnAddProjectButton() {
        WebElement addProjectButton = driver.findElement(By
                .xpath("//button[@class='btn btn-primary'][.='Add Project']"));
        addProjectButton.click();
    }

    /**
     * Performs click on the 'projects' menu item.
     */
    void clickOnProjectsMenuItem() {
        WebElement projectsMenuItem = driver.findElement(By.linkText("Projects"));
        projectsMenuItem.click();
    }

    /**
     * Performs click on the 'Save' button.
     */
    void clickOnSaveButton() {
        // Sometimes we have to wait until the save button appears.
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement saveButton = driver.findElement(By
                    .xpath("//button[@class='btn btn-primary btn-primary-modal-action'][.='Save']"));
            if (saveButton != null) {
                saveButton.click();
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
        WebElement dateInput = driver.findElement(By.id("fields_159"));
        dateInput.sendKeys(date);
    }

    /**
     * Enters the given project name to the project name input.
     *
     * @param projectName The project name to be entered to the project name input field. If it's null or empty,
     *                    everything is removed from the project name text area.
     */
    void enterProjectName(@Nullable String projectName) {
        WebElement projectNameInput = driver.findElement(By.id("fields_158"));
        if (projectName != null && !projectName.isEmpty()) {
            projectNameInput.sendKeys(projectName);
        } else {
            projectNameInput.clear();
        }
    }

    /**
     * Selects the given value from the combo box with the specified ID.
     *
     * @param comboBoxId ID of the combo box from which we want to select a value.
     * @param value      The value which we want to select.
     */
    void selectComboBoxValue(@Nonnull String comboBoxId, @Nonnull String value) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement comboBoxWebElement = driver.findElement(By.id(comboBoxId));
            if (comboBoxWebElement != null) {
                Select comboBox = new Select(comboBoxWebElement);
                comboBox.selectByValue(value);
                return true;
            } else {
                return false;
            }
        });
    }
}