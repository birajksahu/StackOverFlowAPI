package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Responsible for complete flow, thus contains info of 
 * various config 
 * @author biraj
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features",plugin="json:target/jsonReports/cucumber-report.json", glue= {"stepDefinitions"} , stepNotifications = true)
public class TestRunner {

}