package baseclass;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.not;
import java.io.FileReader;
import java.util.ArrayList;
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
	private String WaitForElementMaxDurationSecond;

	/**
	 * Main action class for the selenium
	 * 
	 * @param driver
	 * @param jsonString
	 */
	public void oActionsRequest(WebDriver driver, String jsonString) {

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);

		this.setDriver(driver);
		String actionType = (String) jsonObject.get("ActionType");
		String action = (String) jsonObject.get("Action");
		String elementIdentifierType = (String) jsonObject.get("ElementIdentifierType");
		String elementIdentifier = (String) jsonObject.get("ElementIdentifier");
		String elementproperties = (String) jsonObject.get("Elementproperties");
		String WaitForElementMaxDurationSecond = (String) jsonObject.get("WaitForElementMaxDurationSecond");

		/*
		 * ****************************************
		 */
		System.out.println("actionType: " + actionType);
		System.out.println("action: " + action);
		System.out.println("elementIdentifierType: " + elementIdentifierType);
		System.out.println("elementIdentifier: " + elementIdentifier);
		System.out.println("elementproperties: " + elementproperties);
		System.out.println("WaitForElementMaxDurationSecond: " + WaitForElementMaxDurationSecond);
		/*
		 ******************************************* 
		 */

		oActions(driver, actionType, action, elementIdentifierType, elementIdentifier, elementproperties);

		/*
		 * get the array items from json
		 */
		ArrayList<String> listdata = new ArrayList<String>();
		JSONArray arrayData = (JSONArray) jsonObject.get("ActionSteps");
		if (arrayData != null) {
			for (int i = 0; i < arrayData.size(); i++) {
				listdata.add(arrayData.get(i).toString());
				System.out.println(arrayData.get(i));

				JSONObject jsonArrayObject = (JSONObject) JSONValue.parse(arrayData.get(i).toString());

				int aoStepNunber = ((Long) jsonArrayObject.get("StepNunber")).intValue();
				String aoActionType = (String) jsonArrayObject.get("ActionType");
				String aoAction = (String) jsonArrayObject.get("Action");
				String aoElementIdentifierType = (String) jsonArrayObject.get("ElementIdentifierType");
				String aoElementIdentifier = (String) jsonArrayObject.get("ElementIdentifier");
				String aoElementproperties = (String) jsonArrayObject.get("Elementproperties");

				/*
				 * ****************************************
				 */
				System.out.println("aoStepNunber: " + aoStepNunber);
				System.out.println("aoActionType: " + aoActionType);
				System.out.println("aoAction: " + aoAction);
				System.out.println("aoElementIdentifierType: " + aoElementIdentifierType);
				System.out.println("aoElementIdentifier: " + aoElementIdentifier);
				System.out.println("aoElementproperties: " + aoElementproperties);

				/*
				 ******************************************* 
				 */

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
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(WaitForElementMaxDurationSecond));
			switch (action) {

			case "waitUntilElementTobeVisible":
				wait.until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "waitUntilElementToBeClickable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;

			case "waitUntilTextToDisappear":
				await().atMost(Integer.parseInt(WaitForElementMaxDurationSecond), SECONDS).pollInterval(1, SECONDS)
						.until(element::getText, not(elementproperties));
				break;

			case "implicitlyWait":
				driver.manage().timeouts().implicitlyWait(Integer.parseInt(WaitForElementMaxDurationSecond),
						TimeUnit.SECONDS);
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
