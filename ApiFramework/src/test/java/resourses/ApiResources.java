package resourses;

//enum is a special class in java which has collection of constants/methods
public enum ApiResources {

	AddPlaceAPI("/maps/api/place/add/json"),
	GetPlaceAPI("/maps/api/place/get/json"),
	DeleteplaceAPI("/maps/api/place/delete/json");
	
	private String resourse;
	
	ApiResources(String resourse)
	{
		this.resourse =resourse;
	}
	
	public String getResourse()
	{
		return resourse;
	}
}
