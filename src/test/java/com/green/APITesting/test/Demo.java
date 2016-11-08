package com.green.APITesting.test;

import org.testng.annotations.Test;

import com.green.APITesting.datautils.JsonUtils;
import com.green.APITesting.httputils.InitHttpConnect;

import net.sf.json.JSONObject;

import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterTest;

public class Demo {
	@Test
	public void demo() {
	}

	@BeforeTest
	public void beforeTest() {
		/*Map<String, String> map = new HashMap<>();
		map.put("password", "zuoran335");
		map.put("account", "86341949@qq.com");
		JSONObject data = JsonUtils.mapToJSON(map);
		String post = data.toString();*/
		
		JSONObject obj = new JSONObject();
        obj.element("password", "zuoran335");
        obj.element("account", "86341949@qq.com");
		
		InitHttpConnect con = new InitHttpConnect();
		con.cookie("https://portal.hiar.io/account/signin/", "account=86341949%40qq.com&password=zuoran335");
		
	}

	@AfterTest
	public void afterTest() {
	}

}
