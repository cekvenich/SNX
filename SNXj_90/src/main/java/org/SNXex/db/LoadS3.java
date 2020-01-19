package org.SNXex.db;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.SNX.SNX;
import org.apache.SNX.db.BasicS3Util;
import org.apache.SNX.util.TimerU;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.bloco.faker.Faker;

public class LoadS3 {

	BasicS3Util _s3;
	Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());
	Faker _faker = new Faker();

	String prefix = "2020/01/19/APAC";

	public void load(BasicS3Util s3) throws Throwable {
		
		new SNX().getSNX();
		
		_s3 = s3;
		
		//ins();
		
		List<String> lst =_s3.find(prefix);
		String key = lst.get(0);
		System.out.println(key);
		
		
		List<Map<String,Object>> rows = _s3.getAsList(key);
		
		
		Map row = rows.get(0);
		
		System.out.println(row);
		
	}

	protected void ins() throws Throwable {
		int mCount = 2; // 25*40* 1000 = 1 Million
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
		int i = 0;
		List<Map<String,Object>> lst = new ArrayList();
		while (i <= count) {
			Map<String,Object> row = new HashMap();
			row.put("name", _faker.name.nameWithMiddle());
			row.put("city", _faker.address.city());
			row.put("ip", _faker.internet.ipV4Address().toString());
			row.put("date", _faker.date.backward());
			row.put("cc", _faker.business.creditCardType());
			row.put("dept", _faker.commerce.department());
			row.put("price", _faker.commerce.price());
			lst.add(row);
			i++;
		}
		
		_s3.put(prefix, lst);

	}// ()
}
