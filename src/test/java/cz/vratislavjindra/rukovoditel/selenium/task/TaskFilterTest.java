package cz.vratislavjindra.rukovoditel.selenium.task;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import cz.vratislavjindra.rukovoditel.selenium.utils.TaskStatus;
import org.junit.Test;

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

    @Override
    public void init() {
        super.init();
        createTaskTest = new CreateTaskTest();
        createTaskTest.setChromeDriver(driver);
        createTaskTest.setLoginTest(loginTest);
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
        for (int i = 0; i < 7; i++) {
            createTaskTest.setTaskName("Task number " + i);
            createTaskTest.setTaskDescription("Task number " + i + " " + UUID.randomUUID().toString());
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
        List<TaskStatus> appliedFilters = new ArrayList<>();
        appliedFilters.add(TaskStatus.NEW);
        appliedFilters.add(TaskStatus.OPEN);
        appliedFilters.add(TaskStatus.WAITING);
        verifyAppliedStatusFilters(appliedFilters);
        cancelTaskStatusFilter(TaskStatus.OPEN);
        appliedFilters.remove(TaskStatus.OPEN);
        verifyAppliedStatusFilters(appliedFilters);
    }
}