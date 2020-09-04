package kctt;

import org.testng.annotations.Test;

import baseclass.Baseclass;
import baseclass.WebDrivers;

public class NewTest extends WebDrivers {

	Baseclass bc = new Baseclass();

	@Test(priority = 1, enabled = true)
	public void verifyContactsPageLabelTest() {
		String jsonString = "{\"stepNunber\":\"1\",\"actionType\":\"click\",\"action\":\"singleclick\",\"elementIdentifierType\":\"id\",\"elementIdentifier\":\"SALogIn_Txt_UserName\"}";

		getDriver().get("https://www.kantimemedicare.net/Z1/UI/Common/Login.aspx");
		bc.oActions(getDriver(), jsonString);
		System.out.println(getDriver().getTitle());

	}

}
