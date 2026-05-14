package com.mtiengo.bdd.stepdefinitions;

import com.mtiengo.bdd.hooks.CucumberHooks;
import com.mtiengo.pages.elements.WebTablesPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebTablesSteps {

    private WebTablesPage webTablesPage;

    @Given("I am on the Web Tables page")
    public void iAmOnTheWebTablesPage() {
        webTablesPage = CucumberHooks.getHomePage()
                .goToElements()
                .clickWebTables();
    }

    @Given("a record exists with the following details:")
    public void aRecordExistsWith(DataTable table) {
        Map<String, String> row = table.asMaps().getFirst();
        webTablesPage.clickAddButton();
        webTablesPage.fillRegistrationForm(
                row.get("firstName"),
                row.get("lastName"),
                row.get("email"),
                row.get("age"),
                row.get("salary"),
                row.get("department")
        );
        webTablesPage.clickSubmitButton();

        // Safety net: precondition validates that setup actually worked
        assertTrue(
                webTablesPage.isRecordPresentByEmail(row.get("email")),
                "Failed to set up precondition: record was not created."
        );
    }

    @When("I add a new record with the following details:")
    public void iAddANewRecord(DataTable table) {
        Map<String, String> row = table.asMaps().getFirst();
        webTablesPage.clickAddButton();
        webTablesPage.fillRegistrationForm(
                row.get("firstName"),
                row.get("lastName"),
                row.get("email"),
                row.get("age"),
                row.get("salary"),
                row.get("department")
        );
        webTablesPage.clickSubmitButton();
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        webTablesPage.searchFor(searchTerm);
    }

    @When("I delete the record for {string}")
    public void iDeleteTheRecordFor(String email) {
        webTablesPage.clickDelete(email);
    }

    @Then("a record for {string} should exist in the table")
    public void aRecordShouldExist(String email) {
        assertTrue(
                webTablesPage.isRecordPresentByEmail(email),
                "Expected record with email " + email + " in the table, but it was not found."
        );
    }

    @Then("a record for {string} should not exist in the table")
    public void aRecordShouldNotExist(String email) {
        assertFalse(
                webTablesPage.isRecordPresentByEmail(email),
                "Expected record with email " + email + " to have been removed."
        );
    }

    @Then("the table should show {int} result(s)")
    public void theTableShouldShowResults(int expectedCount) {
        assertEquals(webTablesPage.getVisibleRowCount(), expectedCount);
    }

    @And("a visible row should contain {string}")
    public void aVisibleRowShouldContain(String text) {
        assertTrue(
                webTablesPage.anyVisibleRowContainsText(text),
                "No visible row contains the text: " + text
        );
    }
}