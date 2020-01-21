import java.util.List

import org.apache.SNX.SNX

import org.openqa.selenium.Capabilities

import org.openqa.selenium.HasCapabilities

import org.openqa.selenium.chrome.ChromeDriver

import org.openqa.selenium.chrome.ChromeOptions

import org.openqa.selenium.logging.LogEntry

import org.openqa.selenium.logging.LogType

import org.apache.SNX.tst._

//remove if not needed
import scala.collection.JavaConversions._

object E2ETest {

  def main(args: Array[String]): Unit = {
    new SNX()

    val d: SeleniumUtil = new SeleniumUtil(
      "/home/vic/Downloads/chromedriver_linux64/chromedriver")
    val options: ChromeOptions = d.getOptions
    val driver: ChromeDriver = d.getDriver(options)
    val actualCaps: Capabilities =
      driver.asInstanceOf[HasCapabilities].getCapabilities
    println("Actual caps: " + actualCaps)
    println("Starting XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    val url: String = "http://localhost:8888/tests/index.html"
    driver.get(url)
    Thread.sleep(100)
    println(driver.getTitle)
    val arg = new java.lang.StringBuilder(
      "var callback = arguments[arguments.length - 1];" + "webDriverFoo(callback)")
    val resp = d.exec(driver, arg)
    println(resp)
    println("Ending: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    val logs: List[LogEntry] = d.getLogList(driver, LogType.BROWSER)
    println(logs)
    
  }

}
