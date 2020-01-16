import java.util.List;

import org.SNX.tst.SelUtil;
import org.apache.SNX.SNX;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.apache.SNX.tst.*;

public class MainTests {

	public static void main(String[] args) throws Throwable {

		new SNX();

		SelUtil d = new SelUtil("/home/vic/Downloads/chromedriver_linux64/chromedriver");
		ChromeOptions options = d.getOptions();

		ChromeDriver driver = d.getDriver(options);

		Capabilities actualCaps = ((HasCapabilities) driver).getCapabilities();
		System.out.println("Actual caps: " + actualCaps);
		System.out.println("Starting XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		String url = "http://localhost:8888/tests/index.html";
		driver.get(url);

		Thread.sleep(100);
		System.out.println(driver.getTitle());

		StringBuilder arg = new StringBuilder(
				"var callback = arguments[arguments.length - 1];" + "webDriverFoo(callback)");

		Object resp = d.exec(driver, arg);

		System.out.println(resp);

		// JSONObject json = (JSONObject) new JSONParser().parse((String) response);
		// System.out.println(json);

		System.out.println("Ending: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		List<LogEntry> logs = d.getLogList(driver, LogType.BROWSER);

		System.out.println(logs);

	}

}
