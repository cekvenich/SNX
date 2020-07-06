import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.cli.RPCCli;

public class MainCli {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] ags) throws Throwable {

		RPCCli rpcc = new RPCCli();

		Map args = new HashMap();
		args.put("a", 5);
		args.put("b", 2);

		Map ans = rpcc.invoke("api", "multiply", args, null);

		LOG.info(ans.toString());

	}

}
