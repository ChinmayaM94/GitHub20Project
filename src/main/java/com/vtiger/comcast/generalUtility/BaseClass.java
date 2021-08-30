package com.vtiger.comcast.generalUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.vtiger.comcast.pomrepositorylib.HomePage;
import com.vtiger.comcast.pomrepositorylib.LoginPage;

public class BaseClass {
	public WebDriver driver;

	/* Object creation for lib */
	public JavaUtility jLib = new JavaUtility();
	public WebDriverUtility wLib = new WebDriverUtility();
	public FileUtility fLib = new FileUtility();
	public ExcelUtility eLib = new ExcelUtility();
	public int randomNum = jLib.getRandomNumber();

	@BeforeSuite(groups = { "smokeTest", "regressionTest" })
	public void configBS() {

	}

	/*
	 * @Parameters("BROWSER")
	 * 
	 * @BeforeClass(groups = {"smokeTest", "regressionTest"}) public void
	 * configBC(String BROWSER) throws Throwable { //String BROWSER =
	 * fLib.getPropertyKeyValue("browser"); String URL =
	 * fLib.getPropertyKeyValue("url"); if(BROWSER.equals("chrome")) { driver = new
	 * ChromeDriver(); } else if(BROWSER.equals("firefox")) { driver = new
	 * FirefoxDriver(); } else if(BROWSER.equals("opera")) { driver = new
	 * OperaDriver(); }
	 * 
	 * wLib.maximize(driver); wLib.waitUntilPageLoad(driver); Navigate to
	 * application driver.get(URL); }
	 */

	@BeforeClass(groups = { "smokeTest", "regressionTest" })
	public void configBC() throws Throwable {
		String BROWSER = fLib.getPropertyKeyValue("browser");
		String URL = fLib.getPropertyKeyValue("url");
		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("opera")) {
			driver = new OperaDriver();
		}

		wLib.maximize(driver);
		wLib.waitUntilPageLoad(driver);
		/* Navigate to application */
		driver.get(URL);
	}

	@BeforeMethod(groups = { "smokeTest", "regressionTest" })
	public void configBM() throws Throwable {
		String USERNAME = fLib.getPropertyKeyValue("username");
		String PASSWORD = fLib.getPropertyKeyValue("password");
		/* Login to application */
		LoginPage loginPage = new LoginPage(driver);
		
		if(loginPage.getLoginBtn().isDisplayed()) {
			System.out.println("PASS:: Login page is displayed");
		} else {
			System.out.println("FAIL:: Login page is not displayed");
		}
		loginPage.loginToApp(USERNAME, PASSWORD);
	}

	@AfterMethod(groups = { "smokeTest", "regressionTest" })
	public void configAM() {
		// logout
		new HomePage(driver).signOut();
	}

	@AfterClass(groups = { "smokeTest", "regressionTest" })
	public void configAC() {
		driver.quit();
	}

	@AfterSuite(groups = { "smokeTest", "regressionTest" })
	public void configAS() {

	}

}
