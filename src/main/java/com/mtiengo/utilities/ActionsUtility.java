package com.mtiengo.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for Actions-based interactions
 */
public class ActionsUtility {

    private static Actions act(WebDriver driver) {
        return new Actions(driver);
    }

    public static void dragAndDropBy(WebDriver driver, WebElement source, int x, int y) {
        act(driver).dragAndDropBy(source, x, y).perform();
    }

    public static void sendKeys(WebDriver driver, WebElement source, CharSequence keys) {
        act(driver).sendKeys(source, keys).perform();
    }

    public static void clearAndSendKeys(WebDriver driver, By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //noinspection ConstantConditions
        element.clear();
        element.sendKeys(text);
    }
}
