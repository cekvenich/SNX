package org.apache.SNX.http;

import java.util.Map;

/**
 * Controller and Action. Called by Handler
 */
public interface IRoute {

	/**
	 * @return http path
	 */
	String getPath();

	/**
	 * A Route should be a singleton
	 */
	IRoute getInst();

	/**
	 * Take in http args and return HTTP, or JSON, or such
	 */
	String ret(Map qs);
}
