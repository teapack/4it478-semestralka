package cz.vratislavjindra.rukovoditel.selenium.task;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public class CreateTaskTest extends BaseTaskTest {

    // Name of the task which we're creating.
    private String taskName;
    // Description of the task which we're creating.
    private String taskDescription;

    @Override
    public void init() {
        super.init();
        taskName = "jinv00-" + UUID.randomUUID().toString();
        taskDescription = "jinv00-" + UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }

    @Override
    public void tearDown() {
        WebElement taskDropdown = driver.findElement(By.cssSelector(".btn.btn-default.btn-sm.dropdown-toggle"));
        taskDropdown.click();
        WebElement deleteTaskButton = driver.findElement(By.cssSelector(".fa.fa-trash-o"));
        deleteTaskButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement deleteButton = driver.findElement(By
                    .xpath("//button[@class='btn btn-primary btn-primary-modal-action'][.='Delete']"));
            if (deleteButton != null) {
                deleteButton.click();
                return true;
            } else {
                return false;
            }
        });
        super.tearDown();
    }

    /**
     * Tests that we can create a task with type 'Task', status 'New', priority 'Medium' and not empty name and some
     * description.
     */
    @Test
    public void shouldCreateTaskWithTypeTaskStatusNewPriorityMediumNotEmptyNameNoEmptyDescription() {
        // GIVEN we are logged in
        loginTest.shouldLoginUsingValidCredentials();
        // AND we are creating a new task at a project screen
        clickOnProjectsMenuItem();
        filterProjectsByName(Constants.THE_BEST_PROJECT_NAME);
        openProject(Constants.THE_BEST_PROJECT_NAME);
        clickOnAddTaskButton();
        // AND task type is set as 'Task'
        selectComboBoxValue("fields_167", "42");
        // AND some task name is filled
        enterTaskName(taskName);
        // AND task status is set as 'New'
        selectComboBoxValue("fields_169", "46");
        // AND task priority is set as 'Medium'
        selectComboBoxValue("fields_170", "55");
        // And some description is filled
        enterTaskDescription(taskDescription);
        // WHEN we click on the save button
        clickOnSaveButton();
        // THEN the task is saved and we can find it in the tasks list
        filterTasksByName(taskName);
        // AND the task info page displays correct info
        clickOnTaskInfoButton();
        checkTaskAttribute("form-group-167", "Task");
        checkTaskDescription(taskDescription);
        checkTaskName(taskName);
        checkTaskAttribute("form-group-170", "Medium");
        checkTaskAttribute("form-group-169", "New");
    }
}