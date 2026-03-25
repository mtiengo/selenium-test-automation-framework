package com.mtiengo.pages.elements;

import com.mtiengo.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Elements section
 */
public class ElementsPage extends HomePage {

    private By webTablesMenuItem = By.cssSelector("a[href='/webtables']");
    private By linksMenuItem = By.cssSelector("a[href='/links']");
    private By dynamicPropertiesMenuItem = By.xpath
            ("//li[@id='item-8']/span[text()='Dynamic Properties']"); // For future implementation
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
