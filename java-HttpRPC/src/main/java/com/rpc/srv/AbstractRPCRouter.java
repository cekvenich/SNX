package com.rpc.srv;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.codec.Codec;

// https://hc.apache.org/httpcomponents-core-ga/tutorial/pdf/httpcore-tutorial.pdf
public class AbstractRPCRouter implements HttpRequestHandler {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected Codec C;

	protected Map<String, IHandler> HANDLERS;

	public AbstractRPCRouter(Codec c_) {
		this.C = c_;
	}

	@Override
	public void handle(final ClassicHttpRequest req, final ClassicHttpResponse resp, final HttpContext context)
			throws HttpException, IOException {

		HttpCoreContext ctx = (HttpCoreContext) context;
		LOG.info(ctx.getEndpointDetails().getRemoteAddress().toString());

		String host = "UNKNOWN";
		if (req.getHeader("Origin") != null)
			host = req.getHeader("Origin").toString();
		LOG.warn(host);

		resp.addHeader("Access-Control-Allow-Origin", "*");

		String path = req.getPath().substring(1);
		;
		LOG.info(path); // based on path: call a different handler
		LOG.info(req.getHeaders().toString());

		if (req.getEntity() == null) {// must be CORS
			corsAllowed(resp, context);
			return;
		}
		try {
			String body = EntityUtils.toString(req.getEntity());

			// crypto
			Map params = C.decrypt(body);

			String method = (String) params.get(IHandler.method);
			String ent = (String) params.get(IHandler.ent);

			// handler
			IHandler handler = HANDLERS.get(path);
			if (handler == null)
				throw new Exception("handler not found: " + path);

			Map m = handler.handleRPC(method, ent, params);
			LOG.info(m.toString());

			// good response
			// /////////////////////////////////////////////////////////////////////////////////////////////
			// crypto
			String baRet = C.encrypt(m);

			final StringEntity outgoingEntity = new StringEntity(baRet, ContentType.TEXT_PLAIN);
			resp.addHeader("Content-Type", "text/plain");

			good(resp, outgoingEntity);

		} catch (Exception e) {
			LOG.warn(e.toString(), e);

			// err response
			// /////////////////////////////////////////////////////////////////////////////////////////////
			final StringEntity outgoingEntity = new StringEntity("ERROR ");
			err(resp, outgoingEntity);
		} // try

	}// ()

	protected void corsAllowed(final ClassicHttpResponse response, HttpContext context) throws IOException {
		response.addHeader("Access-Control-Allow-Methods", "POST");
		response.addHeader("Access-Control-Max-Age", 86400);
		response.addHeader("Access-Control-Allow-Headers", "*");

		response.setCode(HttpStatus.SC_OK);
		response.setEntity(null);
		response.close();
	}// ()

	protected void originNotAllowedX(final ClassicHttpResponse response, HttpContext context) throws IOException {
		response.setCode(HttpStatus.SC_FORBIDDEN);
		response.setEntity(null);
		response.close();
	}// ()

	protected void good(final ClassicHttpResponse response, StringEntity body) throws IOException {
		response.setCode(HttpStatus.SC_OK);

		response.setEntity(body);
		LOG.debug("LEN " + response.getEntity().getContentLength());
		response.close();
	}// ()

	protected void err(final ClassicHttpResponse response, StringEntity err) throws IOException {
		response.setCode(HttpStatus.SC_SERVER_ERROR);

		response.setEntity(err);
		response.close();
	}// ()

}// class
