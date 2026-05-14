package com.mtiengo.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for JavaScript execution via JavascriptExecutor
 */
public class JavaScriptUtility {

    public static void scrollToElement(WebDriver driver, By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'}); window.scrollBy(0, -100);",
                element);
    }

    public static void clickJS(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Removes DemoQA ad overlay elements from the DOM.
     *
     * <p>DemoQA serves floating ad iframes and a fixed banner ({@code #fixedban}) that
     * intermittently cover form elements in CI viewports. These overlays intercept native
     * clicks, which cannot be avoided for components like React Select that bind to
     * {@code mousedown}/{@code focus} rather than {@code click}. Removing the elements
     * is safe in a test context — DemoQA uses iframes exclusively for ads, not for
     * functional content.
     *
     * @param driver WebDriver instance
     */
    public static void removeAdOverlays(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('iframe, #fixedban, [id^=\"google_ads\"], " +
                "[id*=\"adplus\"], [class*=\"ad-\"]').forEach(el => el.remove());"
        );
    }
}
