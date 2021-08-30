package com.vtiger.comcast.generalUtility;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

import com.google.common.io.Files;

/**
 * This class contains WebDriver specific generic methods
 * @author Chinmaya
 *
 */
public class WebDriverUtility {
	
	/**
	 * This method is used to maximize the browser window
	 * @param driver
	 */
	public void maximize(WebDriver driver) {
		driver.manage().window().maximize();
	}
	
	/**
	 * This method wait for 20 seconds for page loading
	 * @param driver
	 */
	public void waitUntilPageLoad(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	/**
	 * This method wait for the element to be visible
	 * @param driver
	 * @param element
	 */
	public void waitForElementVisibility(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**
	 * This method wait for the title to be visible
	 * @param driver
	 * @param element
	 */
	public void waitForTitleVisibility(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.titleIs(driver.getTitle()));
	}
	
	/**
	 * This method wait for the element to be clicked, its custom wait created to avoid ElementNotInteractableException
	 * @param element
	 * @throws InterruptedException
	 */
	public void waitAndClick(WebElement element) throws InterruptedException {
		int count =0;
		while(count<20) {
			try {
				element.click();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				count++;
			}
		}
	}
	
	/**
	 * This method is used to get all options present in dropdown List
	 * @param element
	 * @param option
	 */
	public List<WebElement> selectOptions(WebElement element) {
		Select select = new Select(element);
		return select.getOptions();
	}
	
	/**
	 * This method enables user to handle dropdown using visible text
	 * @param element
	 * @param option
	 */
	public void select(WebElement element, String option) {
		Select select = new Select(element);
		select.selectByVisibleText(option);
	}
	
	/**
	 * This method enables user to handle dropdown using index
	 * @param element
	 * @param option
	 */
	public void select(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	/**
	 * This method is used to perform mouse hover action
	 * @param driver
	 * @param element
	 */
	public void mouseHover(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}
	
	/**
	 * This method is used to perform right click action on WebELement
	 * @param driver
	 * @param element
	 */
	public void rightClick(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.contextClick(element).perform();
	}
	
	/**
	 * This method helps to switch from one window to another
	 * @param driver
	 * @param partialWinTitle
	 */
	public void switchToWindow(WebDriver driver, String partialWinTitle) {
		Set<String> windowIds = driver.getWindowHandles();
		for(String wid: windowIds) {
			driver.switchTo().window(wid);
			if(driver.getTitle().contains(partialWinTitle)) {
				break;
			}
		}
	}
	
	/**
	 * This method is used to get the alert message
	 * @param driver
	 */
	public String getAlertText(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}
	
	/**
	 * This method is used to accept the alert popup
	 * @param driver
	 */
	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	
	/**
	 * This method is used to dismiss the alert popup
	 * @param driver
	 */
	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * This method is used for scrolling action in a webpage
	 * @param driver
	 * @param element
	 */
	public void scrollToWebElement(WebDriver driver, WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		int startY = element.getLocation().getY();
		jse.executeScript("arguments[0].scrollBy(0,"+startY+")", element);
	}
	
	/**
	 * This method is used to switch to frame using index
	 * @param driver
	 * @param index
	 */
	public void switchToFrame(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}
	
	/**
	 * This method is used to switch to frame using Web Element
	 * @param driver
	 * @param index
	 */
	public void switchToFrame(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}
	
	/**
	 * This method is used to switch to frame using id or name
	 * @param driver
	 * @param index
	 */
	public void switchToFrame(WebDriver driver, String idOrName) {
		driver.switchTo().frame(idOrName);
	}
	
	/**
	 * This method is used to take WebPage screenshot
	 * @param driver
	 * @param screenshotName
	 * @throws Throwable
	 */
	public void takePageScreenshot(WebDriver driver, String screenshotName) throws Throwable {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./screenshot/"+screenshotName+".PNG");
		Files.copy(srcFile, destFile);
	}
	
	/**
	 * This method is used to take WebElement screenshot
	 * @param driver
	 * @param screenshotName
	 * @throws Throwable
	 */
	public void takeElementScreenshot(WebDriver driver, WebElement element, String screenshotName) throws Throwable {
		File srcFile = element.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./screenshot/"+screenshotName+".PNG");
		Files.copy(srcFile, destFile);
	}
	
	/**
	 * This method is used to pass ENTER key in the browser
	 * @param driver
	 */
	public void passEnterKey(WebDriver driver) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).perform();
	}
	

}
