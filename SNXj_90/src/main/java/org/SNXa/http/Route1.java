package org.SNXa.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.SNX.IRoute;
import org.apache.SNX.util.JACodecUtil;

public class Route1 implements IRoute {
	public String getPath() {
		return "/API1";
	}

	static IRoute _thiz;

	/**
	 * @param db pass in DB
	 */
	public Route1(Object db) {
		_db = db;
		_thiz = this;
	}

	public IRoute getInst() {
		return _thiz;
	}

	static Object _db;

	/**
	 * Will return JSON
	 */
	public String ret(Map qs) {

		// make some fake data to return. Should use DB select command
		List<Map<String, Object>> results = new ArrayList();
		Map row1 = new HashMap();
		row1.put("Name", "Vic");
		row1.put("Title", "VPE");
		results.add(row1);

		Map row2 = new HashMap();
		row2.put("Name", "Al");
		row2.put("Title", "CEO");
		results.add(row2);

		try {
			return JACodecUtil.toJ(results);
		} catch (Throwable e) {

			e.printStackTrace();

			return "Error";
		}
	}

}
