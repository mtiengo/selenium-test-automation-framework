package com.mtiengo.pages.elements;

import com.mtiengo.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Elements section
 */
public class ElementsPage extends HomePage {

    private By webTablesMenuItem = By.xpath("//li[@id='item-3']/span[text()='Web Tables']");
    private By linksMenuItem = By.xpath("//li[@id='item-5']/span[text()='Links']");
    private By dynamicPropertiesMenuItem = By.xpath
            ("//li[@id='item-8']/span[text()='Dynamic Properties']");
    private By textBoxMenuItem = By.xpath("//li[@id='item-0']/span[text()='Text Box']");

    public ElementsPage(WebDriver driver) {
        super(driver);
    }


    public LinksPage clickLinks() {
        click(linksMenuItem);
        return new LinksPage(driver);
    }

    public WebTablesPage clickWebTables() {
        click(webTablesMenuItem);
        return new WebTablesPage(driver);
    }
}
