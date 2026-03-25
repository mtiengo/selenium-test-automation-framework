package com.mtiengo.tests.base;

import com.mtiengo.pages.HomePage;
import com.mtiengo.utilities.CreateDriverUtility;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.mtiengo.utilities.ScreenShotUtility;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.mtiengo.utilities.CreateDriverUtility.createDriver;


/**
 * Base test class that sets up WebDriver and provides common test utilities.
 * Supports multiple browsers - change the Browser enum value to test different browsers.
 */
public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    private static final String DEMOQA_URL = "https://demoqa.com/";

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = createDriver(CreateDriverUtility.Browser.fromString(browser));
        driver.manage().window().maximize();
        driver.get(DEMOQA_URL);
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDownWithScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            ScreenShotUtility.captureScreenshot(driver, testResult.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }
}