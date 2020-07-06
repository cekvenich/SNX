package com.example.handlers;

import java.util.Map;

import com.rpc.srv.IAuth;

public class ExAuth implements IAuth {

	@Override
	public String auth(String user, String pswd, Map ctx) {

		return "OK";
	}

}
