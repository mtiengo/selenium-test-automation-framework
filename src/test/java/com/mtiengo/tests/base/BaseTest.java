package com.mtiengo.tests.base;

import com.mtiengo.base.BasePage;
import com.mtiengo.pages.HomePage;
import com.mtiengo.utilities.CreateDriverUtility;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.mtiengo.utilities.ScreenShotUtility;

import static com.mtiengo.utilities.CreateDriverUtility.createDriver;


/**
 * Base test class that sets up WebDriver and provides common test utilities.
 * Supports multiple browsers - change the Browser enum value to test different browsers.
 */
public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    private static final String DEMOQA_URL = "https://demoqa.com/";

    @BeforeClass
    public void setUp() {
        driver = createDriver(CreateDriverUtility.Browser.CHROME); // Change browser here to test with different browsers
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void loadApplication() {
        driver.get(DEMOQA_URL);
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void takeFailedResultScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            ScreenShotUtility.captureScreenshot(driver, testResult.getName());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}