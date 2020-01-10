package org.apache.ex.db;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;

import org.apache.SNX.db.Timer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.bloco.faker.Faker;

public class LoadFakeDB {
	static Log LOG = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	public LoadFakeDB(DB db) {
		LOG.info("oh hi log");
		_mdb = db;		
	}

	DB _mdb;
	static Faker _faker = new Faker();

	static StringBuilder _ins = new StringBuilder(
		"INSERT INTO tab1(fullName, city, ip, dateOfPurch, cc, dept, price) VALUES(" + "?,?,? ,?,?,?,?" + ")");

	public void loadDB() throws Throwable {
		StringBuilder tab = new StringBuilder(
			"CREATE TABLE tab1(" + "fullName VARCHAR(40), postcode VARCHAR(15), city VARCHAR(25), ip VARCHAR(20), "
				+ "dateOfPurch DATE,"
				+ "cc VARCHAR(25), dept VARCHAR(40), price DECIMAL(10,2)" 
				+ ");");
		
		// create schema
		Connection con = _mdb.begin();
		_mdb.write(con,tab);
		con.commit();
		
		System.out.println(_mdb.read(con, new StringBuilder("SELECT COUNT(*) AS c FROM tab1")));
		con.commit();
		
		insert1MilsLoop(3); // 3 times a million = 3 million
	}// ()

	public void insert1MilsLoop(int count) throws Throwable {
		count = count * 10;
		for (int j = 1; j <= count; j++) {
			Timer watch = Timer.start();
			System.out.print("outer loop " + j + ":"); // 100K
			for (int i = 1; i <= 100; i++) {
				this.insIn(1000);
			}
			System.out.println(" +100K in " + watch.time());
			
			Connection con = _mdb.begin();			
			System.out.println(_mdb.read(con, new StringBuilder("SELECT COUNT(*) AS cout FROM tab1")));
			con.commit();
		}
	}

	protected void insIn(int count) throws Throwable { // ~ 300-700 / sec m or 25K s
		Connection con = _mdb.begin();

		for (int i = 1; i <= count; i++) {
			_mdb.write(con, _ins, _faker.name.nameWithMiddle(), _faker.address.city(),
				_faker.internet.ipV4Address().toString(), _faker.date.backward(), _faker.business.creditCardType(),
				_faker.commerce.department().toString(), _faker.commerce.price() // DECIMAL
			);
		}

		con.commit();
	}// ()

}
