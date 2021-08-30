package pac1;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraCalenderPopUpTest {

	public static void main(String[] args) throws Throwable {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		FileInputStream fin = new FileInputStream("./data/testScriptData1.xlsx");
		Workbook wb = WorkbookFactory.create(fin);
		Sheet sheet = wb.getSheet("Sheet1");
		Row row = sheet.getRow(7);
		String dateMonthYear = row.getCell(1).getStringCellValue();
		
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get("https://www.yatra.com/");
		
		WebElement departure = driver.findElement(By.xpath("//span[text()='Departure Date']"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", departure);
		String dateXp = "//td[contains(@title,'"+dateMonthYear+"')]";
		driver.findElement(By.xpath(dateXp)).click();
		
	}

}
