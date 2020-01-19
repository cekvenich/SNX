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

	static public String toJ(List lst) throws Throwable {
		try {
			JSONArray list = new JSONArray();
			list.addAll(lst);
			return list.toJSONString();
		} catch (Throwable t) {
			System.err.println(lst);
			throw t;
		}
	}

	static public String toJ(Map m) throws Throwable {
		try {
			JSONObject obj = new JSONObject();
			obj.putAll(m);
			return obj.toJSONString();
		} catch (Throwable t) {
			System.err.println(m);
			throw t;
		}

	}

	static public Map toMap(String s) throws Throwable {
		try {
			JSONParser _parser = new JSONParser();
			Object obj = _parser.parse(s);
			return (JSONObject) obj;

		} catch (Throwable t) {
			System.err.println(s);
			throw t;
		}
	}

	static public List toList(String s) throws Throwable {
		try {
			JSONParser _parser = new JSONParser();
			Object obj = _parser.parse(s);
			return (JSONArray) obj;

		} catch (Throwable t) {
			System.err.println(s);
			throw t;
		}
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