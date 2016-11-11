package com.green.APITesting.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.green.APITesting.datautils.JsonUtils;
import com.green.APITesting.httputils.HttpConnect;
import com.green.APITesting.httputils.InitHttpConnect;
import com.green.APITesting.logutils.OutputFile;

import net.sf.json.JSONObject;

public class Demo {
	String path = System.getProperty("user.dir") + "\\HtmlData\\";
	String format = ".html";

	@Test
	public void demo() throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("Content-Disposition: form-data; name='name'", "Demo1");
		map.put("Content-Disposition: form-data; name='description'", "test");
		map.put("Content-Disposition: form-data; name='data_type'", "0");
		map.put("Content-Disposition: form-data; name='icon'; filename='PushToGit.png'Content-Type: image/png",
				"<file>");
		map.put("Content-Disposition: form-data; name='token'", "c17a34aa90cba4e9f4d0b35b827790ff");

		JSONObject data = JsonUtils.mapToJSON(map);
		String post = data.toString();
		
		HttpConnect hc = new HttpConnect();
		String buffer = null;
		String filename = path + "Demo" + format;
		try {
			// HttpConnect初始化
			hc.initPOST("https://portal.hiar.io/api/application/create");
			// 设置POST请求数据
			hc.sendPost(post);
			buffer = hc.readData();
			// 读取报文
			hc.killconnet();

			OutputFile fop = new OutputFile();
			fop.writeFile(filename, buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*HttpConnect hc = new HttpConnect();
		hc.initPOST("https://portal.hiar.io/api/application/create");
		hc.sendPost(post);
		hc.readData();
		hc.killconnet();*/
	}

	@BeforeTest
	public void beforeTest() {
		/*
		 * Map<String, String> map = new HashMap<>(); map.put("password",
		 * "zuoran335"); map.put("account", "86341949@qq.com"); JSONObject data
		 * = JsonUtils.mapToJSON(map); String post = data.toString();
		 */

		InitHttpConnect con = new InitHttpConnect();
		con.cookie("https://portal.hiar.io/account/signin/", "account=86341949%40qq.com&password=zuoran335");
	}

	@AfterTest
	public void afterTest() {
	}

}
