package pac1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateOrganization1 {

	public static void main(String[] args) throws Exception {
		
		FileInputStream fis = new FileInputStream("./data/commonData.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String browser = pObj.getProperty("browser");
		String url = pObj.getProperty("url");
		String username = pObj.getProperty("username");
		String password = pObj.getProperty("password");
		
		FileInputStream fis1 = new FileInputStream("./data/testScriptData1.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		Sheet sh = wb.getSheet("Sheet1");
		Row row = sh.getRow(1);
		String orgName = row.getCell(2).getStringCellValue();
		
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
		driver.get(url);
		
		//STEP 1: Login
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();
		
		//STEP 2: Navigate to Organizations module
		driver.findElement(By.xpath("//td[@class]/a[text()='Organizations']")).click();
		
		//STEP 3: Click on Create Organization button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		
		//STEP 4: Enter all details and create new organization
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[contains(@title,'Save')]")).click();
		
		String actualHeaderText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		String status = "";
		
		//STEP 5: Verify Organization name in header of the msg
		if(actualHeaderText.contains(orgName)) {
			status = "PASS";
		} else {
			status = "FAIL";
		}
		System.out.println(status);
		
		row.createCell(5).setCellValue(status);
		FileOutputStream fos = new FileOutputStream("./data/testScriptData1.xlsx");
		wb.write(fos);
		wb.close();
		
		//STEP 6: logout
		WebElement userImg = driver.findElement(By.xpath("//img[contains(@src,'user.PNG')]"));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(userImg).perform();
		
		driver.findElement(By.linkText("Sign Out")).click();
		
		driver.quit();
	}

}
