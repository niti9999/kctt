package constants;

import org.apache.commons.math3.distribution.GeometricDistribution;

import baseclass.PageBase;


public class Constants extends PageBase {
	public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/Drivers/chromedriver.exe";
	public static final String INTERNET_EXPLORER_DRIVER_PATH = System.getProperty("user.dir")
			+ "/Drivers/IEDriverServer.exe";
	public static final String FIREFOX_DRIVER_PATH = System.getProperty("user.dir") + "/Drivers/geckodriver.exe";

	// public static final long PAGE_LOAD_TIMEOUT = 30;
	// public static final long IMPLICIT_WAIT = 15;
	// public static final long EXPLICIT_WAIT = 15;

	public static final String DOWNLOAD_FOLDER = System.getProperty("user.dir") + "/All Folders/Screenshots";

}
