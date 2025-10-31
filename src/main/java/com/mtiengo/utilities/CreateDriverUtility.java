package com.mtiengo.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateDriverUtility {
    public enum Browser {
        CHROME,
        FIREFOX,
        EDGE
    }

    public static WebDriver createDriver(Browser browser) {
        WebDriver driver = switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver();
            }
            default -> throw new IllegalArgumentException("Browser not supported: " + browser);
        };

        System.out.println("Running tests on: " + browser + " browser.");
        return driver;
    }
}
