package org.apache.SNX.db;

import java.util.concurrent.TimeUnit;

/**
 * Timer. Copied from WWW
 *
 */
public class Timer {
	long starts;

	public static Timer start() {
		return new Timer();
	}

	private Timer() {
		reset();
	}

	public Timer reset() {
		starts = System.currentTimeMillis();
		return this;
	}

	public long time() {
		long ends = System.currentTimeMillis();
		return ends - starts;
	}

	public long time(TimeUnit unit) {
		return unit.convert(time(), TimeUnit.MILLISECONDS);
	}
}