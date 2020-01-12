package cz.vratislavjindra.rukovoditel.selenium.task;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import cz.vratislavjindra.rukovoditel.selenium.utils.TaskStatus;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for testing the task filtering feature.
 *
 * @author Vratislav Jindra
 * @version 202001111945
 */
public class TaskFilterTest extends BaseTaskTest {

    private CreateTaskTest createTaskTest;
    private String taskNamePrefix = "Task number ";

    @Override
    public void init() {
        super.init();
        createTaskTest = new CreateTaskTest();
        createTaskTest.setChromeDriver(driver);
        createTaskTest.setLoginTest(loginTest);
    }

    @Override
    public void tearDown() {
        driver.findElement(By.name("select_all_items")).click();
        driver.findElement(By
                .xpath("//button[@class='btn btn-default dropdown-toggle'][normalize-space()='With Selected']"))
                .click();
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement deleteButton = driver.findElement(By
                    .xpath("//a[@class=' link-to-modalbox'][normalize-space()='Delete']"));
            if (deleteButton != null) {
                deleteButton.click();
                return true;
            } else {
                return false;
            }
        });
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement confirmDeleteButton = driver.findElement(By
                    .xpath("//button[@class='btn btn-primary btn-primary-modal-action'][.='Delete']"));
            if (confirmDeleteButton != null) {
                confirmDeleteButton.click();
                return true;
            } else {
                return false;
            }
        });
        super.tearDown();
    }

    /**
     * Tests the filtering feature.
     */
    @Test
    public void shouldFilterCorrectly() {
        // GIVEN we are logged in
        loginTest.shouldLoginUsingValidCredentials();
        // AND we are creating a new task at a project screen
        clickOnProjectsMenuItem();
        filterProjectsByName(Constants.THE_BEST_PROJECT_NAME);
        openProject(Constants.THE_BEST_PROJECT_NAME);
        // AND we create 7 new tasks (each with different status)
        for (int i = 0; i < 7; i++) {
            createTaskTest.setTaskName(taskNamePrefix + i);
            createTaskTest.setTaskDescription(taskNamePrefix + i + " " + UUID.randomUUID().toString());
            TaskStatus taskStatus;
            switch (i) {
                case 0:
                    taskStatus = TaskStatus.NEW;
                    break;
                case 1:
                    taskStatus = TaskStatus.OPEN;
                    break;
                case 2:
                    taskStatus = TaskStatus.WAITING;
                    break;
                case 3:
                    taskStatus = TaskStatus.DONE;
                    break;
                case 4:
                    taskStatus = TaskStatus.CLOSED;
                    break;
                case 5:
                    taskStatus = TaskStatus.PAID;
                    break;
                default:
                    taskStatus = TaskStatus.CANCELED;
            }
            createTaskTest.setTaskStatus(taskStatus);
            createTaskTest.createTask();
        }
        // WHEN we set filters to statuses New, Open and Waiting
        setDefaultFilters();
        List<TaskStatus> appliedFilters = new ArrayList<>();
        appliedFilters.add(TaskStatus.NEW);
        appliedFilters.add(TaskStatus.OPEN);
        appliedFilters.add(TaskStatus.WAITING);
        // THEN only New, Open and Waiting tasks are displayed
        verifyAppliedStatusFilters(appliedFilters);

        // I have no idea how to continue with the GIVEN-WHEN-THEN comments now since it's supposed to be one test case,
        // but there are multiple triggers (multiple WHENs) with different outcomes (different THENs).

        // WHEN we set filters to statuses New and Waiting (we remove the filter for OPEN status)
        clickOnAppliedFilters();
        cancelTaskStatusFilter(TaskStatus.OPEN);
        appliedFilters.remove(TaskStatus.OPEN);
        // THEN only New and Waiting tasks are displayed
        verifyAppliedStatusFilters(appliedFilters);

        // WHEN we remove all filters
        removeAllFilters();
        appliedFilters.add(TaskStatus.OPEN);
        appliedFilters.add(TaskStatus.DONE);
        appliedFilters.add(TaskStatus.CLOSED);
        appliedFilters.add(TaskStatus.PAID);
        appliedFilters.add(TaskStatus.CANCELED);
        // THEN all tasks are displayed
        verifyAllTasksAreDisplayed(7, taskNamePrefix);
    }
}