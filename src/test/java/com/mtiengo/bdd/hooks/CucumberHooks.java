package com.mtiengo.bdd.hooks;

import com.mtiengo.pages.HomePage;
import com.mtiengo.utilities.CreateDriverUtility;
import com.mtiengo.utilities.DriverManager;
import com.mtiengo.utilities.ScreenShotUtility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mtiengo.utilities.CreateDriverUtility.createDriver;

/**
 * Cucumber lifecycle hooks mirroring the behavior of BaseTest for TestNG.
 * Each scenario gets its own thread-local WebDriver and HomePage instance,
 * enabling parallel execution without shared state.
 */
public class CucumberHooks {

    private static final Logger log = LoggerFactory.getLogger(CucumberHooks.class);
    private static final String DEMOQA_URL = "https://demoqa.com/";
    private static final ThreadLocal<HomePage> homePageHolder = new ThreadLocal<>();

    @Before(order = 0)
    public void logStart(Scenario scenario) {
        log.info("Starting: {}", scenario.getName());
    }

    @Before(order = 1)
    public void setUpDriver() {
        String browser = System.getProperty("browser", "chrome");
        WebDriver driver = createDriver(CreateDriverUtility.Browser.fromString(browser));
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.get(DEMOQA_URL);
        homePageHolder.set(new HomePage(driver));
    }

    /**
     * Exposes the thread-local HomePage so step definitions can start
     * their navigation chain (e.g., getHomePage().goToElements().clickWebTables()).
     */
    public static HomePage getHomePage() {
        return homePageHolder.get();
    }

    @After(order = 1)
    public void tearDownDriver(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure-" + scenario.getName());
            ScreenShotUtility.captureScreenshot(driver, scenario.getName());
        }
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
        homePageHolder.remove();
    }

    @After(order = 0)
    public void logEnd(Scenario scenario) {
        String status = scenario.isFailed() ? "FAIL" : "PASS";
        log.info("{}: {}", status, scenario.getName());
    }
}