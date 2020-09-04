package baseclass;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Baseclass {

	public void oActions(WebDriver driver,String actionType, String action, String elementIdentifierType, String elementIdentifier) {
		WebElement element = null;
		switch (actionType) {
		case "id": 
			element = driver.findElement(By.id("elementIdentifier"));
		}
		switch (actionType) {
		case "click":
			switch (action) {
			case "singleClick":
				element.click();
				break;
			}
			break;
		}

	}

	public void oActions(WebDriver driver,String jsonString) {

		Object obj1 = JSONValue.parse(jsonString);
		JSONObject jsonObject = (JSONObject) obj1;

		String actionType = (String) jsonObject.get("actionType");
		String action = (String) jsonObject.get("action");
		String elementIdentifierType = (String) jsonObject.get("elementIdentifierType");
		String elementIdentifier = (String) jsonObject.get("elementIdentifier");
		System.out.println(actionType);
		System.out.println(action);
		System.out.println(elementIdentifierType);
		System.out.println(elementIdentifier);
		WebElement element = null;
		switch (actionType) {
		case "id": 
			element = driver.findElement(By.id("elementIdentifier"));
		}
		switch (actionType) {
		case "click":
			switch (action) {
			case "singleClick":
				element.click();
				break;
			}
			break;
		}

	}
}
