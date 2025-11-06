package com.mtiengo.tests.elements;

import com.mtiengo.pages.elements.WebTablesPage;
import com.mtiengo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WebTablesTest extends BaseTest {

    private WebTablesPage webTablePage;

    @BeforeMethod
    public void navigateToWebTables() {
        webTablePage = homePage.goToElements().clickWebTables();
    }

    /**
     * Test 1: Edit a record from the table
     */
    @Test
    public void testEditAgeWebTable() {
        String email = "kierra@example.com";
        String expectedAge = "34";

        webTablePage.clickEdit(email);
        webTablePage.setAge(expectedAge);
        webTablePage.clickSubmitButton();

        String actualAge = webTablePage.getTableAge(email);
        Assert.assertEquals(actualAge, expectedAge,
                "\n Expected age: '" + expectedAge + "', but got: '" + actualAge + "' \n");
    }

    /**
     * Test 2: Add a new record and check it afterwards
     */
    @Test
    public void testAddNewRecord() {
        // Test data
        String firstName = "Peter";
        String lastName = "Everyman";
        String email = "everyman@test.com";
        String age = "30";
        String salary = "5000";
        String department = "QA";

        webTablePage.clickAddButton();
        webTablePage.fillRegistrationForm(firstName, lastName, email, age, salary, department);
        webTablePage.clickSubmitButton();

        // Verify if record was added
        Assert.assertTrue(webTablePage.isRecordPresentByEmail(email),
                "\n Record with the email '" + email + "' was not found in the table \n");

        // Verify specific fields with clear expected vs actual
        String actualFirstName = webTablePage.getFieldValueByEmail(email, 0);
        Assert.assertEquals(actualFirstName, firstName,
                "\n Expected firstName: '" + firstName + "', but got: '" + actualFirstName + "' \n");

        String actualAge = webTablePage.getFieldValueByEmail(email, 2);
        Assert.assertEquals(actualAge, age,
                "\n Expected age: '" + age + "', but got: '" + actualAge + "' \n");
    }

    /**
     * Test 3: Delete a record from the table
     */
    @Test
    public void testDeleteRecord() {
        String emailToDelete = "cierra@example.com";

        // Check if record exists before attempting deletion
        Assert.assertTrue(webTablePage.isRecordPresentByEmail(emailToDelete),
                "\n Record with email '" + emailToDelete + "' was not found before deletion \n");

        webTablePage.clickDelete(emailToDelete);

        Assert.assertFalse(webTablePage.isRecordPresentByEmail(emailToDelete),
                "\n Record with email '" + emailToDelete + "' still exists after deletion \n");
    }

    /**
     * Test 4: Search functionality in the table
     */
    @Test
    public void testSearchInTable() {
        String searchTerm = "Alden";

        // Get initial row count
        int initialRowCount = webTablePage.getVisibleRowCount();
        Assert.assertTrue(initialRowCount > 0,
                "\n Table should have records initially \n");

        webTablePage.searchFor(searchTerm);

        // Verify filtered results
        int filteredRowCount = webTablePage.getVisibleRowCount();
        Assert.assertTrue(filteredRowCount < initialRowCount,
                "\n Search should filter results (expected < " + initialRowCount + ", got " + filteredRowCount + ") \n");
        Assert.assertTrue(filteredRowCount > 0,
                "\n Search should return at least one result \n");

        // Verify at least one visible row contains the search term
        Assert.assertTrue(webTablePage.anyVisibleRowContainsText(searchTerm),
                "\n At least one visible row should contain the search term '" + searchTerm + "' \n");
    }

    /**
     * Test 5: Searching with non-existent results
     */
    @Test
    public void testSearchWithNoResult() {
        String searchTerm = "NonExistentPerson";

        webTablePage.searchFor(searchTerm);

        Assert.assertTrue(webTablePage.isTableEmpty(),
                "\n Table should be empty when searching for non-existent term \n");
    }

    /**
     * Test 6: Trying to enter invalid email format
     * Validates that the form rejects invalid emails
     */
    @Test
    public void testAddRecordWithInvalidEmail() {
        String firstName = "Invalid";
        String lastName = "Email";
        String invalidEmail = "notreallyan.email"; // Missing @ symbol
        String age = "25";
        String salary = "100000";
        String department = "Automation";

        webTablePage.clickAddButton();
        webTablePage.fillRegistrationForm(firstName, lastName, invalidEmail, age, salary, department);
        webTablePage.clickSubmitButton();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(webTablePage.isRegistrationFormOpen(),
                "\n Form should remain open when email is invalid \n");

        // The demo website does not show visual validation errors on the email field
        // Uncomment below if testing a site that does:

        // softAssert.assertTrue(webTablePage.emailFieldHasValidationError(),
        //        "\n Email field should show validation error \n");

        softAssert.assertFalse(webTablePage.isRecordPresentByEmail(invalidEmail),
                "\n Record with invalid email should NOT be added \n");

        softAssert.assertAll();
    }

    /**
     * Test 7: Trying to submit with missing required fields
     */
    @Test
    public void testAddRecordWithMissingFields() {
        String firstName = "Incomplete";
        // Leaving other required fields intentionally blank

        webTablePage.clickAddButton();
        webTablePage.fillRegistrationForm(firstName, "", "", "", "", "");
        webTablePage.clickSubmitButton();

        // Assert form is still open (submission failed due to validation)
        Assert.assertTrue(webTablePage.isRegistrationFormOpen(),
                "\n Registration form should remain open when required fields are missing \n");
    }

    /**
     * Test 8: Trying to add record with invalid age
     * Validates that age field only accepts valid numeric values
     */
    @Test
    public void testAddRecordWithInvalidAge() {
        String firstName = "Invalid";
        String lastName = "Ageman";
        String email = "invalid.age@test.com";
        String invalidAge = "AZ"; // Non-numeric age (could also test negative numbers)
        String salary = "20000";
        String department = "Marketing";

        webTablePage.clickAddButton();
        webTablePage.fillRegistrationForm(firstName, lastName, email, invalidAge, salary, department);
        webTablePage.clickSubmitButton();

        // Form should either stay open OR record should not be added
        Assert.assertTrue(webTablePage.isRegistrationFormOpen() ||
                        !webTablePage.isRecordPresentByEmail(email),
                "\n Form should stay open OR record should not be added when age is invalid \n");
    }
}