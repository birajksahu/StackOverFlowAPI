package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
/**
 * Contains all the utils methods to be used for the framework
 * @author biraj
 *
 */
public class Utils {
	public static RequestSpecification req;
	ResponseSpecification resspec;
	/**
	 * Creates Request Specification object with provided query params
	 * @param order
	 * @param sort
	 * @param pagesize
	 * @param min
	 * @return
	 * @throws IOException
	 */
	public RequestSpecification requestSpecification(String order, String sort, String pagesize, String min) throws IOException {
		
		if (req==null) {
			//generates log.txt for helping in debugging
		    PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
		    //uses base url from global.properties file
		    req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				    .addQueryParam("order", order)
				    .addQueryParam("sort", sort)
				    .addQueryParam("pagesize", pagesize)
				    .addQueryParam("min", min)
				    .addQueryParam("site", "stackoverflow")
				    .addFilter(RequestLoggingFilter.logRequestTo(log))
				    .addFilter(ResponseLoggingFilter.logResponseTo(log))
				    .build();
		}
		else {
			req = null;
		    PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
		    req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				    .addQueryParam("order", order)
				    .addQueryParam("sort", sort)
				    .addQueryParam("pagesize", pagesize)
				    .addQueryParam("min", min)
				    .addQueryParam("site", "stackoverflow")
				    .addFilter(RequestLoggingFilter.logRequestTo(log))
				    .addFilter(ResponseLoggingFilter.logResponseTo(log))
				    .build();
			
		}
		return req;
		
	}
	/**
	 * Creates request Spec without query params passed
	 * @return
	 * @throws IOException
	 */
	public RequestSpecification requestSpecification() throws IOException  {
		    PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
		    req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				    .addQueryParam("order", "desc")
				    .addQueryParam("sort", "rank")
				    .addQueryParam("site", "stackoverflow")
				    .addFilter(RequestLoggingFilter.logRequestTo(log))
				    .addFilter(ResponseLoggingFilter.logResponseTo(log))
				    .build();
		return req;
		
	}
	
	/**
	 * Helps in getting value from global properties file
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public String resourcePath(String resource) throws IOException {
		return getGlobalValue(resource);
	}
	
	/**
	 * Returns Response Spec
	 * @return
	 */
	public ResponseSpecification responseSpecification() {
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
		return resspec;
	}
	
	/**
	 * Retuns property based on key from global.properties file
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/java/resources/global.properties");
		prop.load(fis);
		//System.out.println(prop.getProperty(key));
		return prop.getProperty(key);
	}
	
	/**
	 * Returns a specific key value from a response
	 * @param response
	 * @param key
	 * @return
	 */
	public String getJsonPath(Response response, String key) {
		JsonPath js = new JsonPath(response.asString());
		return js.get(key).toString();
	}

}
