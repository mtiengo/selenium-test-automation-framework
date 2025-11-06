package com.mtiengo.pages.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PracticeFormPage extends FormsPage {

    // ========== Specific Locators ==========
    private final By maleRadioButton = By.id("gender-radio-1");
    private final By femaleRadioButton = By.id("gender-radio-2");
    private final By otherRadioButton = By.id("gender-radio-3");
    private final By submitButton = By.id("submit");
    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By mobileNumber = By.id("userNumber");
    // For future implementation: private final By dateOfBirth = By.
    private final By subjectsField = By.xpath(
            "//div[@class='subjects-auto-complete__placeholder css-1wa3eu0-placeholder']");
    private final By currentAddress = By.id("currentAddress");
    private final By uploadPictureButton = By.id("uploadPicture");
    // For future implementation: private final By stateDropdownSelection = By.
    // For future implementation: private final By cityDropdownSelection = By.

    // ========== Public ENUM for checkboxes ==========
    // Better scaling and abstraction
    public enum Hobby {
        SPORTS("//input[@id='hobbies-checkbox-1']"),
        READING("//input[@id='hobbies-checkbox-2']"),
        MUSIC("//input[@id='hobbies-checkbox-3']");

        private final By locator;

        Hobby(String xpath) {
            this.locator = By.xpath(xpath);
        }

        public By getLocator() {
            return locator;
        }
    }


    public PracticeFormPage(WebDriver driver) {
        super(driver);
    }

    // ========== Radio Methods ==========
    // Since gender options are usually boiled to 3 choices
    // 3 separate methods works well here
    // Without the need to create a public ENUM
    // Or further abstractions
    public void clickMaleRadioButton() {
        scrollToElement(maleRadioButton);
        clickJS(maleRadioButton);
    }

    public void clickFemaleRadioButton() {
        scrollToElement(femaleRadioButton);
        clickJS(femaleRadioButton);
    }

    public void clickOtherRadioButton() {
        scrollToElement(otherRadioButton);
        clickJS(otherRadioButton);
    }

    public boolean isFemaleSelected() {
        return findPresence(femaleRadioButton).isSelected();
    }

    public boolean isMaleSelected() {
        return findPresence(maleRadioButton).isSelected();
    }

    public boolean isOtherSelected() {
        return findPresence(otherRadioButton).isSelected();
    }

    // ========== Submit Methods ==========
    public void clickSubmitButton() {
        scrollToElement(submitButton);
        click(submitButton);
    }

    // ========== CheckBox Methods ==========
    /**
     * Sets a checkbox state according to the expected value.
     *
     * @param locator           the checkbox locator
     * @param shouldBeChecked   true to ensure the checkbox is checked, false to ensure it is unchecked
     *
     * If the checkbox is already in the desired state, no action is taken.
     *
     * @implNote This method is private because it serves as internal logic
     * for checkbox interactions. Use public helpers instead for better readability
     * and future-proof logging.
     */
    private void setCheckbox(By locator, boolean shouldBeChecked) {
        WebElement checkbox = findPresence(locator);
        boolean isChecked = checkbox.isSelected();

        if (isChecked != shouldBeChecked) {
            scrollToElement(locator);
            clickJS(locator);
        }
    }

    // Helper methods to facilitate readability and better future logging
    public void clickHobbyCheckbox(Hobby hobby) {
        setCheckbox(hobby.getLocator(), true);
    }

    public void unclickHobbyCheckbox(Hobby hobby) {
        setCheckbox(hobby.getLocator(), false);
    }


    // ========== Test Boolean ==========
    public boolean isHobbySelected(Hobby hobby) {
        return findPresence(hobby.getLocator()).isSelected();
    }
}
