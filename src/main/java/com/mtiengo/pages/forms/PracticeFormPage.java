package com.mtiengo.pages.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PracticeFormPage extends FormsPage {

    // ========== Locators ==========

    // ========== Radio Button Locators ==========
    private final By maleRadioButton = By.id("gender-radio-1");
    private final By femaleRadioButton = By.id("gender-radio-2");
    private final By otherRadioButton = By.id("gender-radio-3");
    private final By submitButton = By.id("submit");

    // ========== Keys Field Input Locators ==========
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By mobileNumberField = By.id("userNumber");
    private final By subjectsField = By.id("subjectsInput");
    private final By currentAddressField = By.id("currentAddress");

    // ========== Calendar Locators ==========
    private final By dateOfBirthField = By.id("dateOfBirthInput");
    private final By monthOfBirthDropDown = By.className("react-datepicker__month-select");
    private final By yearOfBirthDropDown = By.className("react-datepicker__year-select");
    private By dayValueFromCalendar(String day) {
        return By.xpath("//div[contains(@class,'react-datepicker__day react-datepicker__day--')]" +
                "[text()='"+ day +"']");
    }

    // ========== Dropdown Locators ==========
    private final By stateDropdownSelect = By.xpath("//div[text()='Select State']");
    private final By cityDropdownSelect = By.xpath("//div[text()='Select City']");

    // ========== Upload Locators ==========
    private final By uploadPictureButton = By.id("uploadPicture");

    // ========== Modal Locators ==========
    private final By modal = By.className("modal-content");
    private final By modalTitle = By.cssSelector(".modal-title.h4");
    private final By modalTable = By.cssSelector(".table-responsive");
    private final By modalCloseButton = By.id("closeLargeModal");

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

    // ========== Click and Send Keys Methods for Practice Forms ==========
    public void enterFirstName(String firstName) {
        scrollToElement(firstNameField);
        clearAndSendKeys(firstNameField,firstName);
    }

    public void enterLastName(String lastName) {
        scrollToElement(lastNameField);
        clearAndSendKeys(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        scrollToElement(emailField);
        clearAndSendKeys(emailField, email);
    }

    public void enterMobileNumber(String mobileNumber) {
        scrollToElement(mobileNumberField);
        clearAndSendKeys(mobileNumberField, mobileNumber);
    }

    public void enterCurrentAddress(String currentAddress) {
        scrollToElement(currentAddressField);
        clearAndSendKeys(currentAddressField, currentAddress);
    }

    // ========== City and State Dropdown ==========
    /**
     * Enum representing valid State and City combinations.
     * Each state has a list of valid cities.
     */
    public enum StateCity {
        NCR("NCR", "Delhi", "Gurgaon", "Noida"),
        UTTAR_PRADESH("Uttar Pradesh", "Agra", "Lucknow", "Merrut"),
        HARYANA("Haryana", "Karnal", "Panipat"),
        RAJASTHAN("Rajasthan", "Jaipur", "Jaiselmer");

        private final String stateName;
        private final String[] cities;

        StateCity(String stateName, String... cities) {
            this.stateName = stateName;
            this.cities = cities;
        }

        public String getStateName() {
            return stateName;
        }

        public String[] getCities() {
            return cities;
        }

        public String getFirstCity() {
            return cities[0];
        }

        public String getRandomCity() {
            int randomIndex = (int) (Math.random() * cities.length);
            return cities[randomIndex];
        }
    }

    public void selectState(String stateName) {
        scrollToElement(stateDropdownSelect);
        selectReactOption(stateDropdownSelect, stateName);
    }

    public void selectCity(String cityName) {
        scrollToElement(cityDropdownSelect);
        selectReactOption(cityDropdownSelect, cityName);
    }

    public void selectStateAndCity(StateCity stateCity, String cityName) {
        selectState(stateCity.getStateName());
        selectCity(cityName);
    }

    // ========== Birthday Methods ==========

    public void enableCalendar() {
        scrollToElement(dateOfBirthField);
        click(dateOfBirthField);
    }

    public void setYearOfBirthDropDown(String yearOfBirth) {
        scrollToElement(yearOfBirthDropDown);
        selectByVisibleText(yearOfBirthDropDown, yearOfBirth);
    }

    public void setMonthOfBirthDropDown(String monthOfBirth) {
        scrollToElement(monthOfBirthDropDown);
        selectByVisibleText(monthOfBirthDropDown, monthOfBirth);
    }

    public void setDayValueFromCalendar(String day) {
        scrollToElement(dayValueFromCalendar(day));
        click(dayValueFromCalendar(day));
    }


    // ========== Upload Picture Methods ==========

    public void uploadPicture(String absoluteFilePath) {
        scrollToElement(uploadPictureButton);
        WebElement uploadInput = findPresence(uploadPictureButton);
        uploadInput.sendKeys(absoluteFilePath);
    }

    // ========== Subject Methods ==========

    public void enterSubject(String subject) {
        scrollToElement(subjectsField);
        WebElement subjectInput = find(subjectsField);
        subjectInput.click();
        subjectInput.sendKeys(subject);
        subjectInput.sendKeys(Keys.ENTER);
    }

    // ========== Modal Methods ==========

    public boolean isSubmissionModalDisplayed() {
        return isModalDisplayed(modal);
    }

    public String getModalTitleText() {
        return getModalTitle(modalTitle);
    }

    public String getModalValue(String label) {
        return getModalValueByLabel(modalTable, label);
    }

    public void closeSubmissionModal() {
        closeModal(modalCloseButton);
    }

    public boolean isModalClosed() {
        return waitForModalToDisappear(modal);
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
