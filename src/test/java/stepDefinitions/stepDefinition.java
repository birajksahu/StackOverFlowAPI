package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.GetBadges;
import resources.APIResources;
import resources.Utils;



/**
 * @author biraj
 * This class takes care of all Step Definitions needed for scenarios to 
 * be executed in Feature file. 
 */
public class stepDefinition extends Utils{
	RequestSpecification req;
	RequestSpecification req1;
	RequestSpecification reqspec;
	ResponseSpecification resspec;
	Response response;
	pojo.GetBadges gb;
	List<String> badge_names;
	Integer size;
	List<String> badge_ids;
	String delimitedid;
	Integer quota;
	APIResources resourceAPI;
	JsonPath js;
	
	/**
	 * Helps us create request specification for any Badge APIs with query parameters
	 * @param order
	 * @param sort
	 * @param pagesize
	 * @param min
	 * @throws IOException
	 */
	@Given("StackOverFlow Badge API with {string} {string} {string} {string}")
	public void stack_over_flow_badge_api_with(String order, String sort, String pagesize, String min) throws IOException {
		//creates request spec based on params passed
		reqspec= given().spec(requestSpecification(order, sort, pagesize, min));
	}
	
	/**
	 * creates request specification for badge APIs without any query parameters
	 * @throws IOException
	 */
	@Given("StackOverFlow Badge API")
	public void stack_over_flow_badge_api() throws IOException {
		//creates request spec without any params
		reqspec= given().spec(requestSpecification());
	}
	
	/**
	 * The response is build based on resource path param and the http method. 
	 * @param resource This is API's resource alias name.
	 *                 Accepts the following constants - getBadgesAPI,getRecipentAPI
	 *                 getRecipentsById, getBadgesById.
	 *                 These constants are mapped to an enum class to fetch us corr.
	 *                 resource path.
	 * @param method   This is the http method we are going to use on the API end point.
	 */
	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		resourceAPI = APIResources.valueOf(resource);
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		//here based on the resource alias, the response is built.
		if (resource.equalsIgnoreCase("getBadgesById")) {
			if (delimitedid.isEmpty()) {
			}
			else {
				//delimitedid is a string of ids separated by ";" This builds the response fpr getBadgesId
				String newResource = (resourceAPI.getResource()+delimitedid);
				response = reqspec.expect().defaultParser(Parser.JSON).when().get(newResource);
			}
		}
	    else if (resource.equalsIgnoreCase("getRecipentsById")) {
	    	if (delimitedid.isEmpty()) {
				}
	    	else {
	    		//response for getRecipentsById is built
	    		String newResource = (resourceAPI.getResource()+delimitedid+"/recipients");
				response = reqspec.expect().defaultParser(Parser.JSON).when().get(newResource);
	    	}
	    }
		else {
			response = reqspec.expect().defaultParser(Parser.JSON).when().get(resourceAPI.getResource());
		}
		js = new JsonPath(response.asString());	
	}
	/**
	 * Asserts if api call got success status code
	 * @param int1  any valid status code
	 */
	@Then("the api call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(200, response.getStatusCode());
	}
	
	/**
	 * Asserts a key from the response doesnt match an expected value
	 * @param column
	 * @param int1
	 */
	@Then("{string} in response body is not {int}")
	public void in_response_body_is_not(String column, Integer int1) {
		String errorid = js.getString(column);
	    assertEquals(errorid, String.valueOf(int1));
	}
	
	/**
	 * Asserts a key from the response match an expected value
	 * @param column
	 * @param int1
	 */
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String column, String int1) {
		String errorid = js.getString(column);
	    assertEquals(errorid, String.valueOf(int1));
	}
	
	/**
	 * Uses the list of Badge ids generated by Badge API to validate getBadgesById API
	 * Uses the badges pojo object
	 * @param expectedSize  used to validate the items size of the getBadgesById response
	 * @param resource      This is API's resource alias name.
	 *                      Accepts the following constants - getBadgesAPI,getRecipentAPI
	 *                      getRecipentsById, getBadgesById.
	 *                      These constants are mapped to an enum class to fetch us corr.
	 *                      resource path.
	 * @throws IOException
	 */
	@Then("verify badges fetched count maps to {int} using {string}")
    public void verify_badges_fetched_count_maps_to_using(Integer expectedSize, String resource) throws IOException {
		//pojo class's object used retrive and verify data
		gb = response.as(GetBadges.class);
		badge_ids= new ArrayList<String>();
		for (int i =0; i<gb.getItems().size(); i++) {
			badge_ids.add(gb.getItems().get(i).getBadge_id());
			//badge_names.add(gb.getItems().get(i).getName());
		}
		delimitedid = String.join(";", badge_ids);
		Integer size = gb.getItems().size();
	    req1=given().spec(requestSpecification());
		user_calls_with_http_request(resource,"GET");
		assertEquals(expectedSize, size);
    }
	
	/**
	 * Assert recipents of required Badge ids are fetched
	 * @param int1
	 * @param resource
	 * @throws IOException
	 */
	@Then("verify recipents fetched with status code {int} using {string}")
	public void verify_recipents_fetched_with_status_code_using(Integer int1, String resource) throws IOException {
		req1=given().spec(requestSpecification());
		user_calls_with_http_request(resource,"GET");
		assertEquals(200, response.getStatusCode());
	}
	
	/**
	 * Validate max size per page in response
	 * @param expectedSize
	 * @param resource
	 * @throws IOException
	 */
	@Then("verify badges fetched max size per page is {int} using {string}")
    public void verify_badges_fetched_max_size_per_page_is(Integer expectedSize, String resource) throws IOException {
		gb = response.as(GetBadges.class);
		badge_ids= new ArrayList<String>();
		for (int i =0; i<gb.getItems().size(); i++) {
			badge_ids.add(gb.getItems().get(i).getBadge_id());
			//badge_names.add(gb.getItems().get(i).getName());
		}
		delimitedid = String.join(";", badge_ids);
		Integer size = gb.getItems().size();
	    req1=given().spec(requestSpecification());
		user_calls_with_http_request(resource,"GET");
		assertNotEquals(expectedSize, size);
    }

}
