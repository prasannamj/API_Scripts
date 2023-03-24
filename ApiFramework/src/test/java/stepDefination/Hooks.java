package stepDefination;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefination m = new StepDefination();
		
		if(StepDefination.placeId==null)
		{
		m.add_place_payload_with("prasanna", "English", "India");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_using("prasanna", "GetPlaceAPI");
	}
	}
}
