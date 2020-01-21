package org.SNXa.http

import java.util.ArrayList

import java.util.HashMap

import java.util.List

import java.util.Map

import org.apache.SNX.IRoute

import org.apache.SNX.util.JACodecUtil

import Route1._

//remove if not needed
import scala.collection.JavaConversions._

object Route1 {

  var _thiz: IRoute = _

  var _db: AnyRef = _

}

class Route1 /**
  * @param db pass in DB
  */
(db: AnyRef)
    extends IRoute {

  def getPath(): String = "/API1"

  _db = db

  _thiz = this

  def getInst(): IRoute = _thiz

  /**
    * Will return JSON
    */
  def ret(qs: Map[_, _]): String = {
    // make some fake data to return. Should use DB select command
    val results: java.util.List[java.util.Map[String,Object]] = new ArrayList()
    
    val row1: Map[String, Object] = new HashMap()
    row1.put("Name", "Vic")
    row1.put("Title", "VPE")
    results.add(row1)
    val row2: Map[String, Object] = new HashMap()
    row2.put("Name", "Al")
    row2.put("Title", "CEO")
    results.add(row2)
    try JACodecUtil.toJ(results)
    catch {
      case e: Throwable => {
        e.printStackTrace()
        "Error"
      }

    }
  }

}