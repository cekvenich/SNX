package org.apache.SNX.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

// todo: add pool
public class AbstractSDB {

	static QueryRunner _srun = new QueryRunner();

	/**
	 * 
	 * Read
	 */
	public List<Map<String, Object>> read(Connection con, StringBuilder sql, Object... args) throws Throwable {

		MapListHandler handler = new MapListHandler();
		List<Map<String, Object>> res = _srun.query(con, sql.toString(), handler, args);

		return res;

	}// ()

	/**
	 * Use for the bulk writes|inserts, needs a commit on con
	 */
	public synchronized void write(Connection con, StringBuilder sql, Object... args) throws Throwable {
		MapListHandler handler = new MapListHandler();
		List<Map<String, Object>> ret = _srun.insert(con, sql.toString(), handler, args); // it does all CRUD ops.
		if (ret != null && ret.size() > 0) {
			Map<String, Object> results = ret.get(0);
		}

	}// ()

}
