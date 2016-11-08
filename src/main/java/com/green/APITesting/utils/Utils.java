package com.green.APITesting.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Utility class
 * 
 * @author Dieson Zuo
 * 
 */
public class Utils {

	/**
	 * Get execution cmd result
	 * 
	 * @param script
	 * @return The execution result
	 */
	public static ArrayList<String> getExeCmd(String script) {
		BufferedReader br = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			Process p = exeShell(script);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get execution terminal result
	 * 
	 * @param script
	 * @return
	 */
	public static ArrayList<String> getExeTerminal(String script) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Process process = exeShell(script);

			InputStreamReader ir = new InputStreamReader(
					process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line;
			while ((line = input.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * execution shell
	 * 
	 * @param script
	 */
	public static Process exeShell(String script) {
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return process;
	}


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

	/**
	 * The string of ' replace ''
	 * 
	 * @param str
	 * @return
	 */
	public static String singleReplace(String str) {
		if (str.contains("'")) {
			return str.replaceAll("'", "''");
		} else {
			return str;
		}
	}

	/**
	 * Datetime format switch to English
	 * 
	 * @param str
	 * @return
	 */
	public static String datetimeToEnglish(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		Date date = null;
		String dateString = "";

		try {
			date = format.parse(str);
			dateString = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateString;
	}

	/**
	 * Get the expired time
	 * @param time
	 * @return
	 */
	public static int getExpiredTime(String time) {
		try {
			if (time == null || time.equals("")) {
				return -1;
			} else if (time.equals("00000000")) {
				return Integer.MAX_VALUE;
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				double dayc = (dateFormat.parse(time).getTime() - System
						.currentTimeMillis()) / (double) (1000 * 60 * 60 * 24);
				int day = (int) dayc;
				if (dayc > 0) {
					day = day + 1;
				}
				return day;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Integer.MAX_VALUE;
	}

}
