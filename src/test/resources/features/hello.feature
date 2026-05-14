# language: en
Feature: Smoke test for Cucumber setup
  Sanity check for Cucumber wiring

  Scenario: Cucumber executes a basic scenario
    Given the framework is configured
    When Cucumber runs this scenario
    Then it should pass without errors