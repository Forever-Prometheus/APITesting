package com.green.APITesting.logutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

public class Log {
	/**
	 * 显示所有日志信息
	 * */
	public final static int Log_Level_All = 1;

	/**
	 * 输出尽可能多的信息以便追踪程序的运行
	 * */
	public static final int Log_Level_Trace = 2;

	/**
	 * 只输出正常的日志信息
	 * */
	public static final int Log_Level_Normal = 3;

	/**
	 * 只输出错误信息
	 * */
	public static final int Log_Level_Error = 4;
	/**
	 * 不输出的日志信息
	 * */
	public static final int Log_Level_None = 5;

	/**
	 * 是否向控制台输出日志信息	默认输出
	 * */
	public static boolean Log2Console = false;

	/**
	 * 是否向文件输出日志信息	默认不输出
	 * */
	public static boolean Log2File = false;

	/**
	 * 日志文件的路径	默认是工程根目录
	 * */
	public static String Log_Path = "." + File.separator + "log.txt";

	/**
	 * 输出日志信息的类型	默认不输出日志信息
	 * */
	protected static int Level = Log_Level_None;

	private Log() {

	}

	public static int getLevel() {
		return Level;
	}

	/**
	 * 设置输出日志信息的类型
	 * 
	 * @param level
	 *            输出日志信息的类型
	 * */
	public static void setLevel(int level) {
		if (level >= 1 && level <= 5)// 输出日志信息的类型在规定的范围内
			Level = level;
		else
			// 若不在范围内则输出正常的日志信息
			Level = Log_Level_Normal;
	}

	/**
	 * @return the logPath
	 */
	public static String getLogPath() {
		return Log_Path;
	}

	/**
	 * @param path
	 *            日志输入文件的路径
	 * */
	public static void setLogPath(String path) {
		Log_Path = path;
	}

	/**
	 * @return the islog2console
	 */
	public static boolean islog2console() {
		return Log2Console;
	}

	/**
	 * @return the islog2isfile
	 */
	public static boolean islog2file() {
		return Log2File;
	}

	/**
	 * @param log2Console
	 *            设置是否向控制台输出日志
	 */
	public static void setLog2Console(boolean log2Console) {
		Log2Console = log2Console;
	}

	/**
	 * @param log2isFile
	 *            设置是否向文件输出日志
	 */
	public static void setLog2File(boolean log2File) {
		Log2File = log2File;
	}

	/**
	 * 输出异常的日志信息
	 * 
	 * @param event
	 *            用来描述事件的字符串
	 * @param e
	 *            异常事件
	 * */
	public static void logException(String event, Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		ps.close();
		log(Log_Level_Error, event + e + ":" + baos);
		try {
			baos.close();
		} catch (IOException e1) {
			// 输出空
		}
	}

	/**
	 * 输出日志信息
	 * 
	 * @param level
	 *            输出日志信息的类型
	 * @param detail
	 *            事件的具体描述
	 * */
	public static void log(int level, String detail) {

		if (level == Log.Log_Level_None) {// 不输出日志则退出
			return;
		}
		if (level > Log.Level) {//
			return;
		}
		Date dt = new Date();// 设置日志时间
		String log = "[" + dt.toString() + "] [";// 日志信息中每行开头显示的信息
		switch (level) {
		case Log_Level_All:
			log += "ALL";
			break;
		case Log_Level_Trace:
			log += "Trace";
			break;
		case Log_Level_Normal:
			log += "Normal";
			break;
		case Log_Level_Error:
			log += "Error";
			break;
		}
		log += "][" + Thread.currentThread().getName() + "] " + detail;

		if (Log2Console) {
			System.out.println(log);
		}
		if (Log2File) {
			try {
				FileOutputStream fos = new FileOutputStream(Log_Path, true);
				PrintStream ps = new PrintStream(fos);
				ps.println(log);
				ps.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void simplelog(String detail){
		if (Log2Console) {
			System.out.println(detail);
		}
		if (Log2File) {
			try {
				FileOutputStream fos = new FileOutputStream(Log_Path, true);
				PrintStream ps = new PrintStream(fos);
				ps.println(detail);
				ps.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
