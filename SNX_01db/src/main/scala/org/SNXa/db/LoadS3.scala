package org.SNXa.db

import java.lang.invoke.MethodHandles

import java.util.ArrayList

import java.util.HashMap

import java.util.List

import java.util.Map

import org.apache.SNX.SNX

import org.apache.SNX.db.BasicS3Util

import org.apache.SNX.util.TimerU

import org.apache.commons.logging.Log

import org.apache.commons.logging.LogFactory

import io.bloco.faker.Faker

class LoadS3 {

  var _s3: BasicS3Util = _

  var LOG: Log = LogFactory.getLog(MethodHandles.lookup().lookupClass())

  var _faker: Faker = new Faker()

  var prefix: String = "2020/01/23/APAC"

  def load(s3: BasicS3Util): Unit = {
    new SNX().getSNX
    _s3 = s3
    ins()
    val lst: List[String] = _s3.find(prefix)
    val key: String = lst.get(0)
    println(key)
    val rows: java.util.List[java.util.Map[String, Object]] = _s3.getAsList(key)
   
   
    val row: Map[_, _] = rows.get(0)
    println(row)
  }

  protected def ins(): Unit = {
    // 25*40* 1000 = 1 Million
    val mCount: Int = 25
    var i: Int = 0
    while (i <= mCount) {
      println()
      println("Loading a batch:")
      val t: TimerU = TimerU.start()
      _insBatch(40 * 1000)
      i = i + 1
      println()
      println(" in " + t.time())
    }
  }

  protected def _insBatch(count: Int): Unit = {
    var i: Int = 0
    val lst: java.util.List[java.util.Map[String, Object]] = new ArrayList()
    while (i <= count) {
      
      val row: java.util.Map[String, Object] = new HashMap()
      row.put("name",   _faker.name.nameWithMiddle())
      row.put("city",   _faker.address.city())
      row.put("ip",    _faker.internet.ipV4Address().toString)
      row.put("date",  _faker.date.backward().toGMTString())
      row.put("cc",    _faker.business.creditCardType())
      row.put("dept",  _faker.commerce.department())
      row.put("price", _faker.commerce.price())
      lst.add(row) 
      i = i + 1
      
    }
    _s3.put(prefix, lst)
  }//()

}






























