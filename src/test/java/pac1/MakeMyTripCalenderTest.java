package pac1;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakeMyTripCalenderTest {

	public static void main(String[] args) throws Throwable {
		
		FileInputStream fin = new FileInputStream("./data/testScriptData1.xlsx");
		Workbook wb = WorkbookFactory.create(fin);
		Sheet sheet = wb.getSheet("Sheet1");
		Row row = sheet.getRow(4);
		String date = row.getCell(1).getStringCellValue();
		String monthAndYear = row.getCell(2).getStringCellValue();
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get("https://www.makemytrip.com/");
		
		Actions actions = new Actions(driver);
		actions.click().perform();
		
		driver.findElement(By.xpath("//label[@for='departure']")).click();
		String dateXp = "//div[text()='"+monthAndYear+"']/ancestor::div[@class='DayPicker-Month']//p[text()='"+date+"']";
		
		for(;;) {
			try {
				driver.findElement(By.xpath(dateXp)).click();
				break;
			} catch (Exception e) {
				driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
			}
		}
		
		

	}

}
