package kctt;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class test{
    WebDriver driver;
    WebDriverWait wait;

//    @BeforeMethod
//    public void setup() {
//        System.setProperty("webdriver.chrome.driver", "path to/chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.get("https://www.google.com/");
//        wait = new WebDriverWait(driver,50);
//    }
//
//    @Title("Title check")
//    @Description("Checking the title of the loaded page.")
//    @Test
//    public void searchTest(){
//        String title = driver.getTitle();
//      
//        assertEquals(title,"Google");
//      
//        System.out.println("Page Loaded");
//    }
//
//    @AfterMethod
//    public void teardown(){
//        driver.close();
//    }
}