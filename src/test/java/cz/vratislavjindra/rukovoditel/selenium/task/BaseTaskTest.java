package cz.vratislavjindra.rukovoditel.selenium.task;

import cz.vratislavjindra.rukovoditel.selenium.BaseTest;
import cz.vratislavjindra.rukovoditel.selenium.login.ValidLoginTest;
import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Base class for testing the tasks functionality of the app.
 *
 * @author Vratislav Jindra
 * @version 202001111742
 */
public class BaseTaskTest extends BaseTest {

    ValidLoginTest loginTest;

    @Override
    public void init() {
        super.init();
        loginTest = new ValidLoginTest();
        loginTest.setChromeDriver(driver);
    }

    /**
     * Checks that the task description is the same as the expected one.
     *
     * @param expectedTaskDescription The expected task description.
     */
    void checkTaskDescription(String expectedTaskDescription) {
        WebElement descriptionDivWrapper = driver.findElement(By.className("form-group-172"));
        Assert.assertEquals(expectedTaskDescription, descriptionDivWrapper.findElement(By
                .cssSelector(".content_box_content.fieldtype_textarea_wysiwyg")).getText());
    }

    /**
     * Checks that the task name is the same as the expected one.
     *
     * @param expectedTaskName The expected task name.
     */
    void checkTaskName(String expectedTaskName) {
        WebElement taskNameLink = driver.findElement(By.linkText(expectedTaskName));
        Assert.assertNotNull(taskNameLink);
        Assert.assertEquals(expectedTaskName, taskNameLink.getText());
    }

    /**
     * Checks that a task attribute with the given class name is the same as the expected one.
     *
     * @param expectedAttributeValue The expected attribute value.
     */
    void checkTaskAttribute(String attributeClassName, String expectedAttributeValue) {
        WebElement tableRow = driver.findElement(By.className(attributeClassName));
        Assert.assertEquals(expectedAttributeValue, tableRow.findElement(By.tagName("div")).getText());
    }

    /**
     * Performs click on the 'Add Task' button.
     */
    void clickOnAddTaskButton() {
        driver.findElement(By.xpath("//button[@class='btn btn-primary'][.='Add Task']")).click();
    }

    /**
     * Performs click on the 'Task Info' button.
     */
    void clickOnTaskInfoButton() {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement taskInfoButton = driver.findElement(By.xpath("//a[@title='Info']"));
            if (taskInfoButton != null) {
                taskInfoButton.click();
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * Enters the given task description to the task description input.
     *
     * @param taskDescription The task description to be entered to the task description input field. If it's null or
     *                        empty, everything will be removed from the task description text area.
     */
    void enterTaskDescription(@Nonnull String taskDescription) {
        WebElement taskDescriptionIFrame = driver.findElement(By.cssSelector(".cke_wysiwyg_frame.cke_reset"));
        driver.switchTo().frame(taskDescriptionIFrame);
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement taskDescriptionInput = driver.findElement(By
                    .xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']"));
            if (taskDescriptionInput != null) {
                taskDescriptionInput.clear();
                taskDescriptionInput.sendKeys(taskDescription);
                return true;
            } else {
                return false;
            }
        });
        driver.switchTo().parentFrame();
    }

    /**
     * Enters the given task name to the task name input.
     *
     * @param taskName The task name to be entered to the task name input field. If it's null or empty, everything is
     *                 removed from the task name text area.
     */
    void enterTaskName(@Nullable String taskName) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement taskNameInput = driver.findElement(By.id("fields_168"));
            if (taskNameInput != null) {
                if (taskName != null && !taskName.isEmpty()) {
                    taskNameInput.sendKeys(taskName);
                } else {
                    taskNameInput.clear();
                }
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * Filters tasks list by the given task name.
     *
     * @param taskName Task name by which we want to filter the tasks list.
     */
    void filterTasksByName(@Nonnull String taskName) {
        resetSearch(false);
        WebElement searchInput = driver.findElement(By.cssSelector(".form-control.input-medium"));
        searchInput.clear();
        searchInput.sendKeys(taskName);
        driver.findElement(By.xpath("//button[@title='Search']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            List<WebElement> tableRows = driver.findElements(By.cssSelector("table tr"));
            if (tableRows != null && tableRows.size() == 2) {
                WebElement taskTableRow = tableRows.get(1);
                WebElement taskLink = taskTableRow.findElement(By.className("item_heading_link"));
                if (taskLink.getText().equals(taskName)) {
                    Assert.assertEquals(taskName, taskLink.getText());
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        });
    }

    /**
     * Opens project with the given name.
     *
     * @param projectName Name of the project which we want to open.
     */
    void openProject(@Nonnull String projectName) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        webDriverWait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement projectLink = driver.findElement(By.xpath("//a[@class='item_heading_link'][.='" + projectName
                    + "']"));
            if (projectLink != null) {
                try {
                    projectLink.click();
                } catch (StaleElementReferenceException e) {
                    projectLink = driver.findElement(By.xpath("//a[@class='item_heading_link'][.='" + projectName
                            + "']"));
                    projectLink.click();
                }
                return true;
            } else {
                return false;
            }
        });
    }
}