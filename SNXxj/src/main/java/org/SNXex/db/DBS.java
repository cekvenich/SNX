package org.SNXex.db;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.SNX.db.AbstractDB;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.LockingMode;
import org.sqlite.SQLiteConfig.SynchronousMode;
import org.sqlite.SQLiteConfig.TempStore;

/**
 * Warper for SQL-ite
 */
public class DBS extends AbstractDB {

	static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	static Connection _conW;

	/**
	 * _db = new DB(5000, ":memory:"); // for memory DB (RAM) _db = new DBS(5000,
	 * "/home/db/sdb.db"); // or file path if using file
	 * 
	 * @param cacheSize eg 25000 ~ 100 meg in 4K pages
	 * @param db        :memory: for RAM or file path
	 */
	public DBS(int cacheSize, String db) throws Throwable {
		SQLiteConfig configW = new SQLiteConfig();
		configW.setCacheSize(cacheSize);
		configW.setBusyTimeout(120 * 1000);
		configW.setSynchronous(SynchronousMode.OFF);
		configW.setJournalMode(JournalMode.TRUNCATE);
		configW.setTempStore(TempStore.MEMORY);
		configW.enforceForeignKeys(false);
		configW.setReadUncommited(true);
		configW.setLockingMode(LockingMode.EXCLUSIVE);
		configW.setReadOnly(false);
		// memory db
		_conW = DriverManager.getConnection("jdbc:sqlite:" + db, configW.toProperties());
		_conW.setAutoCommit(false);

	}// ()

	/**
	 *  Make sure you finally commit the connection you get here
	 * @return Connection
	 */
	public Connection begin() throws Throwable {
		synchronized (_conW) {
			_conW.beginRequest();
			return _conW;
		}
	}

	/**
		Does the table exists?
	 */
	public boolean tableExists(DBS db, String table) {
		try {
			StringBuilder sql = new StringBuilder("SELECT name FROM sqlite_master WHERE type=\'table\' AND name=");
			sql.append(table);
			LOG.info(sql);
			Connection con = db.begin();
			List<Map<String, Object>> row = db.read(null, sql);
			con.commit();
			if (row.size() != 1)
				return false;
			return true;
		} catch (Throwable err) {
			LOG.debug(err);
			return false;
		}
	}

}
