package com.green.APITesting.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		if (osStr.contains("linux") || osStr.contains("solaris") || osStr.contains("unix") || osStr.contains("mac")) {
			return "LINUX";
		}

		return "OTHER";
	}

	public static String readTxtFile(String filePath) {
		
		StringBuffer sb = null;
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				sb = new StringBuffer();
				InputStream is = new FileInputStream(file);// 获取输入流
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 包装流并且指定编码方式
				BufferedReader br = new BufferedReader(isr);

				String line = null;
				do {
					line = br.readLine();// 读取内容

					if (line != null && !line.equals("")) {
						line = line.trim();
						line = line.replaceAll("&nbsp;", "");
						sb.append(line);
					}
				} while (line != null);
				// 关闭流
				br.close();
				isr.close();
				is.close();
				
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return sb.toString();
	}
}
