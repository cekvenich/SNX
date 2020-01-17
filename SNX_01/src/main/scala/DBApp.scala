
import org.SNXex.db.DBS

import org.SNXex.db.LoadFakeDB

import org.apache.SNX.SNX

import DBApp._

object DBApp {

  var _mdb: DBS = _

  def main(args: Array[String]): Unit = {
    new SNX()
    // for memory
    //_mdb = new DBS(5000, ":memory:")
    // or file path if not using RAM
    _mdb = new DBS(5000, "/home/vic/db/sdb.db")
    // load fake data
    new LoadFakeDB(_mdb).loadDB()
  }

}
