package org.apache.SNX.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JACodecUtil {
	static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	static public String toJ(List lst) throws Throwable {
		try {
			JSONArray list = new JSONArray();
			list.addAll(lst);
			return list.toJSONString();
		} catch (Throwable t) {
			LOG.warn(lst);
			throw t;
		}
	}

	static public String toJ(Map m) throws Throwable {
		try {
			JSONObject obj = new JSONObject();
			obj.putAll(m);
			return obj.toJSONString();
		} catch (Throwable t) {
			LOG.warn(m);
			throw t;
		}

	}

	static public Map toMap(String s) throws Throwable {
		try {
			JSONParser _parser = new JSONParser();
			Object obj = _parser.parse(s);
			return (JSONObject) obj;

		} catch (Throwable t) {
			LOG.warn(s);
			throw t;
		}
	}

	static public List toList(String s) throws Throwable {
		try {
			JSONParser _parser = new JSONParser();
			Object obj = _parser.parse(s);
			return (JSONArray) obj;

		} catch (Throwable t) {
			LOG.warn(s);
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

	static public String toStr(InputStream ins) throws IOException {
		return new String(ins.readAllBytes(), StandardCharsets.UTF_8);
	}

	static public InputStream toIns(String str) throws Throwable {
		return new ByteArrayInputStream(str.getBytes("UTF-8"));
	}// ()

}// class