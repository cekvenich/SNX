package com.example.handlers;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.codec.Codec;
import com.rpc.srv.AbstractRPCRouter;

public class RPCRouter extends AbstractRPCRouter implements HttpRequestHandler {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public RPCRouter(Codec c_) {
		super(c_);

		HANDLERS = new HashMap();

		HANDLERS.put("api", new HandlerOne());

		LOG.info(HANDLERS.get("api").toString());
	}

}// class
