package com.green.APITesting.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnect {
	
	/**
	 * int timeout 超时时间默认6000ms
	 * */
	public int timeout = 6000;
	/**
	 * boolean doinput 	是否向服务器端发送数据	默认为true
	 * */
	public boolean doinput = true;
	/**
	 * boolean dooutput	是否接收服务器端发送的数据	默认为true
	 * */
	public boolean dooutput = true;
	/**
	 * The HttpURLConnection to connect the website.
	 * */
	public HttpURLConnection hc = null;
	/**
	 * sendCoding String 发送请求的编码方式
	 * */
	public String sendCoding = "UTF-8";
	/**
	 * Parsecode String 本地解析时的编码方式
	 * */
	public static final String Parsecode = "UTF-8";
	/**
	 * 
	 * */
	public static String cookie = null;
	
	public HttpConnect() {
	}

	public HttpConnect(String ec) {
		this.sendCoding = ec;
	}

	/**
	 * @throws Exception
	 * 
	 * */
	public void initCon(String str) throws Exception {
		
		URL url = new URL(str);
		hc = (HttpURLConnection) url.openConnection();
		hc.setConnectTimeout(timeout);
		hc.setDoInput(doinput);
		hc.setDoOutput(dooutput);
		hc.setUseCaches(false);
		hc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		HttpURLConnection.setFollowRedirects(false);
		hc.setRequestProperty("Cookie","Cookie: " + HttpConnect.cookie); 
	}

	/**
	 * 发送POST请求
	 * @param postData	要发送的数据
	 * @throws Exception
     */
	public void sendPost(String postData) throws Exception {
		
		// String send = URLEncoder.encode(postdata, MHttpConnect.Dataencoding);
		OutputStream os = hc.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, this.sendCoding);
		osw.write(postData);
		osw.flush();
		osw.close();
		os.close();
	}
	
	/**
	 * 读取数据
	 * 
	 * @return String 读取的内容
	 * */
	public String readData() throws IOException {
		
		int code = hc.getResponseCode();
		StringBuffer sb = null;
		if (code == HttpURLConnection.HTTP_OK) {
			sb = new StringBuffer();
			InputStream is = hc.getInputStream();// 获取输入流
			InputStreamReader isr = new InputStreamReader(is, HttpConnect.Parsecode);// 包装流并且指定编码方式
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
			return sb.toString();
		} else
			return null;
	}

	/**
	 * 断开HTTP连接
	 */
	public void killconnet() {
		
		hc.disconnect();
	}
	
}
