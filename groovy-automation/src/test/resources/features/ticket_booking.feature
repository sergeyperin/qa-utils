
Feature: User can book direct and connection flights via https://www.anywayanyday.com.
  Additionally, there is possibility to hire the car, book hotel and trains.


  Scenario: Booking exists for trains, hotels and cars
    Given I go to the landing page
    Then eventually I am at the landing page

    And the departure city is present
    And the arrive in city is present

    And the hotels link is present
    And the train tickets link is present
    And the car hire link is present

  @stable
  Scenario: Booking is found for direct (no connection) flight
    Given I go to the landing page
    Then eventually I am at the landing page

    When I enter "Odessa" into "departure city"
    And I enter "Kiev" into "arrive in city"
    Then eventually the departure city field has value 'Odessa'
    And eventually the arrive in city field has value 'Kiev'
    When I select "2017" year, "8" month, "14" day
    And I click the search button

    Then eventually I am at the search result page
    And the direct flight is present


  Scenario: Booking is found with connection flight
    Given I go to the landing page
    Then eventually I am at the landing page

    When I enter "Odessa" into "departure city"
    And I enter "Oslo" into "arrive in city"
    Then eventually the departure city field has value 'Odessa'
    And eventually the arrive in city field has value 'Oslo'
    When I select "2017" year, "8" month, "14" day
    And I click the search button

    Then eventually I am at the search result page
    And the connection flight is present

  @wip
  Scenario: Booking is found with connection flight not more than 1 day