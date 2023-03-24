package resourses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utiles {

	public static RequestSpecification req;
	public RequestSpecification RequestSpecification() throws IOException
	{
		
		if(req == null)
		{
		//logging response & request
		PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
		
		//adding spec builder for reusable things like URI,Headers etc 
		req=new RequestSpecBuilder().setBaseUri(GetGlobalValue("baseURL")).addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	
	public String GetGlobalValue(String key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\ADMIN\\eclipse-workspace\\ApiFramework\\src\\test\\java\\resourses\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
	
	public String getJsonPath(Response response, String key)
	{
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}
}
