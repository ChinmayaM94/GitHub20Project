package com.vtiger.comcast.pomrepositorylib;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactsPage {
	WebDriver driver;
	
	public ContactsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//img[@alt='Create Contact...']")
	private WebElement createContactImg;
	
	@FindBy(xpath = "//a[@class='hdrLink']")
	private WebElement contactsHeader;
	
	@FindBy(xpath = "//input[@value='Delete']")
	private WebElement massDeleteBtn;
	
	@FindBy(xpath = "//tr")
	private WebElement parentCheckBoxTag;
	
	@FindBy(xpath = "//img[@src='themes/images/next_disabled.gif']")
	private WebElement nextBtn;
	
	@FindBy(xpath = "//span[@vtfieldname='contact_no']/..")
	private List<WebElement> allContactsList;
	
	public List<WebElement> getAllContactsList() {
		return allContactsList;
	}

	public WebElement getNextBtn() {
		return nextBtn;
	}

	public WebElement getPageNumEdt() {
		return pageNumEdt;
	}

	@FindBy(name = "pagenum")
	private WebElement pageNumEdt;

	public WebElement getParentCheckBoxTag() {
		return parentCheckBoxTag;
	}

	public WebElement getMassDeleteBtn() {
		return massDeleteBtn;
	}

	public WebElement getCreateContactImg() {
		return createContactImg;
	}
	
	public WebElement getContactsHeader() {
		return contactsHeader;
	}
	
	public void selectContacts(List<String> contactIdList) {
		for(String str:contactIdList) {
			try {
			String xpathCb = "//td[text()='"+str+" ']/preceding-sibling::td/input[@name='selected_id']";
			parentCheckBoxTag.findElement(By.xpath(xpathCb)).click();
			} catch(Exception e) {
				nextBtn.click();
			}
		}
	}
	
	public boolean isContactPresent(List<String> contactIdList) {
		int count=1;
		List<String> allContactsIds= new ArrayList<String>();
		
		for(;;) {
			if(Integer.parseInt(pageNumEdt.getAttribute("value"))==count) {
				for(WebElement ele:allContactsList) {
					for(String id:contactIdList) {
						if(ele.getText().equals(id)) {
							return true;
						}
					}
				}
				count++;
				nextBtn.click();
			} else {
				return false;
			}
			
		}
	}
	
}
