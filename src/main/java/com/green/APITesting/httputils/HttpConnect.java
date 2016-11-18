package com.green.APITesting.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.green.APITesting.logutils.OutputFile;

public class HttpConnect {

	/**
	 * int timeout 超时时间默认6000ms
	 */
	public int timeout = 6000;
	/**
	 * boolean doinput 是否向服务器端发送数据 默认为true
	 */
	public boolean doinput = true;
	/**
	 * boolean dooutput 是否接收服务器端发送的数据 默认为true
	 */
	public boolean dooutput = true;
	/**
	 * The HttpURLConnection to connect the website.
	 */
	public HttpURLConnection hc = null;
	/**
	 * sendCoding String 发送请求的编码方式
	 */
	public String sendCoding = "UTF-8";
	/**
	 * Parsecode String 本地解析时的编码方式
	 */
	public static final String Parsecode = "UTF-8";

	public static String cookie = null;

	public static String token = null;

	/**
	 * String fileName 存储报文路径
	 */
	public static String fileName = null;

	public HttpConnect() {
	}

	public HttpConnect(String ec) {
		this.sendCoding = ec;
	}

	/**
	 * POST请求初始化
	 * 
	 * String str 请求地址
	 */
	public void initPOST(String str) throws Exception {

		URL url = new URL(str);
		HttpURLConnection.setFollowRedirects(false);
		hc = (HttpURLConnection) url.openConnection();
		hc.setConnectTimeout(timeout);
		hc.setDoInput(doinput);
		hc.setDoOutput(dooutput);
		hc.setUseCaches(false);
		hc.setRequestMethod("POST");
		hc.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
		hc.setDoOutput(true);
		hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		hc.setRequestProperty("Content-Language", "zh-cn");
		hc.setRequestProperty("Connection", "keep-alive");
		hc.setRequestProperty("Cache-Control", "no-cache");
		hc.setRequestProperty("token", HttpConnect.token);
		hc.setRequestProperty("Cookie", "Cookie: " + HttpConnect.cookie);

	}

	/**
	 * GET请求初始化
	 * 
	 * String str 请求地址
	 */
	public void initGET(String str, String param) throws Exception {

		String getUrl = str + "?" + param;
		URL url = new URL(getUrl);
		hc = (HttpURLConnection) url.openConnection();
		hc.setRequestMethod("GET");
		hc.setRequestProperty("accept", "*/*");
		hc.setRequestProperty("connection", "Keep-Alive");
		hc.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		hc.setRequestProperty("token", HttpConnect.token);
		hc.setRequestProperty("Cookie", "Cookie: " + HttpConnect.cookie);
		hc.connect();
	}

	/**
	 * 发送POST请求
	 * 
	 * @param postData
	 *            要发送的数据
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
	 */
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
			
			// 输出报文为html
			OutputFile fop = new OutputFile();
			String htmlPath = HttpConnect.fileName + ".html";
			fop.writeFile(htmlPath, sb.toString());
			// 输出报文为txt
			String contentType = hc.getContentType();
			if (contentType.contains("json")) {

				String txtPath = HttpConnect.fileName + ".txt";
				fop.writeFile(txtPath, sb.toString());
			}
			return sb.toString();
		} else
			return null;
	}

	public void testConnect(String url, String postdata, String path) throws IOException {

		HttpConnect.fileName = path;
		try {
			// HttpConnect初始化
			this.initPOST(url);
			// 设置POST请求数据
			this.sendPost(postdata);
			// 读取报文
			this.readData();

			this.killconnet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 断开HTTP连接
	 */
	public void killconnet() {

		hc.disconnect();
	}

}
