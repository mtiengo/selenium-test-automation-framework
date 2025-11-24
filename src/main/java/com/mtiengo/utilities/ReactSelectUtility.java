package com.mtiengo.utilities;

import com.mtiengo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for interacting with React Select dropdowns.
 * React Select dropdowns are not standard HTML <select> elements,
 * so they require special handling with clicks and XPath.
 */

public class ReactSelectUtility {

    public static void selectByText(WebDriver driver, By dropdownLocator, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Click to open dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        // Wait and click the option
        By optionLocator = By.xpath("//div[contains(@id, 'react-select') and text()='" + optionText + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public static String getSelectedOption(WebDriver driver, By dropDownLocator) {
        try {
            WebElement dropdown = driver.findElement(dropDownLocator);
            WebElement selectedValue = dropdown.findElement(By.cssSelector("[class*='singleValue']"));
            return selectedValue.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
