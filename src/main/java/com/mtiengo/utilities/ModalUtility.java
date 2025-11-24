package com.mtiengo.utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for interacting with modal dialogs.
 * Handles common modal operations like checking visibility,
 * extracting data, and closing.
 */
public class ModalUtility {


    /**
     * Checks if a modal is currently displayed on the page.
     *
     * @param driver WebDriver instance
     * @param modalLocator the locator for the modal element
     * @return true if modal is visible, false otherwise
     */
    public static boolean isModalDisplayed(WebDriver driver, By modalLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
            closeButton.click();
        } catch (Exception e) {
            System.err.println("\n Failed to close modal: " + e.getMessage() + "\n");
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
