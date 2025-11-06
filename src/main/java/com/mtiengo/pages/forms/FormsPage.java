package com.mtiengo.pages.forms;

import com.mtiengo.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormsPage extends HomePage {

    private final By practiceFormMenuItem = By.xpath("//li[@id='item-0']/span[text()='Practice Form']");

    public FormsPage(WebDriver driver) {
        super(driver);
    }

    public PracticeFormPage clickPracticeForm() {
        scrollToElement(practiceFormMenuItem);
        click(practiceFormMenuItem);
        return new PracticeFormPage(driver);
    }
}
