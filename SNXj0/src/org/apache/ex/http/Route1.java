package org.apache.ex.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.SNX.http.IRoute;
import org.apache.ex.db.DB;
import org.json.simple.JSONArray;

public class Route1 implements IRoute {
	public String getPath() {
		return "/API1";
	}

	static IRoute _thiz;

	/**
	 * @param db pass in DB
	 */
	public Route1(DB db) {
		_mdb = db;
		_thiz = this;
	}

	public IRoute getInst() {
		return _thiz;
	}

	static DB _mdb;

	/**
	 * Will return JSON
	 */
	public String ret(Map qs) {

		// make some fake data to return. Or we can use a DB select command.
		List<Map<String, Object>> results = new ArrayList();
		Map row1 = new HashMap();
		row1.put("Name", "Vic");
		row1.put("Title", "VPE");
		results.add(row1);

		Map row2 = new HashMap();
		row2.put("Name", "Al");
		row2.put("Title", "CEO");
		results.add(row2);

		return JSONArray.toJSONString(results);
	}

}
