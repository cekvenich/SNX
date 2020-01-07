
import java.util._
import java.text.DateFormat._

object One  {
  
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    println(df format now)
  }
  
}// 2.13.1