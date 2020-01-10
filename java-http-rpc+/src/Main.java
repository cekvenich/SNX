import org.rpc.http.HttpMainServer;

public class Main {

	static HttpMainServer _srv;

	public static void main(String[] args) throws Exception {

		_srv = new HttpMainServer();

		try {
			_srv.start(8888);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}// ()

}// class
