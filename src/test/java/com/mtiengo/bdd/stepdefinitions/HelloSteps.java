package com.mtiengo.bdd.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class HelloSteps {

    @Given("the framework is configured")
    public void theFrameworkIsConfigured() {
        System.out.println("Framework setup ok");
    }

    @When("Cucumber runs this scenario")
    public void cucumberRunsThisScenario() {
        System.out.println("Cucumber running");
    }

    @Then("it should pass without errors")
    public void itShouldPassWithoutErrors() {
        System.out.println("Scenario passed");
    }
}
