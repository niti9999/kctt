package kctt;

import org.testng.annotations.Test;
import baseclass.WebDrivers;

public class NewTest extends WebDrivers {

	@Test(priority = 1, enabled = true)
	public void verifyContactsPageLabelTest() {

		getInstance().get("https://www.google.com");
		System.out.println(getInstance().getTitle());
	}

}
