@id:place_holder @Navigation @ignore
Feature: Navigation Tests

  Background:
    Given I open the browser to tag24 website
    And I accept the cookies

    Scenario Outline: Simple Navigation test
    And I choose the location <Name>
    And I confirm the location

    Examples:
      | Name           |
      | Deutschland    |
      | Berlin         |
      | Chemnitz       |
      | Dresden        |
      | Erfurt         |
      | Frankfurt/Main |
      | Hamburg        |
      | Köln           |
      | Leipzig        |
      | Magdeburg      |
      | München        |
      | Stuttgart      |




