package com.green.APITesting.datautils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.green.APITesting.utils.Utils;

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
	
	public static Map<String, Object> txtToMap(String filePath) {
		
		String str = Utils.readTxtFile(filePath);
		return JsonUtils.strToMap(str);
	}

	public static Map<String, Object> strToMap(String str) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(str);
		Iterator<?> it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = jsonObject.get(key).toString();
			map.put(key, value);
		}
		return map;
	}
}
