package cz.vratislavjindra.rukovoditel.selenium.project;

import org.junit.Test;

/**
 * Class for testing the 'create project without name' test case.
 *
 * @author Vratislav Jindra
 * @version 202001111539
 */
public class CreateProjectWithoutNameTest extends BaseProjectTest {

    /**
     * Tests that we can not create a project without name.
     */
    @Test
    public void shouldNotCreateProjectWithoutName() {
        // GIVEN we are logged in
        loginTest.shouldLoginUsingValidCredentials();
        // AND we are creating a new project at the projects screen
        clickOnProjectsMenuItem();
        clickOnAddProjectButton();
        // AND no project name is filled
        enterProjectName(null);
        // WHEN we click on the save button
        clickOnSaveButton();
        // THEN no project is saved (error label is displayed)
        checkSaveProjectResult(false, null);
    }
}