package org.SNXa.db

import java.lang.invoke.MethodHandles

import java.sql.Connection

import org.apache.SNX.util.TimerU

import org.apache.commons.logging.Log

import org.apache.commons.logging.LogFactory

import io.bloco.faker.Faker


class LoadFakeSDB(var _mdb: SDB) {
   var LOG: Log = LogFactory.getLog(MethodHandles.lookup().lookupClass())

  var _faker: Faker = new Faker()

  var _ins: StringBuilder = new StringBuilder(
    "INSERT INTO tab1(fullName, city, ip, dateOfPurch, cc, dept, price) VALUES(" +
      "?,?,? ,?,?,?,?" +
      ")")
 
  LOG.info("oh hi log")

  def loadDB(): Unit = {
    val createTab1: StringBuilder = new StringBuilder(
      "CREATE TABLE tab1( fullName VARCHAR(40), postcode VARCHAR(15), city VARCHAR(25), ip VARCHAR(20), " +
        "dateOfPurch DATE," +
        "cc VARCHAR(25), dept VARCHAR(40), price DECIMAL(10,2)" +
        ");")
    var con: Connection = null
    if (!_mdb.tableExists(_mdb, "tab1")) {
      // create schema
      con = _mdb.begin()
      _mdb.write(con, new java.lang.StringBuilder(createTab1))
      
      con.commit()
    }
    con = _mdb.begin()
    println(
      _mdb.read(con,  new java.lang.StringBuilder("SELECT COUNT(*) AS c FROM tab1")))
    con.commit()
    // 3 times a million = 3 million
    insert1MilsLoop(3)
  }

  def insert1MilsLoop(c: Int): Unit = {
    val count = c * 10
    var j: Int = 1
    while (j <= count) {
      val watch: TimerU = TimerU.start()
      // 100K
      System.out.print("outer loop " + j + ":")
      var i: Int = 1
      while (i <= 100) {
        insIn(1000) 
        i = i + 1
      }
      println(" +100K in " + watch.time())
      val con: Connection = _mdb.begin()
      println(
        _mdb.read(con,  new java.lang.StringBuilder("SELECT COUNT(*) AS cout FROM tab1")))
      con.commit() 
      j = j + 1
    }
  }

  protected def insIn(count: Int): Unit = {
// ~ 300-700 / sec m or 25K s
    val con: Connection = _mdb.begin()
    var i: Int = 1
    while (i <= count) {
      _mdb.write(
        con,
        new java.lang.StringBuilder(_ins),
        _faker.name.nameWithMiddle(),
        _faker.address.city(),
        _faker.internet.ipV4Address().toString,
        _faker.date.backward(),
        _faker.business.creditCardType(), // DECIMAL
        _faker.commerce.department().toString, // DECIMAL
        _faker.commerce.price()
      ) 
      i = i + 1
    }
    con.commit()
  }

}
