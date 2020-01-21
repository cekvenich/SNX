package org.SNXa.http

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;

import org.apache.SNX.IRoute;
import org.apache.SNX.http.AbstracClassicHttpHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;

class HttpHandlerImpl(routes:Map[String, IRoute], docRoot:String )  extends AbstracClassicHttpHandler with HttpRequestHandler { 

  val LOG: Log = LogFactory.getLog(MethodHandles.lookup().lookupClass())

  var _routes: Map[String, IRoute] = routes
  
  var _docRoot: String =  docRoot
  
  @throws(classOf[HttpException])
  @throws(classOf[IOException])
  def handle(req: ClassicHttpRequest, resp: ClassicHttpResponse, context: HttpContext) {

  } //()

}//class