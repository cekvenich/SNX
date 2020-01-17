package org.apache.SNX.tst;

import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;

public class SeleniumUtil {

	ChromeOptions _options;

	ChromeDriver _driver;

	/**
	 * 
	 * Download from https://sites.google.com/a/chromium.org/chromedriver
	 * 
	 * @param chromedriverPath eg
	 *                         "/home/vic/Downloads/chromedriver_linux64/chromedriver"
	 */
	public SeleniumUtil(String chromedriverPath) {

		System.setProperty("webdriver.chrome.driver", chromedriverPath);
	}

	public ChromeOptions getOptions() {

		_options = new ChromeOptions();
		_options.addArguments("--disable-extensions");
		_options.addArguments("--no-sandbox");
		_options.addArguments("disable-infobars");

		_options.addArguments("window-size=1920x1200");

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		logPrefs.enable(LogType.PROFILER, Level.INFO);

		_options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		_options.getCapability(CapabilityType.LOGGING_PREFS);

		// options.setHeadless(true);
		return _options;
	}

	public ChromeDriver getDriver(ChromeOptions options) {
		_driver = new ChromeDriver(options);
		return _driver;
	}

	public Object exec(ChromeDriver driver, StringBuilder arg) {
		/*
		 * https://javadoc.io/doc/org.seleniumhq.selenium/selenium-api/latest/org/openqa
		 * /selenium/JavascriptExecutor.html#executeScript(java.lang.String,java.lang.
		 * Object...)
		 */
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		Object response = jse.executeAsyncScript(arg.toString());
		return response;
	}

	public List<LogEntry> getLogList(ChromeDriver driver, String type) {
		Logs logs = driver.manage().logs();
		return logs.get(type).getAll();
	}

}
