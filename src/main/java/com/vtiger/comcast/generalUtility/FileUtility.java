package com.vtiger.comcast.generalUtility;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Is is developed to read the data from commonData.properties file
 * @author Chinmaya
 *
 */
public class FileUtility {
	/**
	 * Is is used to read the data from commonData.properties files based on key which you can pass as an argument
	 * @param key
	 * @return
	 * @throws Throwable
	 */
	public String getPropertyKeyValue(String key) throws Throwable {
		FileInputStream fis = new FileInputStream("./data/commonData.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String value = pObj.getProperty(key);
		return value;
	}

}
