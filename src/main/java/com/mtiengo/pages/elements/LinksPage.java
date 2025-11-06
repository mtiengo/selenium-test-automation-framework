package com.mtiengo.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Links subsection
 */
public class LinksPage extends ElementsPage {

    // ========== Locators - API Call Links ==========
    private final By badRequestLink = By.id("bad-request");
    private final By responseLink = By.id("linkResponse");

    // ========== Locators - New Tab Links ==========
    private final By homeLink = By.id("simpleLink");
    private final By homeDynamicLink = By.id("dynamicLink");

    // Store original window handle
    private String originalWindowHandle;

    public LinksPage(WebDriver driver) {
        super(driver);
    }

    // ========== API Call Link Methods ==========

    public void clickBadRequestLink() {
        scrollToElement(badRequestLink);
        click(badRequestLink);
    }

    public String getResponse() {
        return find(responseLink).getText();
    }

    // ========== New Tab Link Methods ==========
    /**
     * Clicks the Home link which opens in a new tab.
     * Automatically saves original window handle and switches to new tab.
     */
    public void clickHomeLink() {
        // Save original window handle before clicking
        originalWindowHandle = getCurrentWindowHandle();
        System.out.println("Original window handle saved: " + originalWindowHandle);

        // Click link (opens new tab)
        click(homeLink);

        // Switch to new tab
        switchToNewlyOpenedWindow(originalWindowHandle);
        System.out.println("Switched to new tab");
    }

    /**
     * Clicks the Home Dynamic link which opens in a new tab.
     * Automatically saves original window handle and switches to new tab.
     */
    public void clickHomeDynamicLink() {
        originalWindowHandle = getCurrentWindowHandle();
        click(homeDynamicLink);
        switchToNewlyOpenedWindow(originalWindowHandle);
    }

    /**
     * Switches back to the original window/tab.
     */
    public void switchBackToOriginalWindow() {
        if (originalWindowHandle != null) {
            switchBackToOriginalWindow(originalWindowHandle);
            System.out.println("Switched back to original window");
        }
    }

    /**
     * Gets the current URL (useful for verifying new tab navigation).
     *
     * @return Current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
