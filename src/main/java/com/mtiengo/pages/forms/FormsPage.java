package com.mtiengo.pages.forms;

import com.mtiengo.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormsPage extends HomePage {

    private final By practiceFormMenuItem = By.cssSelector("a[href='/automation-practice-form']");

    public FormsPage(WebDriver driver) {
        super(driver);
    }

    public PracticeFormPage clickPracticeForm() {
        click(practiceFormMenuItem);
        return new PracticeFormPage(driver);
    }
}
