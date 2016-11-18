package com.green.APITesting.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.green.APITesting.datautils.JsonUtils;
import com.green.APITesting.httputils.HttpConnect;
import com.green.APITesting.httputils.InitHttpConnect;


public class Demo {
	
	String token = null;
	String path = System.getProperty("user.dir") + "\\HtmlData\\";

	@Test
	public void createImageList() throws Exception {
		
		String fileName = path + "CreateImageList";
		HttpConnect.token = this.token;
		HttpConnect hc = new HttpConnect();
		hc.testConnect("https://portal.hiar.io/api/collection/create?", "name=Demo1&description=test&appid=0", fileName);
		
		Map<String, Object> map = JsonUtils.txtToMap(fileName + ".html");
		String retCode = map.get("retCode").toString();
		Assert.assertEquals(retCode, "0");
	}

	@BeforeTest
	public void beforeTest() throws Exception {

		InitHttpConnect con = new InitHttpConnect();
		token = con.token("https://portal.hiar.io/account/signin/", "account=86341949%40qq.com&password=zuoran335");
	}

	@AfterTest
	public void afterTest() {
	}

}
