package com.mtiengo.tests.forms;

import com.mtiengo.exceptions.ModalCloseException;
import com.mtiengo.pages.forms.PracticeFormPage;
import com.mtiengo.tests.base.BaseTest;
import com.mtiengo.utilities.ModalUtility;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PracticeFormTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(PracticeFormTest.class);
    private static final By CLOSE_BUTTON = By.id("closeLargeModal");

    @Test(groups = {"smoke"})
    public void testE2EPracticeForm() {
        var practiceFormPage = getHomePage().goToForms().clickPracticeForm();

        // Basic Fields
        practiceFormPage.enterFirstName("Johnny");
        practiceFormPage.enterLastName("Test");
        practiceFormPage.enterEmail("johnny@test.com");
        practiceFormPage.enterMobileNumber("1234567890");
        practiceFormPage.enterCurrentAddress("Selenium Ave, 1024");

        // Date of Birth
        practiceFormPage.enableCalendar();
        practiceFormPage.setYearOfBirthDropDown("1991");
        practiceFormPage.setMonthOfBirthDropDown("September");
        practiceFormPage.setDayValueFromCalendar("16");

        // Radio Buttons
        practiceFormPage.clickMaleRadioButton();

        // Hobby Checkboxes
        practiceFormPage.clickHobbyCheckbox(PracticeFormPage.Hobby.SPORTS);
        practiceFormPage.clickHobbyCheckbox(PracticeFormPage.Hobby.READING);
        practiceFormPage.clickHobbyCheckbox(PracticeFormPage.Hobby.MUSIC);

        // Subjects (autocomplete)
        practiceFormPage.enterSubject("Maths");
        practiceFormPage.enterSubject("Computer Science");

        // State and City Selection
        practiceFormPage.selectStateAndCity(PracticeFormPage.StateCity.RAJASTHAN, "Jaipur");

        // Upload Picture
        String picturePath = System.getProperty("user.dir") +
                "/src/test/resources/test-image.jpg";
        practiceFormPage.uploadPicture(picturePath);

        // Submit Button
        practiceFormPage.clickSubmitButton();

        // ======== Modal Validations ========
        SoftAssert softAssert = new SoftAssert();

        // Check modal appeared
        softAssert.assertTrue(practiceFormPage.isSubmissionModalDisplayed(),
                "\n Modal should be displayed after form submission \n");

        // Validate modal title
        String modalTitle = practiceFormPage.getModalTitleText();
        softAssert.assertEquals(modalTitle, "Thanks for submitting the form",
                "\n Modal title should be: \n 'Thanks for submitting the form'.\n");

        // Validate all data in modal
        softAssert.assertEquals(practiceFormPage.getModalValue("Student Name"),
                "Johnny Test",
                "\n Student Name mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Student Email"),
                "johnny@test.com",
                "\n Student Email mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Gender"),
                "Male",
                "\n Gender mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Mobile"),
                "1234567890",
                "\n Mobile number mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Date of Birth"),
                "16 September,1991",
                "\n Date of Birth mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Subjects"),
                "Maths, Computer Science",
                "\n Subjects mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Hobbies"),
                "Sports, Reading, Music",
                "\n Hobbies mismatch \n");
        softAssert.assertTrue(practiceFormPage.getModalValue("Picture").
                        contains("test-image.jpg"),
                "\n Picture filename mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("Address"),
                "Selenium Ave, 1024",
                "\n Address mismatch \n");
        softAssert.assertEquals(practiceFormPage.getModalValue("State and City"),
                "Rajasthan Jaipur",
                "\n State and City mismatch \n");


        // Close modal — try the real close button first (real UX path); fall back
        // to backdrop click only if it fails. See issue #1 for the SUT bug context.
        closeModalWithFallback(practiceFormPage);

        // Verify modal closed
        softAssert.assertTrue(practiceFormPage.isModalClosed(),
                "\n Modal should disappear after closing. \n");


        softAssert.assertAll();
    }

    private void closeModalWithFallback(PracticeFormPage practiceFormPage) {
        ModalUtility.close(getDriver(), CLOSE_BUTTON);

        if (practiceFormPage.isModalClosed()) {
            log.info("Modal closed via close button (happy path)");
            return;
        }

        log.warn("Close button did not close modal - falling back to backdrop click. " +
                "Known issue: https://github.com/mtiengo/selenium-test-automation-framework/issues/1");
        practiceFormPage.closeSubmissionModal();

        if (!practiceFormPage.isModalClosed()) {
            throw new ModalCloseException(
                    "Modal failed to close via both close button and backdrop");
        }
    }
}
