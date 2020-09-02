package baseclass;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import constants.Constants;

public class WebDrivers {

	private WebDriver driver;

	/**
	 * Default constructor.
	 * 
	 * @return
	 */
	protected WebDrivers() {
		getDriver();
	}

	/**
	 * get the new driver.
	 * 
	 * @return
	 */
	private WebDriver InitiateDriver() {
		//String defaultDownloadFolder = PageBase.getDefaultDownloadFolder();
		if (driver == null) {

			DesiredCapabilities dc = null;

			Object browserType = "firefox";

			if (browserType.equals("firefox")) {
				dc = DesiredCapabilities.firefox();
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.WINDOWS);

				System.setProperty("webdriver.gecko.driver", Constants.FIREFOX_DRIVER_PATH);
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.download.folderList", 2);
			//	options.addPreference("browser.download.dir", defaultDownloadFolder);
				options.addPreference("browser.download.useDownloadDir", true);
				options.addPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/xml,application/pdf,application/xls, application/csv,"
								+ " application/xlsx, application/vnd.ms-excel, application/octet-stream, "
								+ ".xls application/vnd.ms-excel, application/x-msexcel, application/excel, "
								+ "application/x-excel, application/vnd.ms-excel");
				options.addPreference("pdfjs.disabled", true);
				dc.merge(options);

			} else {
				dc = DesiredCapabilities.internetExplorer();
				dc.setBrowserName("iexplore");
				dc.setPlatform(Platform.WINDOWS);
			}

			// driver = new FirefoxDriver();
			try {
				driver = new RemoteWebDriver(new URL("http://192.168.2.92:4444/wd/hub"), dc);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return driver;
	}

	/**
	 * Getting the instance of the driver.
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		if (driver == null) {
			InitiateDriver();
			return driver;
		} else {
			return driver;
		}
	}

	/**
	 * Quit the driver.
	 * 
	 * @return
	 */
	public void closeDriver() {
		driver.quit();
	}

}