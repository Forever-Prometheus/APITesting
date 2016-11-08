package com.green.APITesting.datautils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author Dieson Zuo
 * @date Nov 4, 2016 5:07:53 PM
 */
public class JsonUtils {
	
	public static JSONObject mapToJSON(Map<String, String> map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;
	}
	
	public static JSONObject listToJSON(List<String> list) {
		JSONObject jsonObject = JSONObject.fromObject(list);
		return jsonObject;
	}
	
}
