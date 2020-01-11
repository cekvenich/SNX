
import java.util.HashMap;
import java.util.Map;

import org.SNXex.db.DBS;
import org.SNXex.http.HttpHandler;
import org.SNXex.http.Route1;
import org.apache.SNX.SNX;
import org.apache.SNX.http.IRoute;
import org.apache.SNX.http.MainHttpServer;

public class SrvApp {

	static MainHttpServer _srv;
	static DBS _db;

	public static void main(String[] args) throws Throwable {

		new SNX();

		System.out.println("start " + System.getProperty("user.dir"));

		Map<String, IRoute> routes = new HashMap();
		// we add one route for our HTTP server
		IRoute r1 = new Route1(_db);
		routes.put(r1.getPath(), r1);
		// routes done
		String docRoot = System.getProperty("user.dir") + "/www";
		_srv = new MainHttpServer(new HttpHandler(routes, docRoot));
		_srv.start(8888);

		System.out.println("end");

	}

}
