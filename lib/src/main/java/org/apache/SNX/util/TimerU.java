package org.apache.SNX.util;

import java.util.concurrent.TimeUnit;

/**
 * Timer. Copied from WWW
 *
 */
public class TimerU {
	long starts;

	public static TimerU start() {
		return new TimerU();
	}

	private TimerU() {
		reset();
	}

	public TimerU reset() {
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