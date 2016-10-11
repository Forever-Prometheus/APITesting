package com.green.APITesting.httputils;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class Cookies {
	private static CookieManager manager = new CookieManager();

	/**
	 *	处理cookies add到cookieManager中提供getcookies使用
	 * @param uri
	 * @param strcoo
     */
	public static void storecoo(URI uri,String strcoo) {
		
		//将规则改掉，接受所有的cookie
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		//保存这个定制的CookieManager
		CookieHandler.setDefault(manager);
		//接受HTTP请求的时候，得到和保存新的Cookie
		HttpCookie cookie = new HttpCookie("Cookie: ", strcoo);
		cookie.setMaxAge(6000);
		manager.getCookieStore().add(uri, cookie);
	}

	/**
	 * 获取到处理完成的cookies
	 * @return
     */
	public static HttpCookie getcookies(){
		
		HttpCookie res = null;
		//使用Cookie的时候
		//取出CookieStore
		CookieStore store = manager.getCookieStore();

		//得到所有的URI
		List<URI> uris = store.getURIs();
		for (URI ur : uris) {
			//筛选需要的URI
			//得到属于这个URI的所有Cookie
			List<HttpCookie> cookies = store.get(ur);
			for (HttpCookie coo : cookies) {
				res = coo;
			}
		}
		return res;
	}
}
