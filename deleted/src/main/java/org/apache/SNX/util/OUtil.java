package org.apache.SNX.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class OUtil {

	public static final String DIR = System.getProperty("user.dir");
	public static Runtime RT = Runtime.getRuntime();

	public static String trimStr(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	public static byte[] toBa8(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Convert to String UT8
	 *
	 * @param ba
	 * @return
	 */
	public static String toStr8(byte[] ba) {
		return new String(ba, StandardCharsets.UTF_8);
	}

	public static Map loadProps(String fn) throws Throwable {
		InputStream input = new FileInputStream(fn);

		Map props = new Properties();
		((Properties) props).load(input);
		input.close();
		return props;
	}

	protected static String _localHost;

	public static synchronized String getLocalHost() {
		if (_localHost != null)
			return _localHost;

		String h = null;
		try {
			final DatagramSocket socket = new DatagramSocket();

			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			h = socket.getLocalAddress().getHostAddress();
			socket.close();
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		_localHost = h;
		return _localHost;
	}

	public String getPID() {
		String pid = ManagementFactory.getRuntimeMXBean().getName();
		return pid;
	}

	/**
	 * Force GC
	 *
	 * @return
	 */
	public synchronized long fgc() {
		long ramB = RT.totalMemory() - RT.freeMemory();

		Object obj = new Object();
		WeakReference<Object> wref = new WeakReference<>(obj);
		obj = null;
		while (wref.get() != null) {
			try {
				Thread.sleep(0, 1);
			} catch (InterruptedException e) {
			}
			System.gc();
		}
		long ramA = RT.totalMemory() - RT.freeMemory();

		return ramB - ramA;
	}

}
