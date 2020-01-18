package org.SNXex.db;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;

import org.apache.SNX.util.JUtil;

public class S3 {

	// arn:aws:s3:::snx01

	static MinioClient _mclient;

	static String _bucket = "s3s";

	public static void main(String[] args) throws Throwable {

		String server = "https://s3.wasabisys.com";
		String access_key = "LUYU20154833CM4L57P8";
		String secret_key = "oKZmf7b6qmXwydFDqvm7hEGHnYVObZlN9lMtEDLD";

		_mclient = new MinioClient(server, access_key, secret_key);

		String prefix = "Monday/NY"; 
		Map row = new HashMap();
		row.put("a", "A");
		row.put("b", "B");
		put(prefix, row);
		// second row;
		put(prefix, row);
		
		
		System.out.println(find(prefix));
	}

	static protected List<String> find(String prefix) throws Throwable {
		Iterable<Result<Item>> iter = _mclient.listObjects(_bucket, prefix);
		List<String> lst = new ArrayList();
		for (Result<Item> result : iter) {
			Item item = result.get();
			lst.add(item.objectName());
		}

		return lst;
	}

	static protected int getKeyPosition(String prefixPlusKey) {
		return prefixPlusKey.lastIndexOf('/');
	}
	
	/**
	 * Auto generates they, you only pass the prefix
	 */
	static protected void put(String prefix, Map m) throws Throwable {
		String s = JUtil.toJ(m);
		InputStream ins = toIns(s);
		String key = "";
		_mclient.putObject(_bucket, prefix + "/" + key, ins, "application/octet-stream");
	}

	static protected void remove(String prefixPlusKey) throws Throwable {
		_mclient.removeObject(_bucket, prefixPlusKey);
	}

	/**
	 * Gets an InputStream stored as JSON: Map
	 */
	static protected Map getAsMap(String prefixPlusKey) throws Throwable {
		InputStream ins = _mclient.getObject(_bucket, prefixPlusKey);
		String s = toStr(ins);

		return JUtil.toMap(s);
	}

	static public String toStr(InputStream ins) {
		return new BufferedReader(new InputStreamReader(ins)).lines().collect(Collectors.joining("\n"));
	}

	static public InputStream toIns(String str) throws Throwable {
		return new ByteArrayInputStream(str.getBytes("UTF-8"));
	}// ()

}// class
