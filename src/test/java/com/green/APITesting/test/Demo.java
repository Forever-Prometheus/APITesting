package com.green.APITesting.test;

import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.green.APITesting.datautils.JsonUtils;
import com.green.APITesting.httputils.HttpConnect;
import com.green.APITesting.httputils.InitHttpConnect;

import junit.framework.Assert;

public class Demo {
	
	String token = null;

	@Test
	public void demo1() throws Exception {
		
		HttpConnect.token = this.token;

		HttpConnect hc = new HttpConnect();
		hc.testConnect("https://portal.hiar.io/api/collection/create?", "name=Demo1&description=test&appid=0", "CreateImageList");
		
		Map<String, Object> map = JsonUtils.strToMap(hc.buffer);
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
