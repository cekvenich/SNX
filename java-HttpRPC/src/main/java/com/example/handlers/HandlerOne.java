package com.example.handlers;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.srv.IAuth;
import com.rpc.srv.IHandler;

/**
 * 
 * ~ Business Layer
 *
 */
public class HandlerOne implements IHandler {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected static IAuth _auth;

	public HandlerOne() {
		_auth = new ExAuth();
	}

	@Override
	public Map handleRPC(final String method, final String scr, final Map params) throws Exception {

		String user = (String) params.get(IHandler.user);
		String pswd = (String) params.get(IHandler.pswd);

		String auth = _auth.auth(user, pswd, params);

		if (IAuth.FAIL == auth)
			throw new Exception("failed auth " + user);

		if ("multiply".equals(method)) {
			long a = (long) params.get("a");
			long b = (long) params.get("b");

			long c = multiply(a, b);
			Map resp = new HashMap();
			resp.put(IHandler.result, c);

			return resp;
		}

		if ("log".equals(method)) {
			handleLog(params);
			return null;
		}

		throw new Exception("handling error for " + method);

	}

	protected long multiply(long a, long b) {
		return a * b;
	}// ()

	protected void handleLog(Map params) {
		LOG.warn("LOG");
		LOG.warn(params.toString());
	}// ()

	@Override
	public IAuth getAuth() {
		return _auth;
	}

}// ()
