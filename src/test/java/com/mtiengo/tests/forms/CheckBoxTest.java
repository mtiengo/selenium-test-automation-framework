package com.mtiengo.tests.forms;

import com.mtiengo.pages.forms.PracticeFormPage;
import com.mtiengo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CheckBoxTest extends BaseTest {

    @Test
    public void testCheckBox() {
        var formsPage = homePage.goToForms().clickPracticeForm();
        formsPage.clickHobbyCheckbox(PracticeFormPage.Hobby.SPORTS);
        formsPage.clickHobbyCheckbox(PracticeFormPage.Hobby.MUSIC);
        formsPage.clickHobbyCheckbox(PracticeFormPage.Hobby.READING);
        formsPage.unclickHobbyCheckbox(PracticeFormPage.Hobby.READING);

        SoftAssert softAssert = new SoftAssert();

        boolean isMusicSelected = formsPage.isHobbySelected(PracticeFormPage.Hobby.MUSIC);
        boolean isSportsSelected = formsPage.isHobbySelected(PracticeFormPage.Hobby.SPORTS);
        boolean isReadingSelected = formsPage.isHobbySelected(PracticeFormPage.Hobby.READING);

        softAssert.assertTrue(isMusicSelected,
                "\n Music checkbox is supposed to be selected but isn't \n");
        softAssert.assertTrue(isSportsSelected,
                "\n Sports checkbox is supposed to be selected but isn't \n");
        softAssert.assertFalse(isReadingSelected,
                "\n Reading checkbox is supposed to be deselected but isn't \n");

        softAssert.assertAll();
    }
}
