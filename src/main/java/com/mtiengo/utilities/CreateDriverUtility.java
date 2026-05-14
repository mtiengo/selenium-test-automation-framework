package com.mtiengo.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class CreateDriverUtility {
    private static final Dimension HEADLESS_WINDOW_SIZE = new Dimension(1920, 1080);

    public enum Browser {
        CHROME, FIREFOX, EDGE;

        public static Browser fromString(String value) {
            return Browser.valueOf(value.toUpperCase());
        }
    }

    public static WebDriver createDriver(Browser browser) {
        boolean headless = Boolean.getBoolean("headless");

        WebDriver driver = switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                }
                yield new ChromeDriver(options);
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                yield new FirefoxDriver(options);
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                }
                yield new EdgeDriver(options);
            }
        };

        if (headless) {
            // --window-size flag is unreliable in headless=new on some Chrome builds; set via API instead.
            driver.manage().window().setSize(HEADLESS_WINDOW_SIZE);
        } else {
            driver.manage().window().maximize();
        }

        System.out.println("Running tests on: " + browser + " browser" + (headless ? " (headless)" : "") + ".");
        System.out.println("Window size: " + driver.manage().window().getSize());
        return driver;
    }
}
