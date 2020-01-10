package org.rpc.base;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;

public abstract class AbstractHTTPRouter {

    static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	protected void originNotAllowedX(final ClassicHttpResponse response, HttpContext context) throws IOException {
		response.setCode(HttpStatus.SC_FORBIDDEN);
		response.setEntity(null);
		response.close();
	}// ()

	
	protected Map<String, String> nvp2Map(List<NameValuePair> qsl) {
		Map m = new HashMap();
		for (NameValuePair el : qsl)
			m.put(el.getName(), el.getValue());
		return m;
	}
	
	protected void good(final ClassicHttpResponse response, StringEntity body) throws IOException {
		response.setCode(HttpStatus.SC_OK);
		response.setHeader("Access-Control-Allow-Origin", "*");

		response.setEntity(body);
		response.close();
	}// ()

	protected void err(final ClassicHttpResponse response, StringEntity err) throws IOException {
		response.setCode(HttpStatus.SC_SERVER_ERROR);
		response.setHeader("Access-Control-Allow-Origin", "*");

		response.setEntity(err);
		response.close();
	}// ()

	
}// class
