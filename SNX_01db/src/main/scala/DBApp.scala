


import org.SNXa.db._

import org.apache.SNX.SNX


object DBApp {
  
  def main(args: Array[String]): Unit = {
    new SNX()
    // for memory
    //_mdb = new DBS(5000, ":memory:")
    // or file path if not using RAM
      var _mdb = new SDB(5000, "/home/vic/db/sdb.db")
    // load fake data
    new LoadFakeSDB(_mdb).loadDB()
  }

}
