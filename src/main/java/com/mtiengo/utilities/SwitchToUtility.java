package com.mtiengo.utilities;

import org.openqa.selenium.WebDriver;

import java.util.Set;


/**
 * Utility class for switching between windows, tabs, frames and alerts.
 */
public class SwitchToUtility {

    /**
     * Helper method to reduce driver.switchTo() calls and improve readability
     */
    private static WebDriver.TargetLocator switchTo(WebDriver driver) {
        return driver.switchTo();
    }

    /**
     * Switches to a specific window or tab by handle.
     * @param driver
     * @param handle
     */
    public static void switchToWindow(WebDriver driver, String handle) {
        switchTo(driver).window(handle);
    }

    public static void switchToNewlyOpenedWindow(WebDriver driver, String originalHandle) {
        Set<String> allHandles = driver.getWindowHandles();
        // In Selenium, windows and tabs are both treated as window handles
        System.out.println("Number of open windows/tabs: " + allHandles.size());

        for (String handle : allHandles) {
            if (!handle.equals(originalHandle)) {
                switchToWindow(driver, handle);
                System.out.println("Switched to a new window/tab with handle: " + handle);
                return;
            }
        }
    }

    public static void switchBackToOriginalWindow(WebDriver driver, String originalHandle) {
        switchToWindow(driver, originalHandle);
        System.out.println("Switched back to original window/tab with handle: " + originalHandle);
    }

    public static void switchToFrameByIndex(WebDriver driver, int index) {
        switchTo(driver).frame(index);
    }

    public static void switchToFrameByNameOrID(WebDriver driver, String nameOrID) {
        switchTo(driver).frame(nameOrID);
    }

    public static void switchToDefaultContent(WebDriver driver) {
        switchTo(driver).defaultContent();
    }

    public static void switchToParentFrame(WebDriver driver) {
        switchTo(driver).parentFrame();
    }
}
