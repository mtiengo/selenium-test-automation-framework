package com.mtiengo.pages;

import com.mtiengo.base.BasePage;
import com.mtiengo.pages.elements.ElementsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // Locators
    private final By formsCard = By.xpath("//div[@id='app']//h5[text()='Forms']");
    private final By elementsCard = By.xpath("//div[@id='app']//h5[text()='Elements']");
    private final By widgetsCard = By.xpath("//div[@id='app']//h5[text()='Widgets']");
    private final By alertsFramesWindowsCard = By.xpath("//div[@id='app']//h5[contains(text(), 'Alerts')]");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ElementsPage goToElements() {
        scrollToElement(elementsCard);
        click(elementsCard);
        return new ElementsPage(driver);
    }
}
