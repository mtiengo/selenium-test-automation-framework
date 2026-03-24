package com.mtiengo.utilities;

import com.mtiengo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public static void selectByText(WebDriver driver, By inputLocator, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Clicks input
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(inputLocator));
        input.click();

        // Confirms menu opened
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@id,'-option-0')]")));

        // Clicks option
        By optionLocator = By.xpath(
                "//div[contains(@id,'-option-') and normalize-space(.)='" + optionText + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator)).click();
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
