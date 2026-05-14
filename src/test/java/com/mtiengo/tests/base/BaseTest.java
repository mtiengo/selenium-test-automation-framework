package com.mtiengo.tests.base;

import com.mtiengo.pages.HomePage;
import com.mtiengo.utilities.CreateDriverUtility;
import com.mtiengo.utilities.DriverManager;
import com.mtiengo.utilities.ScreenShotUtility;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.mtiengo.utilities.CreateDriverUtility.createDriver;

/**
 * Base test class providing thread-safe WebDriver management via ThreadLocal.
 * Each test method running in parallel gets its own isolated driver and page objects.
 */
public class BaseTest {

    private static final ThreadLocal<HomePage> homePageHolder = new ThreadLocal<>();
    private static final String DEMOQA_URL = "https://demoqa.com/";

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected HomePage getHomePage() {
        return homePageHolder.get();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        WebDriver driver = createDriver(CreateDriverUtility.Browser.fromString(browser));
        DriverManager.setDriver(driver);
        driver.get(DEMOQA_URL);
        homePageHolder.set(new HomePage(driver));
    }

    @AfterMethod
    public void tearDownWithScreenshot(ITestResult testResult) {
        WebDriver driver = getDriver();
        if (ITestResult.FAILURE == testResult.getStatus()) {
            ScreenShotUtility.captureScreenshot(driver, testResult.getName());
        }
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
        homePageHolder.remove();
    }
}