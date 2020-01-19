
import org.SNXex0.db.LoadS3J;
import org.apache.SNX.db.BasicS3Util;

public class LoadS3App {

	public static void main(String[] args) throws Throwable {
		String server = "ewr1.vultrobjects.com";
		String access = "QVJDCMTBVDZLLQTJVND1";
		String secret = "WrUKYmuNEhs1EdE9w1rXsqnKczgWoB9nCLj2mTTu";

		String bucket = "snx01";

		BasicS3Util s3 = new BasicS3Util(server, access, secret, bucket);

		new LoadS3J().load(s3);

	}

}// class
