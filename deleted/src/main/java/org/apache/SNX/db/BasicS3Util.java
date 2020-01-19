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

/**
 * Tested w/ Vultr S3 Object store, but should work with all S3 compatible
 * object stores * ( AWS, GAE, Linode, DO, Wassabi, etc. )
 * 
 * It uses JSON as codec. It could be improved via anything that support
 * InputStream for example MessagePack or Snap
 */
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
			if (!item.isDir())
				lst.add(item.objectName());
		}
		return lst;
	}

	/**
	 * @deprecated
	 */
	public void put(String prefix, Map m) throws Throwable {
		String s = JACodecUtil.toJ(m);
		InputStream ins = JACodecUtil.toIns(s);
		String key = UUID.randomUUID().toString();
		_mclient.putObject(_bucket, prefix + "/" + key, ins, "application/octet-stream");
	}

	/**
	 * Auto generates guid, you only pass the prefix
	 */
	public void put(String prefix, List<Map<String, Object>> lst) throws Throwable {
		String s = JACodecUtil.toJ(lst);
		InputStream ins = JACodecUtil.toIns(s);
		String key = UUID.randomUUID().toString();
		key = key.replaceAll("[^a-zA-Z0-9]", "");// clean
		_mclient.putObject(_bucket, prefix + "/" + key, ins, "application/octet-stream");
	}

	public void remove(String prefixPlusKey) throws Throwable {
		_mclient.removeObject(_bucket, prefixPlusKey);
	}

	/**
	 * @deprecated
	 */
	public Map getAsMap(String prefixPlusKey) throws Throwable {
		InputStream ins = _mclient.getObject(_bucket, prefixPlusKey);
		String s = JACodecUtil.toStr(ins);

		return JACodecUtil.toMap(s);
	}

	/**
	 * Gets an InputStream stored as JSON: List
	 * 
	 */
	public List<Map<String, Object>> getAsList(String prefixPlusKey) throws Throwable {
		InputStream ins = _mclient.getObject(_bucket, prefixPlusKey);
		String s = JACodecUtil.toStr(ins);

		return JACodecUtil.toList(s);
	}

}// class
