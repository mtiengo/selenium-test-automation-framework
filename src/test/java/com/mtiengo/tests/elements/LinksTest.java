package com.mtiengo.tests.elements;

import com.mtiengo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinksTest extends BaseTest {

    /**
     * Test 1: tests API call links that do not open new windows or tabs.
     * This test can be altered to test all API call links on the page.
     */
    @Test
    public void testLinksAPICall() {
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

    /**
     * Test 2: Tests links that open new tabs.
     */
    @Test
    public void testLinksNewTab() {
        var linksPage = homePage.goToElements().clickLinks();

        // Save original window handle before opening new tab.
        linksPage.clickHomeLink();

        String currentURL = linksPage.getCurrentUrl();
        Assert.assertTrue(currentURL.equals("https://demoqa.com/"), "\n Current URL: " + currentURL +
                "\n Does not match expected URL: 'https://demoqa.com/' \n");

        linksPage.switchBackToOriginalWindow();
        String returnURL = linksPage.getCurrentUrl();
        Assert.assertTrue(returnURL.equals("https://demoqa.com/links"), "\n Current URL: " + returnURL +
                "\n Did not return to original tab: 'https://demoqa.com/links' \n");
        System.out.println("Reverted to original page");
    }
}
