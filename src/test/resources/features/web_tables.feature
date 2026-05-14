@web-tables
Feature: Web Tables management
  As a user of the DemoQA Web Tables widget
  I want to manage records in the table
  So that I can add, search and remove entries

  Background:
    Given I am on the Web Tables page

  @smoke
  Scenario: Add a new record to the table
    When I add a new record with the following details:
      | firstName | lastName | email                    | age | salary | department |
      | Matheus   | Tiengo   | matheus.tiengo@test.com  | 35  | 50000  | QA         |
    Then a record for "matheus.tiengo@test.com" should exist in the table

  Scenario: Search returns a matching record
    When I search for "Cierra"
    Then the table should show 1 result
    And a visible row should contain "Cierra Vega"

  Scenario: Delete a record that the test itself created
    Given a record exists with the following details:
      | firstName | lastName | email                    | age | salary | department |
      | Disposable| Record   | disposable@test.com      | 40  | 1000   | Temp       |
    When I delete the record for "disposable@test.com"
    Then a record for "disposable@test.com" should not exist in the table