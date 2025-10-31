package com.mtiengo.tests.elements;

import com.mtiengo.pages.HomePage;
import com.mtiengo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinksTest extends BaseTest {

    @Test
    public void testLinks() {
        var linksPage = homePage.goToElements().clickLinks();
        linksPage.clickBadRequestLink();
        String actualResponse = linksPage.getResponse();
        Assert.assertTrue(actualResponse.contains("400"),
                "\n Actual Response: " + actualResponse +
                        "\n Does NOT contain expected status code: '400' \n");

        Assert.assertTrue(actualResponse.contains("Bad Request"),
                "\n Actual Response: " + actualResponse +
                        "\n Does NOT contain expected message: 'Bad Request' \n");
    }
}
