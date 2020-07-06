package com.rpc.srv;

import java.lang.invoke.MethodHandles;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.hc.core5.http.ConnectionClosedException;
import org.apache.hc.core5.http.ExceptionListener;
import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRPCMainServer {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected SocketConfig _socketConfig;

	protected HttpServer _server;
	protected HttpRequestHandler _router;

	public HttpRPCMainServer(HttpRequestHandler router) {
		_router = router;
	}

	public void start(int port) throws Exception {

		_socketConfig = SocketConfig.custom().setSoTimeout(60, TimeUnit.SECONDS).setTcpNoDelay(true).build();

		_server = ServerBootstrap.bootstrap().setListenerPort(port).setSocketConfig(_socketConfig)
				.setExceptionListener(new ExceptionListener() {

					@Override
					public void onError(final Exception ex) {
						LOG.warn(ex.toString(), ex);
						ex.printStackTrace();
					}

					@Override
					public void onError(final HttpConnection conn, final Exception ex) {
						if (ex instanceof SocketTimeoutException) {
							LOG.info("Connection timed out");

						} else if (ex instanceof ConnectionClosedException) {
							LOG.info("Closed " + ex.getMessage());

						} else {

							LOG.warn(ex.toString(), ex);
						}
					}// onError

				}).register("*", _router).create();

		_server.start();

		LOG.info("Listening on port " + port);
		System.out.println("Listening on port " + port);

		_server.awaitTermination(TimeValue.MAX_VALUE);

	}// ()

}// class
