package org.SNXa.stress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.Callable;

import org.apache.SNX.util.TimerU;

public class FakeBrowser implements Callable<Object> {

	String _uri;

	long PAUSE_TIME = 2; // 'think time' pause;

	public FakeBrowser(String uri) {
		_uri = uri;
	}

	public Long call() throws Exception {
		TimerU t = TimerU.start();

		String resp = GET(_uri);

		long ms = t.time();

		try {
			Thread.sleep(PAUSE_TIME);
		} catch (Exception e) {
		}

		return ms;
	}

	protected String GET(String uri) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return response.body();
	}

}
