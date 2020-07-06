package com.rpc.cli;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.codec.Codec;
import com.rpc.srv.IHandler;
import com.rutil.Util;

public class RPCCli {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected HttpClient client;
	protected String server = "localhost";
	protected int port = 8888;
	protected String protocol = "http";

	protected Codec C;

	public RPCCli() {
		client = HttpClient.newBuilder().build();
		C = new Codec();
	}

	public Map invoke(String route, String method, Map params, String screenOrModule) throws Throwable {

		String url = protocol + "://" + server + ":" + port + "/" + route;
		LOG.info(url);

		params.put(IHandler.ent, screenOrModule);
		params.put(IHandler.method, method);

		String ba = C.encrypt(params);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Origin", Util.getLocalHost())
				.POST(HttpRequest.BodyPublishers.ofString(ba)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200)
			throw new Exception("Error");

		String baRet = response.body();
		LOG.debug("LEN" + baRet.length());
		// crypto
		Map ret = C.decrypt(baRet);

		LOG.debug(ret.toString());

		return ret;
	}

}
