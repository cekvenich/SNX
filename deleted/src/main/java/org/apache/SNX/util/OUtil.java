package org.apache.SNX.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Properties;

public class OUtil {

	public static final String DIR = System.getProperty("user.dir");
	public static Runtime RT = Runtime.getRuntime();

	public static Map loadProps(String fn) throws Throwable {
		InputStream input = new FileInputStream(fn);

		Map props = new Properties();
		((Properties) props).load(input);
		input.close();
		return props;
	}

	public static String trimStr(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
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
