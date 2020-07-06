package com.rutil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Props {

	INSTANCE;

	private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	// private final InternalLogger _nlog =
	// InternalLoggerFactory.getInstance(Confd.class);

	public static final String DIR = System.getProperty("user.dir");

	protected static String FILE = "conf";

	protected static Map<String, String> _conf;

	protected static Map loadProps(String fn) throws Throwable {
		InputStream input = new FileInputStream(fn);

		Map props = new Properties();
		((Properties) props).load(input);
		input.close();
		return props;
	}

	protected void loadConf() throws Throwable {
		String fn = DIR + "/" + FILE + ".props";

		Map props = loadProps(fn);
		_conf = props; // atomic
	}

	/**
	 * No noise
	 * 
	 * @throws Throwable
	 */
	public String getConfString(String key) throws Throwable {

		String s = getConf(key);
		return s;
	}

	protected String getConf(String key) throws Throwable {
		if (_conf == null)
			loadConf();
		logger.info(_conf.toString());
		String s = _conf.get(key);
		if (null == s)
			throw new Throwable("not found " + key);
		return s.trim();
	}

	public int getConfInt(String key) throws Throwable {
		String pro = getConf(key);
		return Integer.parseInt(pro);
	}

	public boolean getConfBool(String key) throws Throwable {
		String pro = getConf(key);
		return Boolean.parseBoolean(pro);
	}

	public List<String> getConfList(String key) throws Throwable {
		String[] csv = getConf(key).split(",");

		List<String> list = new ArrayList<>(Arrays.asList(csv));
		return list;
	}

}
