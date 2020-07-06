package com.rpc.srv;

import java.util.Map;

public interface IAuth {

	public static final String FAIL = "FAIL";

	/*
	 * Rejects with 'FAIL' if not autheticated. Else returns some string saying what
	 * kind of authrizaion role. Eg: 'admin', or 'microsoft' would mean only for
	 * that company.
	 */
	public String auth(String user, String pswd, Map ctx);

}
