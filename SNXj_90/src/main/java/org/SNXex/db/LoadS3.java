package org.SNXex.db;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import org.apache.SNX.db.BasicS3Util;
import org.apache.SNX.util.TimerU;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.bloco.faker.Faker;

public class LoadS3 {

	BasicS3Util _s3;
	Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());
	Faker _faker = new Faker();

	public void load(BasicS3Util s3) throws Throwable {
		_s3 = s3;

		String prefix = "2020/01/10/APAC";

		System.out.println(s3.find(prefix));
	}

	protected void insMillion() throws Throwable {
		int mCount = 10;
		int i = 0;
		while (i <= mCount) {
			TimerU t =  TimerU.start();
			_ins(25*1000);
			i++;
		    System.out.println(" +100K in " + t.time());
		}
	}

	protected void _ins(int count) throws Throwable {
		String prefix = "2020/01/10/APAC";
		int i = 0;
		while (i <= count) {
			Map row = new HashMap();
			row.put("name", _faker.name.nameWithMiddle());
			row.put("name", _faker.address.city());
			row.put("name", _faker.internet.ipV4Address().toString());
			row.put("name", _faker.date.backward());
			row.put("name", _faker.business.creditCardType());
			row.put("name", _faker.commerce.department());
			row.put("name", _faker.commerce.price());

			_s3.put(prefix, row);
			i++;
		}
	}// ()
}
