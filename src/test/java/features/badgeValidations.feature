Feature: Validate badge API

@Happy
Scenario Outline: Verify badge data is consistent for a specific badge id
    Given StackOverFlow Badge API with "<order>" "<sort>" "<pagesize>" "<min>"
    When User calls "getBadgesAPI" with "Get" http request
    Then the api call got success with status code 200
    And verify badges fetched count maps to <pagesize> using "getBadgesById"
    And verify recipents fetched with status code 200 using "getRecipentsById"
    
Examples:
	| order | sort  | pagesize | min             |
	| desc  | rank  | 4        | silver          |
	| asc   | rank  | 1        | bronze          |
	| desc  | type  | 100      | tag_based       |

@Negative		
Scenario Outline: Verify badge api negative scenario for status codes
    Given StackOverFlow Badge API with "<order>" "<sort>" "<pagesize>" "<min>"
    When User calls "getBadgesAPI" with "Get" http request
    Then "error_id" in response body is "<error_id>"
    And  "error_message" in response body is "<error_message>"
    And  "error_name" in response body is "<error_name>"

Examples:
	| order | sort  | pagesize | min             |  error_id | error_message | error_name    |
    | desc  | type  | 4        | bronze          |   400     | min           | bad_parameter |
   	| asc   | rank  | 101      | bronze          |   400     | pagesize      | bad_parameter |

@Negative  	
Scenario Outline: Verify badgeid api for negative scenario
    Given StackOverFlow Badge API
    When User calls "getBadgesAPI" with "Get" http request
    Then the api call got success with status code 200
    And verify badges fetched max size per page is <pagesize> using "getBadgesById"
 
Examples:
	| order | sort  | pagesize | min             |
	| desc  | rank  | 31       | silver          |