package Datadriven;
import java.io.IOException;
import java.util.ArrayList;

import resourses.dataDriven;

public class testSample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		dataDriven d=new dataDriven();
	ArrayList data=d.getData("Profile", "testdata");
	
	System.out.println(data.get(0));
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));

	
		
	}

}
