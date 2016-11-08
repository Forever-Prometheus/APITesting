package com.green.APITesting.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

/**
 * @author Dieson Zuo
 * @date Nov 8, 2016 3:45:19 PM
 */
public class Test {

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		obj.element("password", "zuoran335");
		obj.element("account", "86341949@qq.com");

		URL url = null;
		HttpURLConnection http = null;

		try {
			url = new URL("https://portal.hiar.io/account/signin");
			http = (HttpURLConnection) url.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(50000);// 设置连接超时
			// 如果在建立连接之前超时期满，则会引发一个
			// java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
			http.setReadTimeout(50000);// 设置读取超时
			// 如果在数据可读取之前超时期满，则会引发一个
			// java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
			http.setRequestMethod("POST");
			// http.setRequestProperty("Content-Type","text/xml;
			// charset=UTF-8");
			http.setRequestProperty("Accept","application/json, text/javascript, */*; q=0.01");
			http.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
			http.connect();

			OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
			osw.write("account=86341949%40qq.com&password=zuoran335");
			osw.flush();
			osw.close();
			String result = "";
			System.out.println(http.getResponseCode());
			System.out.println(http.getRequestMethod());
			if (http.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
				// result = "["+result+"]";
			}
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println("err");
		} finally {
			if (http != null)
				http.disconnect();
		}

	}
}
