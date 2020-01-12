import org.apache.SNX.SNX;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MainTests {

	public static void main(String[] args) throws Throwable {

		new SNX();

		 WebClient bro = new WebClient(BrowserVersion.CHROME);
		 bro.getOptions().setJavaScriptEnabled(true);
		 bro.setAjaxController(new NicelyResynchronizingAjaxController());
		HtmlPage page = bro.getPage("http://htmlunit.sourceforge.net");
		
		page.executeJavaScript("");
		
		page.getElementsByName("");

		String pageAsXml = page.asXml();
		System.out.println(pageAsXml);

		String pageAsText = page.asText();

	}

}
