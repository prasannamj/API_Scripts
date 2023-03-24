package demo;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTest {

	public static void main(String[]args)
	{
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();
		//login Api
		String response = given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"prasannamj23\", \"password\": \"9980240110\" }")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		String ExpectedMessage = "Hey i have Commented from Rest Api";
		
		//Add comment
		String addCommentResponse = given().pathParam("key", "10002").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \""+ExpectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String CommentID = js.getString("id");

	
		
		//Add Attachment
		given().pathParam("key", "10002").header("X-Atlassian-Token","no-check").filter(session)
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("C:\\Users\\ADMIN\\eclipse-workspace\\DynamicJsonPayload\\src\\test\\java\\File\\jira.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	

		//Get Issue
		String issuedetails = given().filter(session).pathParam("key", "10002")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{key}").then()
				.log().all().extract().response().asString();
				
		JsonPath js1 = new JsonPath(issuedetails);
		int Ccount = js1.getInt("fields.comment.comments.size()");
		for(int i =0;i<Ccount;i++)
		{
			String IssueCommentID = js1.get("fields.comment.comments["+i+"].id").toString();
			if(IssueCommentID.equalsIgnoreCase(CommentID))
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, ExpectedMessage);

			}
		}
	}
}
