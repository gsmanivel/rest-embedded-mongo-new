Feature: Get User details
  Background:
    * def requestPayload =
            """
            {
    "userName":"john"
}
            """

  Scenario: GET API Call
    Given url 'http://localhost:'+ port
    And path '/api/users'
    When method GET
    Then status 200
    * print response


  Scenario: POST API Call
    Given url 'http://localhost:'+ port
    And path '/api/users'
    And request requestPayload
    When method POST
    Then status 200