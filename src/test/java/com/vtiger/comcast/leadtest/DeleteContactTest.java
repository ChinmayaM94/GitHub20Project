package com.vtiger.comcast.leadtest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.vtiger.comcast.generalUtility.BaseClass;
import com.vtiger.comcast.pomrepositorylib.ContactInfoPage;
import com.vtiger.comcast.pomrepositorylib.ContactsPage;
import com.vtiger.comcast.pomrepositorylib.CreateNewContactPage;
import com.vtiger.comcast.pomrepositorylib.HomePage;

public class DeleteContactTest extends BaseClass {
	
	@Test
	public void deleteContactTest() throws Throwable {
		
		/*test script data*/
		String expectedHomeTitle = eLib.getDataFromExcel("ContactsModule", 1, 2);
		String lastName = eLib.getDataFromExcel("ContactsModule", 1, 3);
		
		wLib.waitForTitleVisibility(driver);
		HomePage homePage = new HomePage(driver);
		if(driver.getTitle().contains(expectedHomeTitle)) {
			System.out.println("PASS:: Home page is displayed");
		} else {
			System.out.println("FAIL:: Home page is not displayed");
		}
		
		/*Click on Contacts link*/
		homePage.getContactsLink().click();
		
		ContactsPage contactsPage = new ContactsPage(driver);
		if(contactsPage.getContactsHeader().isDisplayed()) {
			System.out.println("PASS:: Contacts page is displayed");
		} else {
			System.out.println("FAIL:: Contacts page is not displayed");
		}
		
		/*precondition:: atleast two contact should be avialble*/
		List<String> contactIdList = new ArrayList<String> ();
		for(int i=1; i<=2; i++) {
		contactsPage.getCreateContactImg().click();
		CreateNewContactPage createContact = new CreateNewContactPage(driver);
		createContact.createContact(lastName+randomNum);
		ContactInfoPage contactInfo = new ContactInfoPage(driver);
		contactIdList.add(contactInfo.getUniqueContactId().getText().trim());
		}
		
		/*Select multiple contacts and go for mass delete*/
		homePage.getContactsLink().click();
		contactsPage.selectContacts(contactIdList);
		contactsPage.getMassDeleteBtn().click();
		jLib.pressVirtualEventKey();
		Thread.sleep(2000);
		if(!contactsPage.isContactPresent(contactIdList)) {
			System.out.println("PASS:: selected contacts are deleted");
		} else {
			System.out.println("FAIL:: selected contacts are not deleted");
		}
		
	}

}
