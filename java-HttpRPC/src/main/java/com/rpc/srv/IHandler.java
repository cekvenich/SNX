package com.rpc.srv;

import java.util.Map;

/**
 * 
 * HTTP-RPC+ + is for routing help and auth help
 *
 */
public interface IHandler {

	public IAuth getAuth();

	public static final String user = "user";
	public static final String pswd = "pswd";
	public static final String token = "token";

	public static final String method = "method";
	/**
	 * screenOrModule
	 */
	public static final String ent = "ent";

	/**
	 * Method takes a Map of params, else throw Exception with the error;
	 */
	public Map handleRPC(final String method, final String screenOrModule, final Map params) throws Exception;

	public static final String result = "result";

}
