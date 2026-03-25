package com.mtiengo.pages;

import com.mtiengo.base.BasePage;
import com.mtiengo.pages.elements.ElementsPage;
import com.mtiengo.pages.forms.FormsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // Locators
    private final By formsCard = By.cssSelector("a[href='/forms']");
    private final By elementsCard = By.cssSelector("a[href='/elements']");
    private final By widgetsCard = By.cssSelector("a[href='/widgets']"); // For future implementation
    private final By alertsFramesWindowsCard = By.cssSelector("a[href='/alertsWindows']"); // For future implementation


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ElementsPage goToElements() {
        scrollToElement(elementsCard);
        click(elementsCard);
        return new ElementsPage(driver);
    }

    public FormsPage goToForms() {
        scrollToElement(formsCard);
        click(formsCard);
        return new FormsPage(driver);
    }
}
