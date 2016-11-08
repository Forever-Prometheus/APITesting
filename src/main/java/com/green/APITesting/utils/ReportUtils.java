package com.green.APITesting.utils;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.testng.Reporter;

public class ReportUtils {
	/**
	 * write log
	 * @param comm
	 */
	public void log(String comm) {
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Reporter.log("[" + time + "]" + comm);
		System.out.println("[" + time + "]" + comm);
	}
	
	/**
	 * write error log
	 * @param comm
	 */
	public void errorLog(String comm) {
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Reporter.log("<span style=\"color:#FF7F27\"><b>[" + time + "]  " + comm + "</b></span><br />");
		System.out.println("[" + time + "]" + comm);
	}
	
	public void screenLog(String comm) {
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Reporter.log(comm);
		System.out.println("[" + time + "]Screenshot: " + comm);
	}
}
