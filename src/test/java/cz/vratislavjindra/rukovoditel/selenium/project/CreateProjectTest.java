package cz.vratislavjindra.rukovoditel.selenium.project;

import cz.vratislavjindra.rukovoditel.selenium.utils.DateTimeUtils;
import org.junit.Test;

import java.util.UUID;

/**
 * Class for testing the 'create project successfully' test case.
 *
 * @author Vratislav Jindra
 * @version 202001111537
 */
public class CreateProjectTest extends BaseProjectTest {

    // Name of the project which we're creating.
    private String projectName;

    @Override
    public void init() {
        super.init();
        projectName = "jinv00-" + UUID.randomUUID().toString();
    }

    @Override
    public void tearDown() {
        clickOnDeleteProjectButton();
        confirmDeletion(projectName);
        resetSearch(true);
        super.tearDown();
    }

    /**
     * Tests that we can create a project with status 'New', priority 'High' today's start date and not empty name.
     */
    @Test
    public void shouldCreateProjectWithStatusNewPriorityHighTodaysStartDateNotEmptyName() {
        // GIVEN we are logged in
        loginTest.shouldLoginUsingValidCredentials();
        // AND we are creating a new project at the projects screen
        clickOnProjectsMenuItem();
        clickOnAddProjectButton();
        // AND project status is set as 'New'
        selectComboBoxValue("fields_157", "37");
        // AND project priority is set as 'High'
        selectComboBoxValue("fields_156", "35");
        // AND project start date is set to today's date
        String currentDate = DateTimeUtils.getCurrentDate();
        enterDate(currentDate);
        // AND some project name is filled
        enterProjectName(projectName);
        // WHEN we click on the save button
        clickOnSaveButton();
        // THEN project with the specified parameters is saved
        checkSaveProjectResult(true, projectName);
        // AND the new project can be found in the projects list
        clickOnProjectsMenuItem();
        filterProjectsByName(projectName);
    }
}