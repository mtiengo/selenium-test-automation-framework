package com.mtiengo.bdd.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloSteps {

    private static final Logger log = LoggerFactory.getLogger(HelloSteps.class);

    @Given("the framework is configured")
    public void theFrameworkIsConfigured() {
        log.info("Framework setup ok");
    }

    @When("Cucumber runs this scenario")
    public void cucumberRunsThisScenario() {
        log.info("Cucumber running");
    }

    @Then("it should pass without errors")
    public void itShouldPassWithoutErrors() {
        log.info("Scenario passed");
    }
}
