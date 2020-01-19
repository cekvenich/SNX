
import org.SNXex0.db.LoadS3;
import org.apache.SNX.db.BasicS3Util;

public class S3M {

	public static void main(String[] args) throws Throwable {
		String server = "ewr1.vultrobjects.com";
		String access = "QVJDCMTBVDZLLQTJVND1";
		String secret = "WrUKYmuNEhs1EdE9w1rXsqnKczgWoB9nCLj2mTTu";

		String bucket = "snx01";

		BasicS3Util s3 = new BasicS3Util(server, access, secret, bucket);

		new LoadS3().load(s3);

	}

}// class
