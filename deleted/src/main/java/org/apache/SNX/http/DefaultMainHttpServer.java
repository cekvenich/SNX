package org.apache.SNX.http;

import java.lang.invoke.MethodHandles;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hc.core5.http.ConnectionClosedException;
import org.apache.hc.core5.http.ExceptionListener;
import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.TimeValue;

/**
 * Http Server based on Apache Core v5+. Needs a handler.
 */
public class DefaultMainHttpServer {

	static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	SocketConfig _socketConfig;

	HttpServer _server;

	HttpRequestHandler _handler;

	public DefaultMainHttpServer(HttpRequestHandler handler) {
		_handler = handler;
	}

	public void start(int port) throws Throwable {

		_socketConfig = SocketConfig.custom().setSoTimeout(120, TimeUnit.SECONDS).setTcpNoDelay(true).build();

		_server = ServerBootstrap.bootstrap().setListenerPort(port).setSocketConfig(_socketConfig)
				.setExceptionListener((ExceptionListener) new ExceptionListener() {

					@Override
					public void onError(final Exception ex) {
						LOG.warn(ex.toString() + " onError");
					}

					@Override
					public void onError(final HttpConnection conn, final Exception ex) {
						if (ex instanceof SocketTimeoutException) {
							LOG.info("Connection timed out");

						} else if (ex instanceof ConnectionClosedException) {
							LOG.info(ex.getMessage() + " Connection Closed");

						} else {

							LOG.warn(ex.toString() + " catchAll");
							ex.printStackTrace();
						}
					}// onError

				}).register("*", _handler).create();

		_server.start();

		System.out.println("Listening on port " + port);

		_server.awaitTermination(TimeValue.MAX_VALUE);

	}// ()

}// class
