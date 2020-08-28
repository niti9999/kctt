package baseclass;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDrivers {

	private static WebDriver driver;

	/**
	 * Default constructor.
	 * 
	 * @return
	 */
	protected WebDrivers() {
		WebDrivers.getDriver();
	}

	/**
	 * get the new driver.
	 * 
	 * @return
	 */
	private static WebDriver getDriver() {
		String defaultDownloadFolder = PageBase.getDefaultDownloadFolder();
		String geckoDriverFolder = PageBase.getGeckoDriverPath();
		if (driver == null) {

			DesiredCapabilities dc = null;

			Object browserType = "firefox";

			if (browserType.equals("firefox")) {
				dc = DesiredCapabilities.firefox();
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.WINDOWS);

				System.setProperty("webdriver.gecko.driver", geckoDriverFolder);
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.download.folderList", 2);
				options.addPreference("browser.download.dir", defaultDownloadFolder);
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

			try {

				driver = new RemoteWebDriver(new URL("http://192.168.2.92:4444/wd/hub"), dc);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return driver;
	}

	// /**
	// * sets the driver as null, so that it can be reused after the quit()
	// method
	// *
	// * @param driver
	// */
	// public static void setDriverAsNULL(WebDriver driver) {
	// WebDriverClass.driver = null;
	// }

	/**
	 * Getting the instance of the driver.
	 * 
	 * @return
	 */
	public static WebDriver getInstance() {
		if (driver == null) {
			WebDrivers.getDriver();
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
		WebDrivers.driver.quit();
	}

	/**
	 * final for getting the main page windowhandle.
	 */
	public static final String ORIGINALHANDLE = WebDrivers.getInstance().getWindowHandle();

}