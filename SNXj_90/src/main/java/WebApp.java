
import java.util.HashMap;
import java.util.Map;

import org.SNXa.http.HttpHandler;
import org.SNXa.http.Route1;
import org.apache.SNX.IRoute;
import org.apache.SNX.SNX;
import org.apache.SNX.http.DefaultMainHttpServer;

public class WebApp {

	static DefaultMainHttpServer _srv;

	public static void main(String[] args) throws Throwable {

		new SNX();

		System.out.println("start " + System.getProperty("user.dir"));

		Map<String, IRoute> routes = new HashMap();
		// we add one route for our HTTP server
		IRoute r1 = new Route1(null);
		routes.put(r1.getPath(), r1);
		// routes done
		String docRoot = System.getProperty("user.dir") + "/www";
		_srv = new DefaultMainHttpServer(new HttpHandler(routes, docRoot));
		_srv.start(8888);

		System.out.println("end");

	}

}
