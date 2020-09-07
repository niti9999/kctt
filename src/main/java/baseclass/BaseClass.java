package baseclass;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Baseclass {

	public String jsonParser(String jsonPath) {
		String json = null;
		try {
			JSONParser parser = new JSONParser();
			// Use JSONObject for simple JSON and JSONArray for array of JSON.
			// path to the JSON file.
			try {
				JSONObject data = (JSONObject) parser.parse(new FileReader(jsonPath));
				json = data.toJSONString();
				System.out.println("jsonParser output: " + json);
			} catch (Exception e) {
				JSONArray data = (JSONArray) parser.parse(new FileReader(jsonPath));
				json = data.toJSONString();
				System.out.println("jsonParser output: " + json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void oActions(WebDriver driver, String jsonString) {
		WebElement element = null;
		Object obj1 = JSONValue.parse(jsonString);
		JSONObject jsonObject = (JSONObject) obj1;

		String actionType = (String) jsonObject.get("actionType");
		String action = (String) jsonObject.get("action");
		String elementIdentifierType = (String) jsonObject.get("elementIdentifierType");
		String elementIdentifier = (String) jsonObject.get("elementIdentifier");
		String elementproperties = (String) jsonObject.get("elementproperties");

		System.out.println("actionType: " + actionType);
		System.out.println("action: " + action);
		System.out.println("elementIdentifierType: " + elementIdentifierType);
		System.out.println("elementIdentifier: " + elementIdentifier);
		System.out.println("elementproperties: " + elementproperties);

		// ELEMENT IDENTIFIER TYPE -> USED TO FIND ELEMENT USING BY
		switch (elementIdentifierType) {
		case "id":
			element = driver.findElement(By.id(elementIdentifier));
			break;
		case "name":
			element = driver.findElement(By.name(elementIdentifier));
			break;
		case "className":
			element = driver.findElement(By.className(elementIdentifier));
			break;
		case "xpath":
			element = driver.findElement(By.xpath(elementIdentifier));
			break;
		case "linkText":
			element = driver.findElement(By.linkText(elementIdentifier));
			break;
		case "partialLinkText":
			element = driver.findElement(By.partialLinkText(elementIdentifier));
			break;
		}

		// ACTION TYPES
		switch (actionType) {

		// CLICK
		case "click":
			switch (action) {
			case "singleClick":
				element.click();
				break;
			}
			break;

		// SEND TEXT
		case "sendText":
			element.sendKeys(elementproperties);
		}

	}
}
