#Author: your.email@your.domain.com
@tag
Feature: Validating Place APIs
  

  @AddPlace @Regression
  Scenario Outline: Veriy id Place is Being Successfully Added using AddPlaceAPI
    Given Add place payload with "<name>""<language>""<address>"
    When user calls "AddPlaceAPI" with "POST" Http Request
    Then the Api call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify place ID created maps "<name>" using "GetPlaceAPI"

Examples: 
|name | language | address|
|culbHouse|kannada|vedavathi nagara|
#|Hospital| english|chitradurga|

	@DeletePlace @Regression
	Scenario: verify if delete functionality is working
	Given  DeletePlace payload
	When user calls "DeleteplaceAPI" with "POST" Http Request
  Then the Api call got success with status code 200
  And "status" in response body is "OK"
    
	 