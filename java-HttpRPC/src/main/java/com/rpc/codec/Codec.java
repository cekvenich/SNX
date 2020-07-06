package com.rpc.codec;

import java.lang.invoke.MethodHandles;
import java.util.Base64;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Codec {

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	
	/**
	 * json, base64, AES
	 */
	public String encrypt(Map m) throws Exception {
		JSONObject js = new JSONObject(m);
		String s = js.toJSONString();

		String es = Base64.getEncoder().encodeToString(s.getBytes());
		return es;
	}

	/**
	 * json, base64, AES
	 */

	public Map decrypt(String cipherDataArg) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(cipherDataArg);

		String decodedString = new String(decodedBytes);

		Map ret = (Map) JSONValue.parse(decodedString);
		return ret;
	}
}
