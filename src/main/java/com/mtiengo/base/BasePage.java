package com.mtiengo.base;

import com.mtiengo.utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    // Use for hidden elements in the DOM such as checkbox inputs and radio buttons
    public WebElement findPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        assert element != null;
        element.click();
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement element = find(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
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

    protected void selectReactOption(By dropDownLocator, String optionText) {
        ReactSelectUtility.selectByText(driver, dropDownLocator, optionText);
    }

    protected String getReactSelectedOption(By dropDownLocator) {
        return ReactSelectUtility.getSelectedOption(driver, dropDownLocator);
    }

    // ========== ModalUtility Facades ==========

    protected boolean isModalDisplayed(By modalLocator) {
        return ModalUtility.isModalDisplayed(driver, modalLocator);
    }

    protected String getModalTitle(By titleLocator) {
        return ModalUtility.getTitle(driver, titleLocator);
    }

    protected String getModalValueByLabel(By tableLocator, String label) {
        return ModalUtility.getValueByLabel(driver, tableLocator, label);
    }

    protected void closeModal(By closeButtonLocator) {
        ModalUtility.close(driver, closeButtonLocator);
    }

    protected boolean waitForModalToDisappear(By modalLocator) {
        return ModalUtility.waitForDisappear(driver, modalLocator);
    }
}

