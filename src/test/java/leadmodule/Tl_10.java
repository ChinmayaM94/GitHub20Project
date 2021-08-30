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

public class Tl_10 {

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
		Row row = sh.getRow(1);
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
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.name("company")).sendKeys(company);
		driver.findElement(By.xpath("//input[@value='T']")).click();
		String groupDropdownOption = row.getCell(5).getStringCellValue();
		String[] arr = groupDropdownOption.split("\n");
		boolean isOptionPresent = false;
		Select select1 = new Select(driver.findElement(By.name("assigned_group_id")));
		String groupOption = select1.getFirstSelectedOption().getText();
		for(String str:arr) {
			if(groupOption.equals(str)) {
				isOptionPresent = true;
				break;
			}
		}
		if(isOptionPresent) {
			System.out.println("PASS:: "+groupOption+" is present in Group dropdown listbox");
		} else {
			System.out.println("FAIL:: Group dropdown listbox is empty");
			isStepCorrect = false;
		}
		
		String userDropdownOption = row.getCell(6).getStringCellValue();
		driver.findElement(By.xpath("//input[@value='U']")).click();
		Select select2 = new Select(driver.findElement(By.name("assigned_user_id")));
		String userOption = select2.getFirstSelectedOption().getText();
		
		if(userOption.contains(userDropdownOption)) {
			System.out.println("PASS:: "+userOption+" is present in User dropdown listbox");
		} else {
			System.out.println("FAIL:: "+userOption+" is not present in User dropdown listbox");
			isStepCorrect = false;
		}
		
		driver.findElement(By.xpath("//input[contains(@title,'Save')]")).click();
		
		String actualHeaderText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		
		if(actualHeaderText.contains(lastName)) {
			System.out.println("PASS:: New Lead is created");
		} else {
			System.out.println("FAIL:: New Lead is not created");
			isStepCorrect = false;
		}
		WebElement leadNo = driver.findElement(By.xpath("//td[text()='Lead No']/following-sibling::td"));
		if(!leadNo.getText().isBlank()) {
			System.out.println("PASS:: Unique Lead No is generated");
		} else {
			System.out.println("FAIL:: Unique Lead No is not generated");
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
		
		//STEP 5
		WebElement userImg = driver.findElement(By.xpath("//img[contains(@src,'user.PNG')]"));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(userImg).perform();
		driver.findElement(By.linkText("Sign Out")).click();
		
		driver.quit();
	}

}
