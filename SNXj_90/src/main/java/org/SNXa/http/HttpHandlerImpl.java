package org.SNXa.http;

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

public class HttpHandlerImpl extends AbstracClassicHttpHandler implements HttpRequestHandler {

	 Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	 Map<String, IRoute> _routes;

	String _docRoot;

	public HttpHandlerImpl(Map<String, IRoute> routes, String docRoot) {
		_routes = routes;
		_docRoot = docRoot;
		LOG.info(_docRoot);
	}

	@Override
	public void handle(ClassicHttpRequest req, ClassicHttpResponse resp, HttpContext context)
			throws HttpException, IOException {

		try {
			// LOG.info(req.getHeader("Origin").toString());
			resp.setHeader("x-intu-ts", this.getDate());

			String PATH = getPath(req);
			// check if File in docRoot, else server a SSR Route
			 File file = new File(this._docRoot + PATH);

			if (file.exists() && !file.isDirectory()) {
				// we have a file to serve
				serveAFile(file, resp, context);
				LOG.info(".");
				return;
			}
			// end File in docRoot

			// API cache
			resp.setHeader("Cache-Control", "public, max-age=" + 1 + ", s-max-age=" + 1);// edge cache f
			// start qs
			Map<String, String> params = getParams(req);

			// end qs
			LOG.info("qs=" + params);

			if (!_routes.containsKey(PATH)) {
				StringEntity outgoingEntity = new StringEntity("no such resource " + PATH);
				err(resp, outgoingEntity);
				return;
			}
			// good response
			// /////////////////////////////////////////////////////////////////////////////////////////////
			// Chose route based on path. Be aware that maybe no route.
			IRoute r = _routes.get("/API1");
			String ret = r.ret(params);

			// end good response
			// /////////////////////////////////////////////////////////////////////////////////////////////
			 StringEntity outgoingEntity = new StringEntity(ret);

			good(resp, outgoingEntity);

		} catch (Exception e) {
			LOG.error("Handler:" + e.toString());

			// err response
			 StringEntity outgoingEntity = new StringEntity("ERROR");
			err(resp, outgoingEntity);
		} // try

	}// ()

}// class
