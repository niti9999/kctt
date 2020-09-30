package kctt;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class DynamicTestNG {
	
	@Test
	public void dynamicTest() {

		// Creating TestNG object
		TestNG myTestNG = new TestNG();

		// Creating XML Suite
		XmlSuite mySuite = new XmlSuite();

		// Setting the name for XML Suite
		mySuite.setName("My Suite");

		// Setting the XML Suite Parallel execution mode as Methods
		mySuite.setParallel(XmlSuite.ParallelMode.METHODS);

		// Adding the Listener class to the XML Suite
		//mySuite.addListener("test.Listener1");

		// Creating XML Test and add the Test to the Suite
		XmlTest myTest = new XmlTest(mySuite);

		// Setting the name for XML Test
		myTest.setName("My Test");

		// Setting the Preserve Order for XML Test to True to execute the Test
		// in Order
		myTest.setPreserveOrder("true");


		// Creating HashMap for setting the Parameters for the XML Test
//		HashMap<String, String> testngParams = new HashMap<String, String>();
//		testngParams.put("browserName", "Chrome");
//		testngParams.put("browserVersion", "81");
//		myTest.setParameters(testngParams);

		// Creating XML Class
		XmlClass myClass = new XmlClass("kctt.NewTest");

		// Creating XML Include in the form of ArrayList to add Multiple Methods
		// which i need to run from the Class
		List<XmlInclude> myMethods = new ArrayList<>();
		myMethods.add(new XmlInclude("tests"));
		//myMethods.add(new XmlInclude("method2"));

		// Adding the Methods selected to the my XML Class defined
		myClass.setIncludedMethods(myMethods);

		// Getting the Classes and adding it to the XML Test defined
		myTest.getClasses().add(myClass);

		// Creating XML Suite in the form of ArrayList and adding the list of
		// Suites defined
		List<XmlSuite> mySuitesList = new ArrayList<XmlSuite>();
		mySuitesList.add(mySuite);

		// Adding the XMLSuites selected to the TestNG defined
		myTestNG.setXmlSuites(mySuitesList);

		// Setting the execution Thread Count for Parallel Execution
		mySuite.setThreadCount(10);

		// Setting the Verbose Count for Console Logs
		mySuite.setVerbose(2);

		// Executing the TestNG created
		myTestNG.run();
	}
}