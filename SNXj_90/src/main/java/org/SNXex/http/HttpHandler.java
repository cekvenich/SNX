package org.SNXex.http;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.SNX.http.AbstractHTTPHandler;
import org.apache.SNX.IRoute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.net.URLEncodedUtils;

public class HttpHandler extends AbstractHTTPHandler implements HttpRequestHandler {

	static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	static Map<String, IRoute> _routes;

	String _docRoot;

	public HttpHandler(Map<String, IRoute> routes, String docRoot) {
		_routes = routes;
		_docRoot = docRoot;
		LOG.info(_docRoot);
	}

	@Override
	public void handle(ClassicHttpRequest req, ClassicHttpResponse resp, HttpContext context)
			throws HttpException, IOException {

		try {
			// LOG.info(req.getHeader("Origin").toString());
			resp.setHeader("x-intu-ts", this.getDate() );

			String PATH = req.getPath();
			int ppos = PATH.indexOf('?');
			if (ppos < 0)
				ppos = PATH.length();
			PATH = PATH.substring(0, ppos);
			LOG.info("route path=" + PATH);

			// check if File in docRoot, else server a SSR Route
			final File file = new File(this._docRoot + PATH);

			if (file.exists() && !file.isDirectory()) {
				// we have a file to serve
				serveAFile(file, resp, context);
				LOG.info(".");
				return;
			}
			// end File in docRoot
			
			// API cache
		    resp.setHeader("Cache-Control", "public, max-age="+1+", s-max-age="+1);// edge cache for browser and CDN
			// start qs
			String url = req.getRequestUri();
			int upos = url.indexOf('?');
			Map<String, String> params;
			if (upos < 0) {
				params = new HashMap();
			} else {
				String qs = url.substring(++upos);

				List<NameValuePair> qsl = URLEncodedUtils.parse(qs, StandardCharsets.UTF_8);
				params = nvp2Map(qsl);
			}
			// end qs
			LOG.info("qs=" + params);

			if (!_routes.containsKey(PATH)) {
				final StringEntity outgoingEntity = new StringEntity("no such resource " + PATH);
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
			final StringEntity outgoingEntity = new StringEntity(ret);

			good(resp, outgoingEntity);

		} catch (Exception e) {
			LOG.error("Handler:"+ e.toString());

			// err response
			// /////////////////////////////////////////////////////////////////////////////////////////////
			final StringEntity outgoingEntity = new StringEntity("ERROR");
			err(resp, outgoingEntity);
		} // try

	}// ()

}// class
