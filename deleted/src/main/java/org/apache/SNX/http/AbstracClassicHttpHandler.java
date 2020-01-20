package org.apache.SNX.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.EndpointDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.apache.hc.core5.net.URLEncodedUtils;

// https://hc.apache.org/httpcomponents-core-ga/tutorial/pdf/httpcore-tutorial.pdf

/**
 * Called by HTTP Server
 * 
 */
public abstract class AbstracClassicHttpHandler {

	protected void originNotAllowedX(final ClassicHttpResponse response, HttpContext context) throws IOException {
		response.setCode(HttpStatus.SC_FORBIDDEN);
		response.setEntity(null);
		response.close();
	}// ()

	protected Map<String, String> qsToMap(final String uri_) {
		if (!uri_.contains("?"))
			return null;
		String uri = uri_.split("\\?")[1];
		List<NameValuePair> q = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
		Map<String, String> qs = q.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

		return qs;
	}

	protected String getPath(HttpRequest req) {
		String PATH = req.getPath();
		int ppos = PATH.indexOf('?');
		if (ppos < 0)
			ppos = PATH.length();
		PATH = PATH.substring(0, ppos);
		return PATH;
	}
	
	protected Map<String, String> getParams(HttpRequest req) {
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
		
		return params;
	}

	protected Map<String, String> nvp2Map(List<NameValuePair> qsl) {
		Map m = new HashMap();
		for (NameValuePair el : qsl)
			m.put(el.getName(), el.getValue());
		return m;
	}

	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	protected String getDate() {
		Date date = new Date(System.currentTimeMillis());
		// Conversion
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String text = sdf.format(date);
		return text;
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

	protected void serveAFile(File file, ClassicHttpResponse response, HttpContext context) {
		response.setHeader("Cache-Control", "public, max-age=" + 60 * 60 + 1 + ", s-max-age=" + 60 * 60);// edge cache

		HttpCoreContext coreContext = HttpCoreContext.adapt(context);
		EndpointDetails endpoint = coreContext.getEndpointDetails();
		response.setCode(HttpStatus.SC_OK);
		FileEntity body = new FileEntity(file, ContentType.create("text/html", (Charset) null));
		response.setEntity(body);
		// System.out.println(endpoint + ": serving file " + file.getPath());
	}

}// class
