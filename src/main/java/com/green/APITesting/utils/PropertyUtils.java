package com.green.APITesting.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties utils
 * 
 * @author Dieson Zuo
 * 
 */
public class PropertyUtils {
	private Properties properties = new Properties();

	/**
	 * Load properties
	 * @param path
	 * @return
	 */
	public Properties loadProperties(String path) {
		try {
			InputStream fileInputStream = PropertyUtils.class.getResourceAsStream(path);
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}