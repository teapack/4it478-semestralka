package cz.vratislavjindra.rukovoditel.selenium;

import cz.vratislavjindra.rukovoditel.selenium.utils.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Base superclass of all test cases.
 */
public abstract class BaseTest {

    protected ChromeDriver driver;

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
     * Performs click on the 'projects' menu item.
     */
    protected void clickOnProjectsMenuItem() {
        driver.findElement(By.linkText("Projects")).click();
    }

    /**
     * Performs click on the 'Save' button.
     */
    protected void clickOnSaveButton() {
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
     * Filters projects list by the given project name.
     *
     * @param projectName Project name by which we want to filter the projects list.
     */
    protected void filterProjectsByName(@Nonnull String projectName) {
        resetSearch(false);
        WebElement searchInput = driver.findElement(By.id("entity_items_listing66_21_search_keywords"));
        searchInput.clear();
        searchInput.sendKeys(projectName);
        driver.findElement(By.xpath("//button[@title='Search']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            List<WebElement> tableRows = driver.findElements(By.cssSelector("table tr"));
            if (tableRows != null && tableRows.size() == 2) {
                WebElement projectTableRow = tableRows.get(1);
                WebElement newProjectLink = projectTableRow.findElement(By.className("item_heading_link"));
                if (newProjectLink.getText().equals(projectName)) {
                    Assert.assertEquals(projectName, newProjectLink.getText());
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
     * Resets the search so that other classmates don't cry that my searches break their tests.
     *
     * @param wait True if we want to wait until the reset button appears. False if we don't want to wait. When this
     *             parameter is false, we don't need the reset search link appears.
     */
    protected void resetSearch(boolean wait) {
        if (wait) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
            webDriverWait.until((ExpectedCondition<Boolean>) webDriver -> {
                WebElement resetSearchLink = driver.findElement(By.className("reset_search"));
                if (resetSearchLink != null) {
                    resetSearchLink.click();
                    return true;
                } else {
                    return false;
                }
            });
        } else {
            boolean isResetSearchLinkPresent = driver.findElements(By.className("reset_search")).size() > 0;
            if (isResetSearchLinkPresent) {
                driver.findElement(By.className("reset_search")).click();
            }
        }
    }

    /**
     * Selects the given value from the combo box with the specified ID.
     *
     * @param comboBoxId ID of the combo box from which we want to select a value.
     * @param value      The value which we want to select.
     */
    protected void selectComboBoxValue(@Nonnull String comboBoxId, @Nonnull String value) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_SECONDS);
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            WebElement comboBoxWebElement = driver.findElement(By.id(comboBoxId));
            if (comboBoxWebElement != null) {
                new Select(comboBoxWebElement).selectByValue(value);
                return true;
            } else {
                return false;
            }
        });
    }
}