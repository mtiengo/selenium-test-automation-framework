package com.mtiengo.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Web Tables subsection
 */
public class WebTablesPage extends ElementsPage {

    // Locators
    private final By registrationAgeField = By.id("age");
    private final By submitButton = By.id("submit");

    public WebTablesPage(WebDriver driver) {
        super(driver);
    }

    public void clickEdit(String email) {
        By edit = By.xpath("//div[text()='"+ email +"']//following::span[@title='Edit']");
        click(edit);
    }

    public void setAge(String age) {
        clearAndSendKeys(registrationAgeField, age);
    }

    public void clickSubmitButton() {
        click(submitButton);
    }

    public String getTableAge(String email) {
        By tableAge = By.xpath("//div[text()='"+ email +"']//preceding::div[1]");
        return find(tableAge).getText();
    }
}