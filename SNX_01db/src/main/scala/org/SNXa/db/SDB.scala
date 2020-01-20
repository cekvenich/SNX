package org.SNXa.db

import java.lang.invoke.MethodHandles

import java.sql.Connection

import java.sql.DriverManager

import java.util.List

import java.util.Map

import org.apache.SNX.db.AbstractSDB

import org.apache.commons.logging.Log

import org.apache.commons.logging.LogFactory

import org.sqlite.SQLiteConfig

import org.sqlite.SQLiteConfig.JournalMode

import org.sqlite.SQLiteConfig.LockingMode

import org.sqlite.SQLiteConfig.SynchronousMode

import org.sqlite.SQLiteConfig.TempStore



/**
  * Warper for SQL-ite
  */
class SDB /**
  * _db = new DB(5000, ":memory:"); // for memory DB (RAM) _db = new DBS(5000,
  * "/home/db/sdb.db"); // or file path if using file
  *
  * @param cacheSize eg 25000 ~ 100 meg in 4K pages
  * @param db        :memory: for RAM or file path
  */
(cacheSize: Int, db: String)
    extends AbstractSDB {

   var LOG: Log = LogFactory.getLog(MethodHandles.lookup().lookupClass())

  var _conW: Connection = _
    
  val configW: SQLiteConfig = new SQLiteConfig()
  configW.setCacheSize(cacheSize)
  configW.setBusyTimeout(120 * 1000)
  configW.setSynchronous(SynchronousMode.OFF)
  configW.setJournalMode(JournalMode.TRUNCATE)
  configW.setTempStore(TempStore.MEMORY)
  configW.enforceForeignKeys(false)
  configW.setReadUncommited(true)
  configW.setLockingMode(LockingMode.EXCLUSIVE)
  configW.setReadOnly(false)

// memory db
  _conW =
    DriverManager.getConnection("jdbc:sqlite:" + db, configW.toProperties())

  _conW.setAutoCommit(false)

  /**
    *  Make sure you finally commit the connection you get here
    * @return Connection
    */
  def begin(): Connection =  {
    _conW.beginRequest()
    _conW
  }

  /**
 Does the table exists?
    */
  def tableExists(db: SDB, table: String): Boolean =
    try {
      val sql: StringBuilder = new StringBuilder(
        "SELECT name FROM sqlite_master WHERE type='table' AND name='")
      sql.append(table)
      sql.append("'")
      LOG.info(sql)
      val con: Connection = db.begin()
      val rows: java.util.List[java.util.Map[String, Object]] = db.read(con, new java.lang.StringBuilder(sql))
      con.commit()
      LOG.info(rows)
      if (rows.size != 1) false
      true
    } catch {
      case err: Throwable => {
        LOG.info(err)
        false
      }

    }

}