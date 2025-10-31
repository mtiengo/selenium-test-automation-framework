package com.mtiengo.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Utility methods for retrieving values from WebDriver
 */
public class GetUtility {

    public static String getWindowHandle(WebDriver driver) {
        return driver.getWindowHandle();
    }

    public static Set<String> getWindowHandles(WebDriver driver) {
        return driver.getWindowHandles();
    }

    public static String getText(WebDriver driver, By locator) {
        return driver.findElement(locator).getText();
    }

    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        return driver.findElement(locator).getAttribute(attribute);
    }

    public static String getURL(WebDriver driver) {
        return driver.getCurrentUrl();
    }
}
