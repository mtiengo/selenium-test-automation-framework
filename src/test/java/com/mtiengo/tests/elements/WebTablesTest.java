package com.mtiengo.tests.elements;

import com.mtiengo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebTablesTest extends BaseTest {

    @Test
    public void testWebTable() {
        String email = "kierra@example.com";
        String expectedAge = "34";
        var webTablePage = homePage.goToElements().clickWebTables();
        webTablePage.clickEdit(email);
        webTablePage.setAge(expectedAge);
        webTablePage.clickSubmitButton();
        String actualAge = webTablePage.getTableAge(email);
        Assert.assertEquals(actualAge, expectedAge, "\n Actual and Expected Ages Do Not Match \n");
    }
}
