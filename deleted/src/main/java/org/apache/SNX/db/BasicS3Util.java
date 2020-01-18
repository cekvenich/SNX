package org.apache.SNX.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.SNX.util.JACodecUtil;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;;

public class BasicS3Util {

	protected MinioClient _mclient;

	protected String _bucket;

	public BasicS3Util(String server, String access, String secret, String bucket) throws Throwable {
		_bucket = bucket;
		_mclient = new MinioClient(server, access, secret);
	}

	protected int getKeyPosition(String prefixPlusKey) {
		return prefixPlusKey.lastIndexOf('/');
	}

	public List<String> find(String prefix) throws Throwable {
		Iterable<Result<Item>> iter = _mclient.listObjects(_bucket, prefix, true);
		List<String> lst = new ArrayList();
		for (Result<Item> result : iter) {
			Item item = result.get();
			if(!item.isDir())
				lst.add(item.objectName());
		}

		return lst;
	}

	/**
	 * Auto generates they, you only pass the prefix
	 */
	public void put(String prefix, Map m) throws Throwable {
		String s = JACodecUtil.toJ(m);
		InputStream ins = JACodecUtil.toIns(s);
		String key = UUID.randomUUID().toString();
		_mclient.putObject(_bucket, prefix + "/" + key, ins, "application/octet-stream");
	}

	public void remove(String prefixPlusKey) throws Throwable {
		_mclient.removeObject(_bucket, prefixPlusKey);
	}

	/**
	 * Gets an InputStream stored as JSON: Map
	 */
	public Map getAsMap(String prefixPlusKey) throws Throwable {
		InputStream ins = _mclient.getObject(_bucket, prefixPlusKey);
		String s = JACodecUtil.toStr(ins);

		return JACodecUtil.toMap(s);
	}

}// class
