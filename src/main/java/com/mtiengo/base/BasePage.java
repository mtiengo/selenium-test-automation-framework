package com.mtiengo.base;

import com.mtiengo.utilities.ActionsUtility;
import com.mtiengo.utilities.JavaScriptUtility;
import com.mtiengo.utilities.SwitchToUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Object Model classes.
 * Provides reusable methods and facade wrappers for utility classes.
 * All page objects should extend this class to inherit driver and common functionality.
 */

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement findPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator) {
        find(locator).click();
    }


    /**
     * ==================== UTILITY WRAPPERS ====================
     * FACADE Pattern for simplified access to utilities
     */

    // ========== JavaScriptUtility Facades ==========

    protected void scrollToElement(By locator) {
        JavaScriptUtility.scrollToElement(driver, locator);
    }

    protected void clickJS(By locator) {
        JavaScriptUtility.clickJS(driver, locator);
    }

    // ========== ActionsUtility Facades ==========

    protected void clearAndSendKeys(By locator, String text) {
        ActionsUtility.clearAndSendKeys(driver, locator, text);
    }

    // ========== SwitchToUtility Facades ==========
    protected String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    protected void switchToNewlyOpenedWindow(String originalHandle) {
        SwitchToUtility.switchToNewlyOpenedWindow(driver, originalHandle);
    }

    protected void switchToWindow(String handle) {
        SwitchToUtility.switchToWindow(driver, handle);
    }

    protected void switchBackToOriginalWindow(String originalHandle) {
        SwitchToUtility.switchBackToOriginalWindow(driver, originalHandle);
    }

    protected void switchToFrameByIndex(int index) {
        SwitchToUtility.switchToFrameByIndex(driver, index);
    }

    protected void switchToFrameByNameOrId(String nameOrID) {
        SwitchToUtility.switchToFrameByNameOrID(driver, nameOrID);
    }

    protected void switchToDefaultContent() {
        SwitchToUtility.switchToDefaultContent(driver);
    }

    protected void switchToParentFrame() {
        SwitchToUtility.switchToParentFrame(driver);
    }
}

