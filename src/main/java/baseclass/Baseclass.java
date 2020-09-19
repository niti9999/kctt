package baseclass;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.not;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Baseclass {

	private WebDriver driver;
	int WaitForElementMaxDurationSecond;

	/**
	 * Main action class for the selenium
	 * 
	 * @param driver
	 * @param jsonString
	 */
	public void oActionsRequest(WebDriver driver, String jsonString) {

		JSONObject baseJsonObject = (JSONObject) JSONValue.parse(jsonString);

		this.setDriver(driver);
		String actionType = (String) baseJsonObject.get("ActionType");
		String action = (String) baseJsonObject.get("Action");
		String elementIdentifierType = (String) baseJsonObject.get("ElementIdentifierType");
		String elementIdentifier = (String) baseJsonObject.get("ElementIdentifier");
		String elementproperties = (String) baseJsonObject.get("Elementproperties");
		WaitForElementMaxDurationSecond = ((Long) baseJsonObject.get("WaitForElementMaxDurationSecond")).intValue();
		;

		oActions(driver, actionType, action, elementIdentifierType, elementIdentifier, elementproperties);

		LinkedHashMap<Integer, JSONObject> actionStepsMap = new LinkedHashMap<Integer, JSONObject>();
		JSONArray actionStepsArray = (JSONArray) baseJsonObject.get("ActionSteps");

		if (actionStepsArray != null) {
			for (int i = 0; i < actionStepsArray.size(); i++) {

				JSONObject actionStepJson = (JSONObject) JSONValue.parse(actionStepsArray.get(i).toString());
				int StepNunber = ((Long) actionStepJson.get("StepNunber")).intValue();
				JSONObject StepsJson = (JSONObject) actionStepJson.get("Steps");
				actionStepsMap.put(StepNunber, StepsJson);
			}
		}

		/*
		 * get the array items from json
		 */

		if (actionStepsArray != null) {
			for (int i = 1; i < actionStepsArray.size(); i++) {
				actionStepsMap.get(i);

				String aoActionType = (String) actionStepsMap.get(i).get("ActionType");
				String aoAction = (String) actionStepsMap.get(i).get("Action");
				String aoElementIdentifierType = (String) actionStepsMap.get(i).get("ElementIdentifierType");
				String aoElementIdentifier = (String) actionStepsMap.get(i).get("ElementIdentifier");
				String aoElementproperties = (String) actionStepsMap.get(i).get("ElementProperties");

				oActions(driver, aoActionType, aoAction, aoElementIdentifierType, aoElementIdentifier,
						aoElementproperties);
			}
		}

	}

	public void oActions(WebDriver driver, String actionType, String action, String elementIdentifierType,
			String elementIdentifier, String elementproperties) {

		WebElement element = null;
		this.setDriver(driver);

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

		/*
		 * CLICK
		 */
		case "click":
			switch (action) {
			case "singleClick":
				element.click();
				break;
			}
			break;

		/*
		 * SEND TEXT
		 */
		case "sendText":
			element.sendKeys(elementproperties);
			break;

		/*
		 * GET
		 */
		case "get":
			driver.get(elementproperties);
			break;

		/*
		 * WAIT
		 */
		case "wait":
			WebDriverWait wait = new WebDriverWait(driver, WaitForElementMaxDurationSecond);
			switch (action) {

			case "waitUntilElementTobeVisible":
				wait.until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "waitUntilElementToBeClickable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;

			case "waitUntilTextToDisappear":
				await().atMost((WaitForElementMaxDurationSecond), SECONDS).pollInterval(1, SECONDS)
						.until(element::getText, not(elementproperties));
				break;

			case "implicitlyWait":
				driver.manage().timeouts().implicitlyWait((WaitForElementMaxDurationSecond), TimeUnit.SECONDS);
				break;
			}
		}

	}

	/**
	 * JSON parser
	 * 
	 * @param jsonPath
	 * @return
	 */
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

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
