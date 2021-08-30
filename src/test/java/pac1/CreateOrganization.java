package pac1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;

public class CreateOrganization {

	public static void main(String[] args) throws IOException {
		
		FileInputStream fis = new FileInputStream("./data/commonData.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String browser = pObj.getProperty("browser");
		String url = pObj.getProperty("url");
		String username = pObj.getProperty("username");
		String password = pObj.getProperty("password");
		String organizationName = pObj.getProperty("organizationName");
		int implicitTime = Integer.parseInt(pObj.getProperty("implicitTime"));
		
		WebDriver driver = null;
		
		if(browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if(browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if(browser.equals("opera")) {
			driver = new OperaDriver();
		} else if(browser.equals("edge")) {
			driver = new EdgeDriver();
		} else if(browser.equals("ie")) {
			driver = new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicitTime, TimeUnit.SECONDS);
		
		driver.get(url);
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();
		
		driver.findElement(By.xpath("//td[@class]/a[text()='Organizations']")).click();
		
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		driver.findElement(By.name("accountname")).sendKeys(organizationName);
		driver.findElement(By.xpath("//input[contains(@title,'Save')]")).click();
		
		String actualHeaderText = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		
		if(actualHeaderText.contains(organizationName)) {
			System.out.println("PASS:: Organization name in header is present");
		} else {
			System.out.println("FAIL:: Organization name in header is not present");
		}
		
		WebElement userImg = driver.findElement(By.xpath("//img[contains(@src,'user.PNG')]"));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(userImg).perform();
		
		driver.findElement(By.linkText("Sign Out")).click();
		
		//driver.quit();
	}

}
