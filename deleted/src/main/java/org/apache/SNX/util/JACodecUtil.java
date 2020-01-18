package org.apache.SNX.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	/**
	 * 
	 * @deprecated
	 */
	public static byte[] toBA8(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Convert to String UT8
	 *
	 * @deprecated
	 */
	public static String toStr8(byte[] ba) {
		return new String(ba, StandardCharsets.UTF_8);
	}

	static public String toStr(InputStream ins) {
		return new BufferedReader(new InputStreamReader(ins)).lines().collect(Collectors.joining("\n"));
	}

	static public InputStream toIns(String str) throws Throwable {
		return new ByteArrayInputStream(str.getBytes("UTF-8"));
	}// ()

}// class