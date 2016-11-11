package com.green.APITesting.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Dieson Zuo
 * @date Oct 11, 2016 3:21:37 PM
 */
public class InitHttpConnect {

	/**
	 * String Base url
	 */
	private static final String baseurl = "https://portal.hiar.io/account/signin/";
	/**
	 * sendCoding String 发送请求的编码方式
	 */
	public String sendCoding = "UTF-8";
	/**
	 * The HttpURLConnection to connect the website.
	 */
	public HttpURLConnection hc = null;
	/**
	 * Parsecode String 本地解析时的编码方式
	 */
	public static final String Parsecode = "UTF-8";

	public void initCon(String str) throws Exception {

		URL url = null;
		if (str != null && !str.equals("")) {
			url = new URL(str);
		} else {
			url = new URL(InitHttpConnect.baseurl);
		}

		HttpURLConnection.setFollowRedirects(true);
		hc = (HttpURLConnection) url.openConnection();
		hc.setDoOutput(true);
		hc.setDoInput(true);
		hc.setRequestMethod("POST");
		hc.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
		hc.setDoOutput(true);
		hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		hc.setRequestProperty("Content-Language", "zh-cn");

		hc.setRequestProperty("Connection", "keep-alive");
		hc.setRequestProperty("Cache-Control", "no-cache");
	}

	/**
	 * @param postdata
	 *            String 要发送的数据
	 * @throws Exception
	 */
	public void sendPost(String postdata) throws Exception {

		OutputStream os = hc.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, this.sendCoding);
		osw.write(postdata);
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
			InputStreamReader isr = new InputStreamReader(is, InitHttpConnect.Parsecode);// 包装流并且指定编码方式
			BufferedReader br = new BufferedReader(isr);

			String line = null;
			do {
				line = br.readLine();// 读取内容
				if (line != null && !line.equals("")) {
					sb.append(line);
				}
			} while (line != null);
			// 关闭流
			br.close();
			isr.close();
			is.close();
			System.out.println(sb.toString());
			return sb.toString();
		} else
			return null;
	}

	public String getCookie() {

		String cookieskey = "Set-Cookie";
		Map<String, List<String>> maps = hc.getHeaderFields();
		List<String> coolist = maps.get(cookieskey);
		Iterator<String> it = coolist.iterator();
		StringBuffer sbu = new StringBuffer();
		sbu.append("eos_style_cookie=default; ");
		while (it.hasNext()) {
			sbu.append(it.next() + " ");
		}
		System.out.println(sbu.toString());
		return sbu.toString();
	}

	public String getResponse(String key) {

		Map<String, List<String>> maps = hc.getHeaderFields();
		List<String> coolist = maps.get(key);
		Iterator<String> it = coolist.iterator();
		StringBuffer sbu = new StringBuffer();
		sbu.append(key + ":");
		while (it.hasNext()) {
			sbu.append(it.next() + " ");
		}
		System.out.println(sbu.toString());
		return sbu.toString();
	}

	/**
	 * 获取cookie 除去空格
	 * 
	 * @param url
	 * @param postData
	 * @return
	 */
	public String cookie(String URL, String data) {

		String res = null;
		try {

			this.initCon(URL);
			this.sendPost(data);
			res = this.getCookie();
			res = res.trim();
			this.readData();
			this.killconnet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 关闭连接
	 */
	public void killconnet() {
		hc.disconnect();

	}

}
