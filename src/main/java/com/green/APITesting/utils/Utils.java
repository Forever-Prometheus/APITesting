package com.green.APITesting.utils;

/**
 * Utility class
 * 
 * @author Dieson Zuo
 * 
 */
public class Utils {

	/**
	 * Get system type
	 * 
	 * @return
	 */
	public static String getOs() {
		
		String osStr = System.getProperty("os.name");
		if (osStr.startsWith("win")) {
			return "WINDOWS";
		}
		if (osStr.contains("linux") || osStr.contains("solaris")
				|| osStr.contains("unix") || osStr.contains("mac")) {
			return "LINUX";
		}

		return "OTHER";
	}
	

}
