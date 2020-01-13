import java.util.List;
import java.util.logging.Level;

import org.apache.SNX.SNX;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;

public class MainTests {

	public static void main(String[] args) throws Throwable {

		new SNX();

		// Download first https://sites.google.com/a/chromium.org/chromedriver

		String cdriver = "/home/vic/Downloads/chromedriver_linux64/chromedriver";
		System.setProperty("webdriver.chrome.driver", cdriver);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("disable-infobars");

		options.addArguments("window-size=1920x1200");

		// options.setHeadless(true);
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		logPrefs.enable(LogType.PROFILER, Level.INFO);
		
		options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		WebDriver driver = new ChromeDriver(options);
		JavascriptExecutor jse = (JavascriptExecutor) driver;  

		Capabilities actualCaps = ((HasCapabilities) driver).getCapabilities();
		System.out.println("Actual caps: " + actualCaps);
		System.out.println("Starting XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		String url = "http://localhost:8888/tests/index.html";
		driver.get(url); 
		
		Thread.sleep(100);
		System.out.println(driver.getTitle());
		
		StringBuilder foo = new StringBuilder("var callback = arguments[arguments.length - 1];"
				+ "webDriverFoo(callback)");

		/*
		 https://javadoc.io/doc/org.seleniumhq.selenium/selenium-api/latest/org/openqa/selenium/JavascriptExecutor.html#executeScript(java.lang.String,java.lang.Object...)
		 */
		Object response = jse.executeAsyncScript(foo.toString(),"123");

		System.out.println(response);

		//JSONObject json = (JSONObject) new JSONParser().parse((String) response);
		//System.out.println(json);
		
		System.out.println("Ending: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Logs logs = driver.manage().logs();
		
		List<LogEntry> perfLogEntries = logs.get(LogType.BROWSER).getAll();
		System.out.println(perfLogEntries);

	}

}
