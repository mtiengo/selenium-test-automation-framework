package com.mtiengo.pages.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object representing the Web Tables subsection
 */
public class WebTablesPage extends ElementsPage {

    // ========== Locators ==========
    private final By addNewRecordButton = By.id("addNewRecordButton");
    private final By registrationFirstNameField = By.id("firstName");
    private final By registrationLastNameField = By.id("lastName");
    private final By registrationEmailField = By.id("userEmail");
    private final By registrationAgeField = By.id("age");
    private final By registrationSalaryField = By.id("salary");
    private final By registrationDepartmentField = By.id("department");
    private final By searchBox = By.id("searchBox");
    private final By submitButton = By.id("submit");
    private final By tableRows = By.cssSelector(".rt-tbody .rt-tr-group");

    public WebTablesPage(WebDriver driver) {
        super(driver);
    }

    // ========== Edit Record Methods ==========

    public void clickEdit(String email) {
        By edit = By.xpath("//div[text()='"+ email +"']//following::span[@title='Edit']");
        click(edit);
    }

    public void setAge(String age) {
        clearAndSendKeys(registrationAgeField, age);
    }

    public void clickSubmitButton() {
        click(submitButton);
    }

    public String getTableAge(String email) {
        By tableAge = By.xpath("//div[text()='"+ email +"']//preceding::div[1]");
        return find(tableAge).getText();
    }

    // ========== Add New Record Methods ==========

    public void clickAddButton() {
        click(addNewRecordButton);
    }

    public void fillRegistrationForm(String firstName, String lastName, String email,
                                     String age, String salary, String department) {
        clearAndSendKeys(registrationFirstNameField, firstName);
        clearAndSendKeys(registrationLastNameField, lastName);
        clearAndSendKeys(registrationEmailField, email);
        clearAndSendKeys(registrationAgeField, age);
        clearAndSendKeys(registrationSalaryField, salary);
        clearAndSendKeys(registrationDepartmentField, department);
    }

    public boolean isRecordPresentByEmail(String email) {
        By recordLocator = By.xpath("//div[@class='rt-tbody']//div[text()='" + email + "']");
        try {
            find(recordLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFieldValueByEmail(String email, int fieldIndex) {
        // XPath to find the row by email, then get the specific cell
        By fieldLocator = By.xpath(
                "//div[@class='rt-tbody']//div[text()='" + email + "']/ancestor::div[@class='rt-tr-group']//div[@class='rt-td'][" + (fieldIndex + 1) + "]"
        );
        return find(fieldLocator).getText();
    }

    // ========== Delete Record Methods ==========

    public void clickDelete(String email) {
        By deleteButton = By.xpath("//div[text()='"+ email +"']//following::span[@title='Delete']");
        click(deleteButton);
    }

    // ========== Search Methods ==========

    public void searchFor(String searchTerm) {
        clearAndSendKeys(searchBox, searchTerm);
    }

    public int getVisibleRowCount() {
        List<WebElement> rows = driver.findElements(tableRows);
        int count = 0;

        for (WebElement row : rows) {
            String rowText = row.getText().trim();
            if (!rowText.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public boolean isTableEmpty() {
        return getVisibleRowCount() == 0;
    }

    public boolean isRegistrationFormOpen() {
        By modalDialog = By.cssSelector(".modal-content");
        try {
            find(modalDialog);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasValidationError(By fieldLocator) {
        // Verifications should be changed according to the website being tested
        try {
            WebElement field = find(fieldLocator);
            String classValue = field.getAttribute("class");
            return classValue != null && (classValue.contains("invalid") || // Common terms for invalidation in form control
                    classValue.contains("error")) || // Common terms for invalidation in form control
                    field.getCssValue("border-color").contains("rgb(220, 53, 69"); // RGB for Bootstrap Red
        } catch (Exception e) {
            return false;
        }
    }

    // For future use
    public boolean emailFieldHasValidationError() {
        return hasValidationError(registrationEmailField);
    }

    // For future use
    public boolean ageFieldHasValidationError() {
        return hasValidationError(registrationAgeField);
    }

    public boolean anyVisibleRowContainsText(String text) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (row.getText().toLowerCase().contains(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}