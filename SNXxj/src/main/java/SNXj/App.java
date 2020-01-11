package SNXj;

import java.util.HashMap;
import java.util.Map;

import org.apache.SNX.SNX;
import org.apache.SNX.http.IRoute;
import org.apache.SNX.http.MainHttpServer;
import org.apache.ex.db.DB;
import org.apache.ex.http.HttpHandler;
import org.apache.ex.http.Route1;


public class App {


	static MainHttpServer _srv;

	static DB _db;

	public static void main(String[] args) throws Throwable {
	    
		new SNX();
	       
		System.out.println("start "+ System.getProperty("user.dir"));

		_db = new DB(5000, ":memory:"); // for RAM
		//_mdb = new DB(5000, "/home/vic/db/sdb.db"); // or file path if using file
		
		//new LoadDB(_db).loadDB(); // load fake data if needed
		
		Map<String, IRoute> routes  = new HashMap();
		// we add one route for our HTTP server
		IRoute r1 = new Route1(_db);
		routes.put(r1.getPath(), r1);
		// routes done
		String docRoot = System.getProperty("user.dir") + "/www";
		_srv = new MainHttpServer( new HttpHandler(routes, docRoot ));
		_srv.start(8888);

		//new SS();
		
		System.out.println("end");
		
		
		
	}
	
}
