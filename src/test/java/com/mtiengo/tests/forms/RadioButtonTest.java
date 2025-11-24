package com.mtiengo.tests.forms;

import com.mtiengo.tests.base.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RadioButtonTest extends BaseTest {

    @Test
    public void testRadioButton() {
        var formsPage = homePage.goToForms().clickPracticeForm();
        formsPage.clickFemaleRadioButton();
        formsPage.clickMaleRadioButton();
        formsPage.clickOtherRadioButton();
        formsPage.clickFemaleRadioButton();

        SoftAssert softAssert = new SoftAssert();

        boolean isFemaleRadioButtonSelected = formsPage.isFemaleSelected();
        boolean isMaleRadioButtonSelected = formsPage.isMaleSelected();
        boolean isOtherRadioButtonSelected = formsPage.isOtherSelected();

        softAssert.assertTrue(isFemaleRadioButtonSelected,
                "\n Female radio button is not selected \n");
        softAssert.assertFalse(isMaleRadioButtonSelected,
                "\n Male radio button is selected but shouldn't be \n");
        softAssert.assertFalse(isOtherRadioButtonSelected,
                "\n Other radio button is selected but shouldn't be \n");

        softAssert.assertAll();
    }
}
