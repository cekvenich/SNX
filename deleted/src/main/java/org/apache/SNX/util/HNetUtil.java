package org.apache.SNX.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

public class HNetUtil {

	public static final String OK = "OK";

	public static Map<String, String> qsToMap(final String uri_) {
		if (!uri_.contains("?"))
			return null;
		String uri = uri_.split("\\?")[1];
		List<NameValuePair> q = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
		Map<String, String> qs = q.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

		return qs;
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
