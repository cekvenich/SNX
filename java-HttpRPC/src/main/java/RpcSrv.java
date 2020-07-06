import com.example.handlers.RPCRouter;
import com.rpc.codec.Codec;
import com.rpc.srv.HttpRPCMainServer;

public class RpcSrv {

	static HttpRPCMainServer _srv;

	public static void main(String[] args) throws Exception {

		_srv = new HttpRPCMainServer(new RPCRouter(new Codec()));

		try {
			_srv.start(8888);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}// ()

}// class
