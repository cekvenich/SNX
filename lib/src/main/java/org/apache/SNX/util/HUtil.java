package org.apache.SNX.util;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

public class HUtil {

	public static final String OK = "OK";

	public static Map<String, String> getQS(final String uri_) {
		if (!uri_.contains("?"))
			return null;
		String uri = uri_.split("\\?")[1];
		List<NameValuePair> q = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
		Map<String, String> qs = q.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

		return qs;
	}

	public static String getPath(String url) {
		return url.split("\\?")[0];
	}

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

}
