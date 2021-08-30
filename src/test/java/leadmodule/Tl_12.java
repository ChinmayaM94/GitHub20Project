package leadmodule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tl_12 {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("./data/commonData.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String browser = pObj.getProperty("browser");
		String url = pObj.getProperty("url");
		String username = pObj.getProperty("username");
		String password = pObj.getProperty("password");
		
		FileInputStream fis1 = new FileInputStream("./data/testScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("Sheet1");
		Row row = sh.getRow(7);
		String partialTitleText = row.getCell(2).getStringCellValue();
		
		WebDriver driver = null;
		
		if(browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if(browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if(browser.equals("opera")) {
			driver = new OperaDriver();
		} else if(browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		//STEP 1
		driver.get(url);
		boolean isStepCorrect = true;
		//verifying step 1
		try {
			if(driver.findElement(By.id("submitButton")).isDisplayed()) {
				System.out.println("PASS:: Login page is displayed");
			}
		} catch(NoSuchElementException e) {
			System.out.println("FAIL:: Login page is not displayed");
			isStepCorrect = false;
		}
		//STEP 2
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();
		//verifying step 1
		if(driver.getTitle().contains(partialTitleText)) {
			System.out.println("PASS:: Home page is displayed");
		} else {
			System.out.println("FAIL:: Home page is not displayed");
			isStepCorrect = false;
		}
		
		//STEP 3
		driver.findElement(By.xpath("//td[@class]/a[text()='Leads']")).click();
		driver.findElement(By.xpath("//img[@title='Create Lead...']")).click();
		//verifying step 3
		try {
			if(driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).isDisplayed()) {
				System.out.println("PASS:: Creating New Lead page is displayed");
			} 
		} catch(NoSuchElementException e) {
			System.out.println("FAIL:: Creating New Lead page is not displayed");
			isStepCorrect = false;
		}
		
		//STEP 4
		String lastName = row.getCell(3).getStringCellValue();
		String company = row.getCell(4).getStringCellValue();
		String annualRev = row.getCell(5).getStringCellValue();
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.name("company")).sendKeys(company);
		WebElement annualRevenue = driver.findElement(By.name("annualrevenue"));
		annualRevenue.clear();
		annualRevenue.sendKeys(annualRev);
		
		driver.findElement(By.xpath("//input[contains(@title,'Save')]")).click();
		//verification
		String exptectedErrorMsg = row.getCell(6).getStringCellValue();
		Alert alert = driver.switchTo().alert();
		if(alert.getText().equals(exptectedErrorMsg)) {
			System.out.println("PASS:: "+exptectedErrorMsg+ " message is displayed");
		} else {
			System.out.println("FAIL:: "+exptectedErrorMsg+ " message is not displayed");
			isStepCorrect = false;
		}
		alert.accept();
		
		try {
			if(driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).isDisplayed()) {
				System.out.println("PASS:: New Lead is not created");
			} 
		} catch(NoSuchElementException e) {
			System.out.println("FAIL:: New Lead is created");
			isStepCorrect = false;
		}
		
		if(isStepCorrect) {
			row.createCell(7).setCellValue("PASS");
		} else {
			row.createCell(7).setCellValue("FAIL");
		}
		FileOutputStream fos = new FileOutputStream("./data/testScriptData.xlsx");
		wb.write(fos);
		wb.close();
		
		WebElement userImg = driver.findElement(By.xpath("//img[contains(@src,'user.PNG')]"));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(userImg).perform();
		
		driver.findElement(By.linkText("Sign Out")).click();
		
		driver.quit();
		
		

	}

}
