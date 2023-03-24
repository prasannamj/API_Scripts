package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.EcomLoginApiPOJO;
import pojo.EcomOrder;
import pojo.EcomOrderDetails;
import pojo.EcomloginResponce;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
public class EcomApiTest {

	public static void main(String[] args) {
			//building Spec builder for Reusable Base URI
			RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
			
			//POJO class for login credetials
			EcomLoginApiPOJO loginCred = new EcomLoginApiPOJO();
			loginCred.setUserEmail("prasannamj23@gmail.com");
			loginCred.setUserPassword("Qwerty@1");
			
			//login request sending
			RequestSpecification loginreq = given().relaxedHTTPSValidation().log().all().spec(req).body(loginCred);
			
			//getting login response
			EcomloginResponce EcomloginResponce= loginreq.when().post("/api/ecom/auth/login").then().log().all().extract().as(EcomloginResponce.class);
	
			String token = EcomloginResponce.getToken();
			String Userid =EcomloginResponce.getUserId();
			
			
			//Add product -(facing some issue from internal server)
			
			RequestSpecification AddProductBaseRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token).build();
			
			RequestSpecification AddProductReq =given().log().all().spec(AddProductBaseRequest).param("productName", "qwerty")
			.param("productAddedBy", "Userid")
			.param("productCategory", "fashion")
			.param("productSubCategory", "shirts")
			.param("productPrice", "11500")
			.param("productDescription", "Addias")
			.param("productFor", "women")
			.multiPart("productimage", new File("C:\\Users\\ADMIN\\Downloads\\productImage_1650649434146.JPEG"));
			
			
			String AddproductRes = AddProductReq.when().post("/api/ecom/product/add-product").then().log().all().extract().asString();
			JsonPath js = new JsonPath(AddproductRes);
			String productID = js.get("productId");
			
			
			
			//Create Order
			RequestSpecification CreateOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

//			String productID = "6262e95ae26b7e1a10e89bf0";
			
			EcomOrderDetails ORD = new EcomOrderDetails();
			ORD.setCountry("India"); 
			ORD.setProductOrderId(productID);
			
			List<EcomOrderDetails> orderdetailslist  = new ArrayList<EcomOrderDetails>();
			orderdetailslist.add(ORD);
			
			EcomOrder EcomOrder = new EcomOrder();
			EcomOrder.setOrders(orderdetailslist);
			
			RequestSpecification CreateOrderReq =given().log().all().spec(CreateOrderBaseReq).body(EcomOrder);
			
			String AddOrderResponse = CreateOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().asString();
			System.out.println(AddOrderResponse);
			
			
			
			//Delete Product

			RequestSpecification DeleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
			
			RequestSpecification DeleteOrderReq = given().log().all().spec(DeleteOrderBaseReq).pathParam("productId", productID);
	
			String deleteProductResponse = DeleteOrderReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().
					extract().response().asString();

					JsonPath js1 = new JsonPath(deleteProductResponse);

					Assert.assertEquals("Product Deleted Successfully",js1.get("message"));
	
	
	
	
	}

}
