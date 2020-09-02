package baseclass;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.not;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.lowagie.text.Utilities;

import constants.Constants;

public class PageBase extends WebDrivers {

	String host;
	private Integer value;
	Utilities utils = new Utilities();
	Object[][] tabArray = null;
	Workbook workbook = null;

	/**
	 * waits for the visibility of element. Max time to wait from config file.
	 * 
	 * @param element
	 *            element
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForVisibilityOfElement(WebDriver driver, WebElement element) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * waits for the visibility of list of element. Max time to wait from config
	 * file.
	 * 
	 * @param elements
	 *            elements
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForVisibilityOfListOfElement(WebDriver driver, List<WebElement> elements)
			throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	/**
	 * waits for the visibility of either of the element. Max time to wait from
	 * config file.
	 * 
	 * @param element1
	 *            element1
	 * @param element2
	 *            element2
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForVisibilityOfEitherOfTheElements(WebDriver driver, WebElement element1,
			WebElement element2) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(element1),
				ExpectedConditions.visibilityOf(element2)));
	}

	/**
	 * waits for the visibility of either of the element. Max time to wait from
	 * config file.
	 * 
	 * @param element1
	 *            element1
	 * @param element2
	 *            element2
	 * @param element3
	 *            element3
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForVisibilityOfEitherOfTheElements(WebDriver driver, WebElement element1,
			WebElement element2, WebElement element3) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(element1),
				ExpectedConditions.visibilityOf(element2), ExpectedConditions.visibilityOf(element3)));
	}

	/**
	 * wait until element 1 is visible OR element 2 has the given text.
	 * 
	 * @param element1ToBeVisible
	 *            element1ToBeVisible
	 * @param element2ToHaveText
	 *            element2ToHaveText
	 * @param element2ExpectedText
	 *            element2ExpectedText
	 * @throws IOException
	 *             IOException
	 */
	public void explicitlyWaitForEitherOfThese(WebDriver driver, WebElement element1ToBeVisible,
			WebElement element2ToHaveText, String element2ExpectedText) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(element1ToBeVisible),
				ExpectedConditions.textToBePresentInElement(element2ToHaveText, element2ExpectedText)));
	}

	/**
	 * wait until first element is visible OR second element is invisible.
	 * 
	 * @param element1ToBeVisible
	 *            element1ToBeVisible
	 * @param element2ToBeInVisible
	 *            element2ToBeInVisible
	 * @throws IOException
	 *             IOException
	 */
	public void explicitlyWaitForEitherOfThese(WebDriver driver, WebElement element1ToBeVisible,
			WebElement element2ToBeInVisible) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(element1ToBeVisible),
				ExpectedConditions.invisibilityOf(element2ToBeInVisible)));
	}

	/**
	 * waits for the element to be clickable. Max time to wait from config file.
	 * 
	 * @param element
	 *            element
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForElementToBeClickable(WebDriver driver, WebElement element) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * wait for the element to be invisible.
	 * 
	 * @param element
	 *            element
	 * @throws IOException
	 *             IOException
	 */
	public void explicitWaitForElementToInvisible(WebDriver driver, WebElement element) throws IOException {
		final WebDriverWait wait = new WebDriverWait(driver, getIntConfigProperties("WaitForElementMaxTimeDuration"));
		wait.until(ExpectedConditions.invisibilityOfAllElements(element));
	}

	/**
	 * waits for the element to be clickable. Max time to wait from config file.
	 * 
	 * @param element
	 *            element
	 * @throws InterruptedException
	 *             InterruptedException
	 * @throws IOException
	 *             IOException
	 */
	public void waitForPrintToGenerate(WebDriver driver, WebElement element) throws InterruptedException, IOException {
		final WebDriverWait wait = new WebDriverWait(driver,
				getIntConfigProperties("waitForPrintToGenerateMaxTimeDuration"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Thread.sleep(2000);

	}

	/**
	 * click on the webelement (alternative to click function).
	 * 
	 * @param elementAsWebelement
	 *            elementAsWebelement
	 */
	public void clickOnElementByMovingCursorOnIt(WebDriver driver, WebElement elementAsWebelement) {
		Actions act = new Actions(driver);
		act.moveToElement(elementAsWebelement).click().build().perform();
	}

	/**
	 * waits for the text to disappear in element. Max time to wait from config
	 * file.
	 * 
	 * @param element
	 *            element
	 * @param textToDisappear
	 *            TextToDisappear
	 * @throws IOException
	 *             IOException
	 */
	public void fluentWaitForTextToDisappearInElement(final WebElement element, String textToDisappear)
			throws IOException {
		await().atMost(getIntConfigProperties("FluentWaitMaxTimeDuration"), SECONDS).pollInterval(1, SECONDS)
				.until(element::getText, not(textToDisappear));
	}

	/**
	 * return string fields from the config file.
	 * 
	 * @param property
	 *            Property
	 * @return string
	 * @throws IOException
	 *             IOException
	 */
	public String getConfigProperties(String property) throws IOException {
		File configFile = new File("config.properties");

		FileReader reader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(reader);
		host = props.getProperty(property);
		reader.close();
		return host;
	}

	/**
	 * return integer fields from the config file.
	 * 
	 * @param property
	 *            property
	 * @return integer
	 * @throws IOException
	 *             IOException
	 */
	public Integer getIntConfigProperties(String property) throws IOException {
		File configFile = new File("config.properties");
		FileReader reader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(reader);
		host = props.getProperty(property);
		if (host != null) {
			value = new Integer(host);
		}
		reader.close();
		return value;
	}

	/**
	 * deletes all the content of the given folder.
	 * 
	 * @param folderPathInFileFormat
	 *            folderPathInFileFormat
	 * @throws IOException
	 *             IOException
	 */
	public void deleteAllContentsInTheFolder(String folderPathInFileFormat) {
		try {
			FileUtils.cleanDirectory(new File(folderPathInFileFormat));
		} catch (IOException e) {
			System.out.println("unable to clean the output folder");
		}
	}

	/**
	 * Copies all the content from the source folder to the destination folder.
	 *
	 * @param sourceFolderPathInFileFormat
	 *            sourceFolderPathInFileFormat
	 * @param destinationFolderPathInFileFormat
	 *            destinationFolderPathInFileFormat
	 * @throws IOException
	 *             IOException
	 */
	public void copyAllContentsOfTheFolderToAnotherFolder(String sourceFolderPathInFileFormat,
			File destinationFolderPathInFileFormat) throws IOException {
		File adirectory = new File(sourceFolderPathInFileFormat);
		String[] filesInDir = adirectory.list();
		if (filesInDir != null) {
			if (filesInDir.length == 0) {
				throw new IndexOutOfBoundsException();
			}
		}
		FileUtils.copyDirectory(new File(sourceFolderPathInFileFormat), destinationFolderPathInFileFormat);
	}

	/**
	 * Copies file to folder.
	 * 
	 * @param sourceFolderPathInFileFormat
	 *            sourceFolderPathInFileFormat
	 * @param destinationFolderPathInFileFormat
	 *            destinationFolderPathInFileFormat
	 * @throws IOException
	 *             IOException
	 */
	public void copyFileToFolder(File sourceFolderPathInFileFormat, File destinationFolderPathInFileFormat)
			throws IOException {
		FileUtils.copyFileToDirectory(sourceFolderPathInFileFormat, destinationFolderPathInFileFormat, true);
	}

	/**
	 * renames the folder in the destination folder retaining the timestamp of
	 * the files.
	 * 
	 * @param sourceFolderPathInFileFormat
	 *            SourceFolderPathInFileFormat
	 * @param destFolderPathInFileFormat
	 *            DestFolderPathInFileFormat
	 * @param fileName
	 *            FileName
	 * @param extensionWithDot
	 *            ExtensionWithDot
	 */
	public void renameFileInDestFolderSameAsSourceFolderWithTimeStamp(String sourceFolderPathInFileFormat,
			File destFolderPathInFileFormat, String fileName, String extensionWithDot) {
		fileName = sanitizeName(fileName);
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
		File adirectory = new File(sourceFolderPathInFileFormat);
		String[] filesInDir = adirectory.list();
		if (filesInDir != null) {
			for (int i = 0; i < filesInDir.length; i++) {
				// System.out.println( "file: " + filesInDir[i] );
				File src = new File(destFolderPathInFileFormat + "/" + filesInDir[i]);
				src.renameTo(
						new File(destFolderPathInFileFormat + "/" + fileName + "_" + timestamp + extensionWithDot));
			}
		}
	}

	/**
	 * removes all the special characters from the given string.
	 * 
	 * @param input
	 *            input
	 * @return
	 */
	public String sanitizeName(String input) {
		char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();
		char[] charArray = input.toString().toCharArray();
		StringBuilder result = new StringBuilder();
		for (char c : charArray) {
			for (char a : allowed) {
				if (c == a) {
					result.append(a);
				}
			}
		}
		return result.toString();
	}

	/**
	 * get the zip file by providing the source and destination folder. Get
	 * output file with the timestamp.
	 * 
	 * @param sourceFolder
	 *            sourceFolder
	 * @param destinationFolder
	 *            destinationFolder
	 * @param zipName
	 *            zipName
	 * @param zipExtension
	 *            zipExtension
	 * @return
	 */
	public String getZipFileofFolder(String sourceFolder, String destinationFolder, String zipName,
			String zipExtension) {
		FileZip appzip = new FileZip();
		String outputFile = appzip.getZipFile(sourceFolder, destinationFolder, zipName, zipExtension);
		return outputFile;
	}

	/**
	 * This will send the email to given email ID.
	 * 
	 * @param server
	 *            Server
	 * @param from
	 *            from
	 * @param pass
	 *            pass
	 * @param to
	 *            to
	 * @param subject
	 *            subject
	 * @param body
	 *            body
	 * @param attachmentpath
	 *            attachmentpath
	 * @param confirmMailingrequired
	 *            confirmMailingrequired
	 * @throws MessagingException
	 *             MessagingException
	 */
	public void sendPdfReportBySmtp(String server, String from, String pass, String to, String subject, String body,
			String attachmentpath, Boolean confirmMailingrequired) throws MessagingException {
		if (confirmMailingrequired == true) {
			Properties props = System.getProperties();
			String host = server;
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);

			// Set from address
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set subject
			message.setSubject(subject);
			message.setText(body);
			BodyPart objMessageBodyPart = new MimeBodyPart();
			objMessageBodyPart.setText(body);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(objMessageBodyPart);
			objMessageBodyPart = new MimeBodyPart();
			// Set path to the pdf report file
			String filename = attachmentpath;
			// System.getProperty("user.dir") +
			// "\\TestResults_967583650_2018_08_06__09_08_46.pdf";
			// Create data source to attach the file in mail
			DataSource source = new FileDataSource(filename);
			objMessageBodyPart.setDataHandler(new DataHandler(source));
			objMessageBodyPart.setFileName(filename);
			multipart.addBodyPart(objMessageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sucessfully mailed the test results");

		}
	}

	/**
	 * This function will take screenshot.
	 * 
	 * @param webdriver
	 *            webdriver
	 * @param fileWithPath
	 *            fileWithPath
	 * @throws Exception
	 *             Exception
	 */
	public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		/*
		 * Convert web driver object to TakeScreenshot
		 */
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		/*
		 * Call getScreenshotAs method to create image file
		 */
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

		/*
		 * Move image file to new destination
		 */
		File destFile = new File(fileWithPath);

		/*
		 * Copy file at destination
		 */
		FileUtils.copyFile(srcFile, destFile);
	}

	/**
	 * date as String in given format. mm/dd/yyyy => send as "MM/dd/YYYY".
	 * 
	 * @param format
	 *            format
	 * @return string string
	 */
	public String getTodaysDateInStringFormat(String format) {
		Date objDate = new Date();
		String strDateFormat = format;
		SimpleDateFormat objSdf = new SimpleDateFormat(strDateFormat);
		return (objSdf.format(objDate));
	}

	/**
	 * week Start Date from of the current week for mm/dd/yyyy => send as
	 * "MM/dd/YYYY".
	 * 
	 * @param format
	 *            format
	 * @return string string
	 */
	public String getCurrentWeekStartDateInStringFormat(String format) {
		String strDateFormat = format;
		SimpleDateFormat objSdf = new SimpleDateFormat(strDateFormat);
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		return objSdf.format(calendar.getTime());
	}

	/**
	 * week Start Date from of the current week for mm/dd/yyyy => send as
	 * "MM/dd/YYYY".
	 * 
	 * @param format
	 *            format
	 * @return string string
	 */
	public String getYesterdaysDateInStringFormat(String format) {
		String strDateFormat = format;
		SimpleDateFormat objSdf = new SimpleDateFormat(strDateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return objSdf.format(calendar.getTime());
	}

	/**
	 * convert string to int. Send only the strings that can be converted to
	 * integer.
	 * 
	 * @param text
	 *            text
	 * @return integer
	 */
	public int convertStringToInteger(String text) {
		int result = 0;
		try {
			result = Integer.parseInt(text);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * element which needs to be scrolled into view and clicked.
	 * 
	 * @param element
	 *            element which needs to be clicked
	 * @throws IOException
	 *             IO Exception
	 */
	public void scrollElementIntoViewAndClick(WebDriver driver, WebElement element) throws IOException {
		scrollElementIntoView(getDriver(), element);
		explicitWaitForVisibilityOfElement(getDriver(), element);
		explicitWaitForElementToBeClickable(getDriver(), element);
		element.click();
	}

//	/**
//	 * gives the window handles (window ID).
//	 * 
//	 * @param webdriver
//	 *            - driver
//	 * @return
//	 */
//	public String[] getWindowshandles(WebDriver driver) {
//		driver.switchTo().window(ORIGINALHANDLE);
//		Set<String> handles = getDriver().getWindowHandles();
//		Iterator<String> it = handles.iterator();
//		/*
//		 * iterate through your windows
//		 */
//		String[] windowHandles = null;
//		while (it.hasNext()) {
//			windowHandles = new String[2];
//			windowHandles[0] = it.next();
//			windowHandles[1] = it.next();
//		}
//		return windowHandles;
//	}

//	/**
//	 * fetches the last window opened.
//	 * 
//	 * @param webdriver
//	 *            - driver
//	 * @return
//	 */
//	public String windowsHandlerGetNewPopup(WebDriver webdriver) {
//		return windowsHandlerGetPopup(webdriver, 10000);
//	}

//	/**
//	 * fetches the pop up based on windowNum.
//	 * 
//	 * @param webdriver
//	 *            - driver
//	 * @param windowNum
//	 *            - windows number
//	 * @return
//	 */
//	public String windowsHandlerGetPopup(WebDriver webdriver, int windowNum) {
//		getDriver().switchTo().window(ORIGINALHANDLE);
//
//		// Store your parent window
//		// String parentWindowHandler = webdriver.getWindowHandle();
//
//		String subWindowHandler = "";
//		String subWindowHandlerTmp = "";
//		int i = 0;
//
//		/*
//		 * get all window handles
//		 */
//		Set<String> handles = webdriver.getWindowHandles();
//		Iterator<String> iterator = handles.iterator();
//		iterator = handles.iterator();
//		while (iterator.hasNext()) {
//			subWindowHandlerTmp = iterator.next();
//			if (i == windowNum) {
//				subWindowHandler = subWindowHandlerTmp;
//			}
//			i++;
//		}
//		if (subWindowHandler.equals("")) {
//			subWindowHandler = subWindowHandlerTmp;
//		}
//		return subWindowHandler;
//	}

//	/**
//	 * It will close all the current window, except main window.
//	 * 
//	 * @param webdriver
//	 *            - driver
//	 */
//	public void closeAllPopupExceptMainPage(WebDriver webdriver) {
//		getDriver().switchTo().window(ORIGINALHANDLE);
//		Set<String> handles = webdriver.getWindowHandles();
//		Iterator<String> it = handles.iterator();
//		while (it.hasNext()) {
//			String childwin = it.next();
//			if (!childwin.equals(ORIGINALHANDLE)) {
//				getDriver().switchTo().window(childwin);
//				getDriver().close();
//				getDriver().switchTo().window(ORIGINALHANDLE);
//			}
//		}
//	}

	/**
	 * select item in a dropdown by value.
	 * 
	 * @param dropdownAsWebElement
	 *            - web element
	 * @param valueAsString
	 *            - value
	 */
	public void selectValueInDropDownByValue(WebElement dropdownAsWebElement, String valueAsString) {
		Select sel = new Select(dropdownAsWebElement);
		sel.selectByValue(valueAsString);
	}

	/**
	 * select item in a dropdown by visible Text.
	 * 
	 * @param dropdownAsWebElement
	 *            - web element
	 * @param valueAsString
	 *            - value
	 */
	public void selectValueInDropDownByVisibleText(WebElement dropdownAsWebElement, String valueAsString) {
		Select sel = new Select(dropdownAsWebElement);
		sel.selectByVisibleText(valueAsString);
	}

	/**
	 * select item in a dropdown by ind
	 * 
	 * @param dropdownAsWebElement
	 *            - web element
	 */
	public void selectValueInDropDownByindex(WebElement dropdownAsWebElement, int indexAsInteger) {
		Select sel = new Select(dropdownAsWebElement);
		sel.selectByIndex(indexAsInteger);
	}

	/**
	 * gets the selected value (visible text) from the drop down.
	 * 
	 * @param dropdownAsWebElement
	 *            - web element
	 * @return gets the text from the selected value in the dropdown
	 */
	public String getSelectedValueFromDropdown(WebElement dropdownAsWebElement) {
		Select sel = new Select(dropdownAsWebElement);
		String selectedValue = sel.getFirstSelectedOption().getText();
		return selectedValue;
	}

	/**
	 * max time to wait for the download.
	 * 
	 * @return time in integer
	 * @throws IOException
	 *             - exception
	 */
	public int getMaxAllowableTimeForDownloadCompletion() throws IOException {
		return getIntConfigProperties("waitForDownloadCompletionMaxTime");
	}

	/**
	 * function used to return the Outputfilename by appending it with the main
	 * class name.
	 * 
	 * @param OutputFileName
	 *            - OutputFileName
	 * @return string - string
	 * @throws ClassNotFoundException
	 *             - ClassNotFoundException
	 * 
	 */
	@SuppressWarnings("unused")
	private String fetchClassname(String outputFileName) throws ClassNotFoundException {
		String outputFileNameRef = outputFileName;
		String callerClassName = "";
		callerClassName = getCallerClass().getSimpleName();
		callerClassName += similar_string_remover(callerClassName, outputFileNameRef);
		if (callerClassName.equals("")) {
			callerClassName = outputFileNameRef;
		}
		return callerClassName;
	}

	/**
	 * function used to remove the unwanted String from the OutputFileName.
	 * 
	 * @param str1
	 *            - string 1
	 * @param str2
	 *            - string 2
	 * @return
	 */
	private String similar_string_remover(String str1, String str2) {
		// str1.replaceAll("_VerifyPageLoad", "");
		String[] str = str1.split("_");
		for (int i = 0; i < str.length; i++) {
			str2 = str2.replaceAll("(?i)" + str[i], "");
		}
		str2 = str2.replaceAll("_", "");
		str2 = str2.replaceAll("(?i)open", "");
		str2 = str2.replaceAll("_VerifyPageLoad", "");
		return "_" + str2;
	}

	/**
	 * function used to get the ClassName.
	 * 
	 * @param level
	 *            - level
	 * @throws ClassNotFoundException
	 *             - ClassNotFoundException
	 */
	protected Class<?> getCallerClass() throws ClassNotFoundException {
		String rawFqn = "";
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			if (stElements[i].toString().split("\\(")[0]
					.substring(0, stElements[i].toString().split("\\(")[0].lastIndexOf('.'))
					.equalsIgnoreCase(stElements[i + 1].toString().split("\\(")[0].substring(0,
							stElements[i + 1].toString().split("\\(")[0].lastIndexOf('.')))) {
				continue;
			} else {
				rawFqn = stElements[i + 1].toString().split("\\(")[0];
				break;
			}
		}
		return Class.forName(rawFqn.substring(0, rawFqn.lastIndexOf('.')));
	}

	/**
	 * function used to get the ClassMethod.
	 * 
	 * @param level
	 *            - level
	 * @throws ClassNotFoundException
	 *             - ClassNotFoundException
	 */
	protected String getCallerMethod(int level) throws ClassNotFoundException {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[level + 1].getMethodName();
	}

	/**
	 * scroll the element to view area. Scroll the page to view the element into
	 * viewable area.
	 * 
	 * @param webdriver
	 *            - webdriver
	 * @param element
	 *            - element
	 * @return
	 */
	public void scrollElementIntoView(WebDriver webdriver, WebElement element) {
		((JavascriptExecutor) webdriver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * get the text from the alert box.
	 * 
	 * @return
	 */
	public String switchToAlertAndGetText(WebDriver driver) {
		// using the try block here, since in some pages it is used to know if
		// the alert exist then get the text from it
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * accept the alert.
	 * 
	 * @return
	 */
	public void switchToAlertAndAccept(WebDriver driver) {
		// using the try block here, since in some pages it is used to know if
		// the alert exist then accept it
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * dismiss the alert box.
	 * 
	 * @return
	 */
	public void switchToAlertAndClose(WebDriver driver) {
		// using the try block here, since in some pages it is used to know if
		// the alert exist then dismiss it
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * wait for the alert to be present.
	 * 
	 * @throws IOException
	 *             IOException
	 * @throws InterruptedException
	 *             InterruptedException
	 */
	public void waitForAlertToBePresent(WebDriver driver) throws IOException, InterruptedException {
		final WebDriverWait wait = new WebDriverWait(driver,
				getIntConfigProperties("waitTimeForTheSucessOrFailureAlert"));
		wait.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(2000);
	}

	/**
	 * used to execute javscripts in a page. Use only when there are no other
	 * option.
	 * 
	 * @param element
	 *            element
	 */
	public void clickUsingJavascripts(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].click();", element);
	}

	/**
	 * check if download completed by polling every 2 sec and wait for max time
	 * from config file.
	 * 
	 * @throws InterruptedException
	 *             InterruptedException
	 * @throws IOException
	 *             IOException
	 */
	public void checkIfDownloadCompletedBypolling() throws InterruptedException, IOException {
		Thread.sleep(2000);
		String defaultDownloadFolder = Constants.DOWNLOAD_FOLDER;
		File dir = FileUtils.getFile(defaultDownloadFolder);
		boolean filefound = false;
		int count = 0;
		int maxTime = getMaxAllowableTimeForDownloadCompletion();
		while (count < maxTime) {
			for (String file : dir.list(new AndFileFilter(
					new OrFileFilter(new OrFileFilter(new SuffixFileFilter(".pdf"), new SuffixFileFilter(".csv")),
							new OrFileFilter(new SuffixFileFilter(".xls"), new SuffixFileFilter(".xlsx"))),
					new SizeFileFilter(1, true)))) {
				if (file != null) {
					System.out.println("Downloaded File found: " + file);
					filefound = true;
				}
			}
			Thread.sleep(1000);
			count++;
			if (filefound == true) {
				break;
			}
		}
	}

	/**
	 * function will explicitly wait until the page get loads.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	public void waitUntilPageLoads() throws Exception {
		Boolean readyStateComplete = false;
		while (!readyStateComplete) {
			readyStateComplete = ((String) ((JavascriptExecutor) getDriver())
					.executeScript("return document.readyState")).equals("complete");

			/* wait for all ajax to complete */
			waitForAjaxToComplete();
		}
		/*
		 * Check if Page is really Loaded
		 */
		try {
			explicitWaitForVisibilityOfElement(getDriver(), getDriver().findElement(By.tagName("html")));
		} catch (Exception e) {
			waitUntilPageLoads();
		}
	}

	

	/**
	 * get the max wait time for ajax to complete. Max time will be read from
	 * config file.
	 * 
	 * @return int
	 * @throws Exception
	 *             Exception
	 */
	public int getAjaxToCompleteMaxTimeDuration() throws Exception {
		int time = getIntConfigProperties("waitForAjaxToCompleteMaxTimeDuration");
		return time;
	}

	/**
	 * function will wait until the ajax to complete and throws timeout when
	 * time limit exceeds.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	public void waitForAjaxToComplete() throws Exception {
		int timeElapsed = 0;
		Thread.sleep(1000);
		while (true) {
			try {
				if ((Boolean) ((JavascriptExecutor) getDriver())
						.executeScript("return window.jQuery != undefined && jQuery.active === 0")) {
					break;
				}
			} catch (NullPointerException e) {
				break;
			}
			timeElapsed = timeElapsed + 500;
			if (timeElapsed > (getAjaxToCompleteMaxTimeDuration() * 1000)) {
				throw new Exception();
			}
			Thread.sleep(100);
		}
		Thread.sleep(1000);
	}

	/**
	 * get timestamp.
	 * 
	 * @return String
	 */
	public String getTimeStamp() {
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
		return timestamp;
	}

	/**
	 * checks if the element is empty.
	 * 
	 * @param element
	 *            element
	 * @return
	 */
	public Boolean isTextEmptyInElement(WebElement element) {
		try {
			element.getText();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * select value from the autofill and DO NOT wait for the AJAX to complete.
	 * 
	 * @param element
	 *            element
	 * @param value
	 *            value
	 * @throws Exception
	 *             Exception
	 */
	public void selectFromAutofillAndwaitForAjaxToComplete(WebElement element, String value) {
		selectFromAutofill(element, value, true);
	}

	/**
	 * select value from the autofill.
	 * 
	 * @param element
	 *            element
	 * @param value
	 *            value
	 * @param waitForAjaxToComplete
	 *            waitForAjaxToComplete
	 */
	public void selectFromAutofill(WebElement element, String value, Boolean waitForAjaxToComplete) {
		try {
			String id = element.getAttribute("id");

			/*
			 * input should be focused before click
			 */
			((JavascriptExecutor) getDriver()).executeScript("$('#" + id + "').focus();");

			/*
			 * input is clicked
			 */
			element.click();

			Thread.sleep(3000);

			/*
			 * if value is not there, select a random value
			 */
			if (value.equals("")) {

				/*
				 * sometimes click is not working so,pressing a backspace
				 */
				element.sendKeys(Keys.BACK_SPACE);
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ARROW_DOWN);

				/*
				 * some times the input is getting listing upside
				 */
				element.sendKeys(Keys.ARROW_UP);
				element.sendKeys(Keys.ENTER);
			} else {
				/*
				 * clear the element and send the values
				 */
				element.clear();
				element.sendKeys(value);

				Thread.sleep(1000);

				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ARROW_DOWN);

				/*
				 * some times the input is getting listing upside
				 */
				element.sendKeys(Keys.ARROW_UP);
				element.sendKeys(Keys.ENTER);
			}
			/*
			 * when browser is hidden mouse unexpectable condition is occuring
			 */
			if (!switchToAlertAndGetText(getDriver()).equals("")) {
				switchToAlertAndClose(getDriver());
				waitForAjaxToComplete();
				element.sendKeys(Keys.ARROW_DOWN);
				element.sendKeys(Keys.ENTER);
			}
			Thread.sleep(1000);
			/*
			 * blur is not getting handled automatically
			 */
			((JavascriptExecutor) getDriver()).executeScript("$('#" + id + "').blur();");
			/*
			 * onblur admit will get called
			 */
			if (waitForAjaxToComplete) {
				waitForAjaxToComplete();
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Error while selecting from autofill");
		}
	}

	/**
	 * This saves the page using the save button (as given in parameter and
	 * reloads the page).
	 * 
	 * @param saveButtonAsWebelement
	 *            SaveButtonAsWebelement
	 * @param requiresManualReloadOfPage
	 *            RequiresManualReloadOfPage
	 */
	public void saveAndReloadThePage(WebElement saveButtonAsWebelement, Boolean requiresManualReloadOfPage,
			String successMessageInTheAlert) {
		/*
		 * click on the save button
		 */
		saveButtonAsWebelement.click();

		/*
		 * wait for the alert to be present. If saved successfully, then
		 * proceed, else throw exception
		 */
		try {
			waitForAlertToBePresent(getDriver());
			if (switchToAlertAndGetText(getDriver()).toLowerCase().contains(successMessageInTheAlert.toLowerCase())) {

				/*
				 * close the alert
				 */
				switchToAlertAndClose(getDriver());

				/*
				 * wait for the page to reload
				 */
				waitUntilPageLoads();
				waitForAjaxToComplete();
				Thread.sleep(2000);

				/*
				 * if manual reload of the page is required
				 */
				if (requiresManualReloadOfPage) {

					/*
					 * refresh the page and wait for the page to load
					 * successfully
					 */
					getDriver().navigate().refresh();
					waitUntilPageLoads();
					waitForAjaxToComplete();
					Thread.sleep(2000);
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			Assert.assertTrue(false, "Some exception occured while saving the page" + e.toString());
		}
	}

	/**
	 * do HTML blur effect on the given element.
	 * 
	 * @param element
	 *            element
	 */
	public void doHtmlLBlurManuallyOnElement(WebElement element) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.println("thread exception catch");
		}
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].blur();", element);
	}

	/**
	 * check if the element is prefilled by sending element type and element and
	 * comparing with the expected value. This will also do the assertion based
	 * on parameter. <br>
	 * <b>Important for usage:</b> use elementType only as
	 * <li>radio</li>
	 * <li>checkbox</li>
	 * <li>textbox</li>
	 * <li>dropdown</li><br>
	 * 
	 * @param elementType
	 *            elementType
	 * @param element
	 *            Element
	 * @return
	 */
	public boolean checkIfElementIsPrefilled(String elementType, WebElement element, String expectedValue,
			Boolean requireAssertion) {
		/*
		 * get the prefilled value
		 */
		String prefilledValue = "";

		/*
		 * switch to elementType and get the prefilled value
		 */
		switch (elementType) {
		case "radio":
			prefilledValue = element.getAttribute("checked");
			/*
			 * replacing the expected value, since there is no expected value
			 * for it
			 */
			expectedValue = "true";
			break;
		case "checkbox":
			prefilledValue = element.getAttribute("checked");
			/*
			 * replacing the expected value, since there is no expected value
			 * for it
			 */
			expectedValue = "true";
			break;
		case "textbox":
			prefilledValue = element.getAttribute("value");
			break;
		case "dropdown":
			prefilledValue = getSelectedValueFromDropdown(element);
			break;
		default:
			break;
		}

		/*
		 * if the prefill value is unknown, then verify that something is
		 * prefilled
		 */
		Boolean isPrefilled = true;
		if (expectedValue.equals("")) {
			if (prefilledValue.equals("")) {
				if (requireAssertion == true) {
					Assert.assertTrue(false, "Element is not prefilled");
				} else {
					isPrefilled = false;
				}
			}
		} else if (!prefilledValue.equals(expectedValue)) {
			/*
			 * if PrefilledValue is not matching the ExpectedValue, then it is
			 * incorrect.
			 */
			if (requireAssertion == true) {
				Assert.assertTrue(false, "Element is not prefilled");
			} else {
				isPrefilled = false;
			}
		}

		return isPrefilled;
	}

	/**
	 * check if the element is prefilled by sending element type and element and
	 * comparing with the expected value. This will also do the assertion. <br>
	 * <b>Important for usage:</b> use elementType only as
	 * <li>radio</li>
	 * <li>checkbox</li>
	 * <li>textbox</li>
	 * <li>dropdown</li><br>
	 * 
	 * @param elementType
	 *            elementType
	 * @param element
	 *            Element
	 */
	public void checkIfElementIsPrefilled(String elementType, WebElement element, String expectedValue) {
		checkIfElementIsPrefilled(elementType, element, expectedValue, true);
	}

	/**
	 * check if the element is prefilled by some value by sending element type
	 * and element. This will also do the assertion.<br>
	 * <b>Important for usage:</b> use elementType only as
	 * <li>radio</li>
	 * <li>checkbox</li>
	 * <li>textbox</li>
	 * <li>dropdown</li><br>
	 * 
	 * @param elementType
	 *            elementType
	 * @param element
	 *            Element
	 */
	public void checkIfElementIsPrefilled(String elementType, WebElement element) {
		checkIfElementIsPrefilled(elementType, element, "", true);
	}

	/**
	 * check if the element is prefilled by some value by sending element type
	 * and element. This will<b> NOT </b>do the assertion.<br>
	 * <b>Important for usage:</b> use elementType only as
	 * <li>radio</li>
	 * <li>checkbox</li>
	 * <li>textbox</li>
	 * <li>dropdown</li><br>
	 * 
	 * @param elementType
	 *            elementType
	 * @param element
	 *            Element
	 */
	public boolean checkIfElementIsPrefilledWithoutAssertion(String elementType, WebElement element) {
		return checkIfElementIsPrefilled(elementType, element, "", false);
	}

	/**
	 * verify if the element is enabled.
	 * 
	 * @param element
	 *            Element
	 */
	public void verifyThatTheElementisEnabled(WebElement element) {
		Assert.assertTrue(element.isEnabled(), "Element is not enabled");
	}

	/**
	 * verify if the element is enabled.
	 * 
	 * @param element
	 *            Element
	 */
	public void verifyThatTheElementisDisabled(WebElement element) {
		Assert.assertFalse(element.isEnabled(), "Element is not disabled");
	}

	/**
	 * verify that the elements are enabled.
	 * 
	 * @param element
	 *            Element
	 */
	public void verifyThatTheElementsAreEnabled(List<WebElement> element) {
		Iterator<WebElement> itr = element.iterator();
		while (itr.hasNext()) {
			WebElement ele = itr.next();
			Assert.assertTrue(ele.isEnabled(), "Elements are not enabled");
		}
	}

	/**
	 * verify that the elements are enabled.
	 * 
	 * @param element
	 *            Element
	 */
	public void verifyThatTheElementsAreDisabled(List<WebElement> element) {
		Iterator<WebElement> itr = element.iterator();
		while (itr.hasNext()) {
			WebElement ele = itr.next();
			Assert.assertFalse(ele.isEnabled(), "Elements are not disabled");
		}
	}

	/**
	 * read data from excel from given file and sheet.
	 * 
	 * @param sheetName
	 *            sheetName
	 * @return object of data
	 * @throws Exception
	 *             Exception
	 */
	public Object[][] getEntireExcelSheetData(String excelNameWithFullPath, String sheetName) throws Exception {
		try {
			final String excleFilePath = excelNameWithFullPath;
			// utils.getConfigProperties("TestCaseFolder") + "//" + excelName;

			System.out.println("Excel Opened");
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			workbook = WorkbookFactory.create(new File(excleFilePath));

			// Getting the Sheet by sheet name
			Sheet sheet = workbook.getSheet(sheetName);

			// get the total number of rows and columns in the sheet
			int totalCols = sheet.getRow(0).getLastCellNum();
			int totalRows = sheet.getLastRowNum();

			tabArray = new String[totalRows][totalCols];

			int startRow = 1;
			int startCol = 0;
			int ci = 0;
			int cj = 0;

			// loop through the excel and get data
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					Row row = sheet.getRow(i);
					Cell cell = row.getCell(j);

					if (cell == null) {
						tabArray[ci][cj] = "";
					} else {
						switch (cell.getCellTypeEnum()) {
						case BOOLEAN:
							tabArray[ci][cj] = cell.getBooleanCellValue();
							break;
						case STRING:
							tabArray[ci][cj] = cell.getRichStringCellValue().getString();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								tabArray[ci][cj] = dateFormat.format(cell.getDateCellValue()).toString();
							} else {
								cell.setCellType(CellType.STRING);
								tabArray[ci][cj] = cell.getRichStringCellValue().getString();
							}
							break;
						case FORMULA:
							tabArray[ci][cj] = cell.getCellFormula();
							break;
						case BLANK:
							tabArray[ci][cj] = "";
							break;
						default:
							tabArray[ci][cj] = "";
						}
					}

				}
			}
		} catch (Exception e) {
			// Closing the workbook
			workbook.close();
			Assert.assertTrue(false, "Exception happend while reading excel : " + e.toString());
			System.out.println("Exception happend while reading excel : " + e.toString());
		} finally {
			workbook.close();
		}
		// return object
		return tabArray;
	}

	/**
	 * read data from excel from given file and sheet.
	 * 
	 * @param excelNameWithFullPath
	 *            excelNameWithFullPath
	 * @param sheetName
	 *            sheetName
	 * @return String Array with data
	 * @throws Exception
	 *             Exception
	 */
	public String[][] getEntireExcelSheetDataAsStringArray(String excelNameWithFullPath, String sheetName)
			throws Exception {
		String[][] rowArray = null;
		try {
			final String excleFilePath = excelNameWithFullPath;
			// utils.getConfigProperties("TestCaseFolder") + "//" + excelName;

			System.out.println("Excel Opened to get the Test case master file");
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			workbook = WorkbookFactory.create(new File(excleFilePath));

			// Getting the Sheet by sheet name
			Sheet sheet = workbook.getSheet(sheetName);

			// get the total number of rows and columns in the sheet
			int totalCols = sheet.getRow(0).getLastCellNum();
			int totalRows = sheet.getLastRowNum();

			rowArray = new String[totalRows][totalCols];

			int startRow = 1;
			int startCol = 0;
			int ci = 0;
			int cj = 0;

			// loop through the excel and get data
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					Row row = sheet.getRow(i);
					Cell cell = row.getCell(j);

					if (cell == null) {
						tabArray[ci][cj] = "";
					} else {
						switch (cell.getCellTypeEnum()) {

						// CANNOT READ BOOLEAN
						// case BOOLEAN:
						// RowArray[ci][cj] = cell.getBooleanCellValue();
						// break;

						case STRING:
							rowArray[ci][cj] = cell.getRichStringCellValue().getString();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								rowArray[ci][cj] = dateFormat.format(cell.getDateCellValue()).toString();
							} else {
								cell.setCellType(CellType.STRING);
								rowArray[ci][cj] = cell.getRichStringCellValue().getString();
							}
							break;
						case FORMULA:
							rowArray[ci][cj] = cell.getCellFormula();
							break;
						case BLANK:
							rowArray[ci][cj] = "";
							break;
						default:
							rowArray[ci][cj] = "";
						}
					}

				}
			}
		} catch (Exception e) {
			// Closing the workbook
			Assert.assertTrue(false, "Exception happend while reading excel : " + e.toString());
			System.out.println("Exception happend while reading excel : " + e.toString());
			workbook.close();

		} finally {
			workbook.close();
		}
		// return object
		return rowArray;
	}

	

}
