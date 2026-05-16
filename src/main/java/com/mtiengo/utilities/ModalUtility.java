package com.mtiengo.utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for interacting with modal dialogs.
 * Handles common modal operations like checking visibility,
 * extracting data, and closing.
 */
public class ModalUtility {

    private static final Logger log = LoggerFactory.getLogger(ModalUtility.class);


    /**
     * Checks if a modal is currently displayed on the page.
     *
     * @param driver WebDriver instance
     * @param modalLocator the locator for the modal element
     * @return true if modal is visible, false otherwise
     */
    public static boolean isModalDisplayed(WebDriver driver, By modalLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getTitle(WebDriver driver, By titleLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator));
            return title.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getValueByLabel(WebDriver driver, By tableLocator, String label) {
        try {
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 2) {
                    String cellLabel = cells.get(0).getText();
                    if (cellLabel.equalsIgnoreCase(label)) {
                        return cells.get(1).getText();
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static void close(WebDriver driver, By closeButtonLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
            JavaScriptUtility.clickJS(driver, closeButtonLocator);
        } catch (Exception e) {
            log.error("Failed to close modal through close button", e);
        }
    }

    /**
     * Dismisses the modal by dispatching a click on the backdrop element.
     *
     * <p>Dispatching a click event directly on the root {@code .modal} element
     * triggers Bootstrap's built-in backdrop-dismiss handler, which correctly
     * hides the modal.
     *
     * <p>Intended as a fallback path when {@link #close(WebDriver, By)} cannot
     * dismiss the dialog — see DemoQA issue #1, where the Practice Form
     * confirmation modal's native close button ({@code #closeLargeModal}) is
     * broken regardless of wait strategy. Verified manually; this is a defect
     * in the application under test, not a framework limitation.
     *
     * @param driver       WebDriver instance
     * @param modalLocator locator for the root {@code .modal} element (not the inner dialog)
     */
    public static void closeByBackdropClick(WebDriver driver, By modalLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true}));",
                    modal
            );
        } catch (Exception e) {
            log.error("Failed to close modal via backdrop click", e);
        }
    }

    public static boolean waitForDisappear(WebDriver driver, By modalLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
        } catch (Exception e) {
            return false;
        }
    }


}
