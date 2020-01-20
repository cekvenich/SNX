package org.apache.SNX.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.Map.Entry;

public class HNetUtil {

	public static final String OK = "OK";

	/**
	 * 
	 * @deprecated
	 */
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[0xFFFF];
		for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}

	/**
	 * @deprecated
	 */
	public static String mapToQs(Map<String, Object> map) {
		if (map == null || map.size() < 1)
			return "";
		StringBuilder string = new StringBuilder();

		if (map.size() > 0) {
			string.append("?");
		}

		for (Entry<String, Object> entry : map.entrySet()) {
			string.append(entry.getKey());
			string.append("=");
			string.append(entry.getValue());
			string.append("&");
		}

		return string.toString();
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

	public static String getPath(String url) {
		return url.split("\\?")[0];
	}

}
