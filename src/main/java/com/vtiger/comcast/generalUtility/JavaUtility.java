package com.vtiger.comcast.generalUtility;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Random;
/**
 * This class contains java specific generic libraries
 * @author Chinmaya
 *
 */
public class JavaUtility {
	/**
	 * It is used to generate the integer random number within the boundary of 0 to 10000
	 * @return intData
	 */
	public int getRandomNumber() {
		Random random = new Random();
		int randomNum = random.nextInt(10000);
		return randomNum;
	}
	
	/**
	 * It is used to get the current system date and time
	 * @return systemDateAndTime
	 */
	public String getSystemDate() {
		Date date = new Date();
		String systemDateAndTime = date.toString();
		return systemDateAndTime;
	}
	
	/**
	 * It is used to get the current system date with YYYY-MM-DD format
	 * @return finalFormat
	 */
	public String getSystemDate_YYYY_MM_DD() {
		Date date = new Date();
		String systemDateAndTime = date.toString();
		String[] arr = systemDateAndTime.split(" ");
		String YYYY = arr[5];
		int DD = date.getDate();
		int MM = date.getMonth();
		String finalFormat = YYYY+"-"+MM+"-"+DD;
		return finalFormat;
	}
	
	/**
	 * It is used to pass virtual key to OS
	 * @throws Throwable
	 */
	public void pressVirtualEventKey() throws Throwable {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

}
