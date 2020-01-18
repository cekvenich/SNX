package org.apache.SNX.util;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JACodecUtil {
	static JSONParser _parser = new JSONParser();

	static public String toJ(List lst) {
		JSONArray list = new JSONArray();
		list.addAll(lst);
		return list.toJSONString();
	}

	static public String toJ(Map m) {
		JSONObject obj = new JSONObject();
		obj.putAll(m);
		return obj.toJSONString();
	}

	static public Map toMap(String s) throws ParseException {
		Object obj = _parser.parse(s);
		return (JSONObject) obj;
	}

	static public List toLst(String s) throws ParseException {
		Object obj = _parser.parse(s);
		return (JSONArray) obj;
	}

	public static byte[] toBA8(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Convert to String UT8
	 *
	 * @param ba
	 * @return
	 */
	public static String toStr8(byte[] ba) {
		return new String(ba, StandardCharsets.UTF_8);
	}

}// class