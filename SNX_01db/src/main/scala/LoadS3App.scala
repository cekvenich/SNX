
import org.SNXa.db.LoadS3

import org.apache.SNX.db.BasicS3Util

//remove if not needed
import scala.collection.JavaConversions._

object LoadS3App {

  def main(args: Array[String]): Unit = {
    
    val server: String = "ewr1.vultrobjects.com"
    val access: String = "QVJDCMTBVDZLLQTJVND1"
    val secret: String = "WrUKYmuNEhs1EdE9w1rXsqnKczgWoB9nCLj2mTTu"
    val bucket: String = "snx01"
    val s3: BasicS3Util = new BasicS3Util(server, access, secret, bucket)
    
    new LoadS3().load(s3)
    
  }

}
