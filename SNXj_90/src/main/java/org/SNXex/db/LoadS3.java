package org.SNXex.db;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		ins();
	}

	protected void ins() throws Throwable {
		int mCount = 25; // 25*40* 1000 = 1 Million
		int i = 0;
		while (i <= mCount) {
			System.out.println();
			System.out.println("Loading a batch:");
			TimerU t =  TimerU.start();
			_insBatch(40*1000);
			i++;
			System.out.println();
			System.out.println(" in " + t.time());
		}
	}

	protected void _insBatch(int count) throws Throwable {
		String prefix = "2020/01/10/APAC";
		int i = 0;
		List<Map<String,Object>> lst = new ArrayList();
		while (i <= count) {
			Map<String,Object> row = new HashMap();
			row.put("name", _faker.name.nameWithMiddle());
			row.put("name", _faker.address.city());
			row.put("name", _faker.internet.ipV4Address().toString());
			row.put("name", _faker.date.backward());
			row.put("name", _faker.business.creditCardType());
			row.put("name", _faker.commerce.department());
			row.put("name", _faker.commerce.price());
			lst.add(row);
			i++;
		}
		
		_s3.put(prefix, lst);

	}// ()
}
