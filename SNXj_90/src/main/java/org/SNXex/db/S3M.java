package org.SNXex.db;

import java.util.HashMap;
import java.util.Map;;

public class S3M {

	public static void main(String[] args) throws Throwable {
		String server = "ewr1.vultrobjects.com";
		String access = "QVJDCMTBVDZLLQTJVND1";
		String secret = "WrUKYmuNEhs1EdE9w1rXsqnKczgWoB9nCLj2mTTu";

		String bucket = "snx01";

		BasicS3Util s3 = new BasicS3Util(server, access, secret, bucket);

		String prefix = "Monday/NY";
		Map row = new HashMap();
		row.put("a", "A");
		row.put("b", "B");
		
		s3.put(prefix, row);
		s3.put(prefix, row);

		System.out.println(s3.find(prefix));
	}

}// class
