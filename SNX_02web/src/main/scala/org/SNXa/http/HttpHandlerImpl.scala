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

class HttpHandlerImpl(routes: Map[String, IRoute], docRoot: String) extends AbstracClassicHttpHandler with HttpRequestHandler {

  val LOG: Log = LogFactory.getLog(MethodHandles.lookup().lookupClass())

  var _routes: Map[String, IRoute] = routes

  var _docRoot: String = docRoot

  @throws(classOf[HttpException])
  @throws(classOf[IOException])
  def handle(req: ClassicHttpRequest, resp: ClassicHttpResponse, context: HttpContext) {

    // LOG.info(req.getHeader("Origin").toString())
    resp.setHeader("x-intu-ts", this.getDate())
    try {
      var PATH: String = getPath(req)

      // check if File in docRoot, else serve an SSR Route
      var file: File = new File(this._docRoot + PATH)
      if (file.exists() && !file.isDirectory()) {
        // we have a file to serve
        serveAFile(file, resp, context)
        LOG.info(".")
        return
      }

      // API cache
      resp.setHeader("Cache-Control", "public, max-age=" + 1 + ", s-max-age=" + 1); // edge cache f

      // API not found
      if (!_routes.containsKey(PATH)) {
        var outgoingEntity: StringEntity = new StringEntity("no such resource " + PATH)
        err(resp, outgoingEntity)
        return
      }

      // good response
      // /////////////////////////////////////////////////////////////////////////////////////////////
      var r = _routes.get("/API1")
      var params = getParams(req)
      var ret = r.ret(params)
      var outgoingEntity = new StringEntity(ret)
      good(resp, outgoingEntity)
      // end good response

    } catch {
      case e: Throwable =>
        LOG.error("Handler:" + e.toString())

        var outgoingEntity = new StringEntity("ERROR")
        err(resp, outgoingEntity)

    } // try

  } //()

} //class