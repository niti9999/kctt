package baseclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Baseclass {

	public void oActions(String actionType, String action, String elementIdentifierType, WebElement elementIdentifier) {
		switch (actionType) {
		case "click":
			switch (action) {
			case "singleClick":
				elementIdentifier.click();
				break;
			}
			break;
		}

	}

}
