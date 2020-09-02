package kctt;

import org.testng.annotations.Test;

import baseclass.Baseclass;
import baseclass.WebDrivers;

public class NewTest extends WebDrivers {
	
	Baseclass bc;

	@Test(priority = 1, enabled = true)
	public void verifyContactsPageLabelTest() {

		bc = new Baseclass();
		getDriver().get("https://www.google.com");
		
	}

}
