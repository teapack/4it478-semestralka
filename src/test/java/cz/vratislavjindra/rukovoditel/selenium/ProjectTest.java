package cz.vratislavjindra.rukovoditel.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

/**
 * Class for testing logic regarding projects.
 * <p>We can only get to the projects screen after logging in, so it makes sense to extend the {@link LoginTest} class.
 * That way we can access web driver from the {@link LoginTest} and we can use the login method
 * ({@link LoginTest#shouldLoginUsingValidCredentials()}) from the {@link LoginTest} class and then work with the same
 * web driver in this class.</p>
 *
 * @author Vratislav Jindra
 * @version 202001102203
 */
public class ProjectTest extends LoginTest {

    /**
     * Tests that we can log in using valid credentials.
     */
    @Test
    public void shouldNotCreateProjectWithoutName() {
        // GIVEN we are logged in
        shouldLoginUsingValidCredentials();
        // AND we are creating a new project at the projects screen
        clickOnProjectsMenuItem();
        clickOnAddProjectButton();
        // AND no project name is filled
        enterProjectName(null);
        // WHEN we click on the save button
        clickOnSaveButton();
        // THEN no project is saved ()

    }

    /**
     * Performs click on the 'Add Project' button.
     */
    private void clickOnAddProjectButton() {
        WebElement addProjectButton = driver.findElement(By.linkText("Add Project"));
        addProjectButton.click();
    }

    /**
     * Performs click on the 'projects' menu item.
     */
    private void clickOnProjectsMenuItem() {
        WebElement projectsMenuItem = driver.findElement(By.linkText("Projects"));
        projectsMenuItem.click();
    }

    /**
     * Performs click on the 'Save' button.
     */
    private void clickOnSaveButton() {
        WebElement saveButton = driver.findElement(By.linkText("Save"));
        saveButton.click();
    }

    /**
     * Enters the given project name to the project name input.
     *
     * @param projectName The project name to be entered to the project name input field. If it's null or empty, nothing
     *                    will be entered anywhere.
     */
    private void enterProjectName(@Nullable String projectName) {
        if (projectName != null && !projectName.isEmpty()) {
            WebElement projectNameInput = driver.findElement(By.id("fields_158"));
            projectNameInput.sendKeys(projectName);
        }
    }
}