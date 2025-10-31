package com.mtiengo.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Links subsection
 */
public class LinksPage extends ElementsPage {

    // Locators
    private final By badRequestLink = By.id("bad-request");
    private final By responseLink = By.id("linkResponse");

    public LinksPage(WebDriver driver) {
        super(driver);
    }

    public void clickBadRequestLink() {
        scrollToElement(badRequestLink);
        click(badRequestLink);
    }

    public String getResponse() {
        return find(responseLink).getText();
    }
}
