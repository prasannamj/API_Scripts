package stepDefination;

import static io.restassured.RestAssured.given;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resourses.ApiResources;
import resourses.TestDataBuild;
import resourses.Utiles;

public class StepDefination extends Utiles {

	RequestSpecification res;
	ResponseSpecification resspec ;
	Response response ;
	TestDataBuild data = new TestDataBuild();
	static String placeId;
	//Add place Request
@Given("Add place payload with {string}{string}{string}")
public void add_place_payload_with(String name, String language, String address) throws IOException {

				  
				  //for response
//				  resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				  res=given().spec(RequestSpecification())
				 .body(data.addPlacePayload(name,language,address));

	}

@When("user calls {string} with {string} Http Request")
public void user_calls_with_http_request(String resourse, String httpmethod) {
		
		ApiResources apiresource = ApiResources.valueOf(resourse);
		System.out.println(apiresource.getResourse());
		
		  resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		  
		  if(httpmethod.equalsIgnoreCase("POST"))
		  {
			  response =res.when().post(apiresource.getResourse());
		  }
		  else if(httpmethod.equalsIgnoreCase("GET"))
		  {
			  response =res.when().get(apiresource.getResourse());

		  }
	}
	
	@Then("the Api call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		
		assertEquals(response.statusCode(), 200);
		
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String Keyvalue, String Expectedvalue) 
	{

		 assertEquals(getJsonPath(response, Keyvalue).toString(),Expectedvalue);
	}

	//get Place request
	@Then("Verify place ID created maps {string} using {string}")
	public void verify_place_id_created_maps_using(String ExpectedName, String resourse) throws IOException 
	{
	     placeId = getJsonPath(response, "place_id");
	    res= given().spec(RequestSpecification()).queryParam("place_id", placeId);
	    user_calls_with_http_request(resourse,"GET");
	    
	    String Actualname = getJsonPath(response, "name");
	    assertEquals(Actualname,ExpectedName);
	}
	
	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException 
	{
		res = given().spec(RequestSpecification()).body(data.Deleteplacepayload(placeId));
	}



}
