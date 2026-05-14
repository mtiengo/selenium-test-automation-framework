package com.mtiengo.utilities;

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

    /**
     * Selects an option from a React Select dropdown by visible text.
     *
     * <p>React Select binds to {@code mousedown} and {@code focus} events on its input,
     * not to the standard {@code click} event. Synthetic clicks dispatched via
     * {@code JavascriptExecutor} ({@code arguments[0].click()}) bypass these listeners
     * and fail to open the dropdown menu. Native {@link WebElement#click()} dispatches
     * the full event sequence (mousedown, mouseup, click) and is required for both
     * the input and the option.
     *
     * @param driver       WebDriver instance
     * @param inputLocator locator for the React Select input element
     * @param optionText   visible text of the option to select
     */
    public static void selectByText(WebDriver driver, By inputLocator, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        JavaScriptUtility.removeAdOverlays(driver);
        JavaScriptUtility.scrollToElement(driver, inputLocator);
        wait.until(ExpectedConditions.elementToBeClickable(inputLocator)).click();

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
