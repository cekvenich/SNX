package org.SNXex.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.SNX.util.JACodecUtil;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;;

public class S3 {

	// arn:aws:s3:::snx01
	// wasabiUser	arn:aws:iam::100000048691:user/wasabiUser
	
	static MinioClient _mclient;

	static String _bucket = "snx01";

	public static void main(String[] args) throws Throwable {

		String server = "https://s3.wasabisys.com";
		String access_key = "LUYU20154833CM4L57P8";
		String secret_key = "oKZmf7b6qmXwydFDqvm7hEGHnYVObZlN9lMtEDLD";

		_mclient = new MinioClient(server, access_key, secret_key, "us-east-1");

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
		String s = JACodecUtil.toJ(m);
		InputStream ins = JACodecUtil.toIns(s);
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
		String s = JACodecUtil.toStr(ins);

		return JACodecUtil.toMap(s);
	}

}// class
