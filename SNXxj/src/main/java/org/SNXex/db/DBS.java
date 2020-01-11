package org.SNXex.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.SNX.db.AbstractDB;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.LockingMode;
import org.sqlite.SQLiteConfig.SynchronousMode;
import org.sqlite.SQLiteConfig.TempStore;

/**
 * Warper for SQL-ite
 */
public class DBS extends AbstractDB {

	static Connection _conW;

	/**
		_db = new DB(5000, ":memory:"); // for memory DB (RAM)
		_db = new DBS(5000, "/home/db/sdb.db"); // or file path if using file
	 * 
	 * @param cacheSize eg 25000 ~ 100 meg in 4K pages
	 * @param db :memory: for RAM or file path
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
		_conW = DriverManager.getConnection("jdbc:sqlite:"+db, configW.toProperties());
		_conW.setAutoCommit(false);

	}// ()

	public Connection begin() throws Throwable {
		synchronized (_conW) {
			_conW.beginRequest();
			return _conW;
		}
	}

}
