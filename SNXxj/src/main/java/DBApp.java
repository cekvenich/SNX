import org.SNXex.db.DBS;
import org.SNXex.db.LoadFakeDB;
import org.apache.SNX.SNX;

public class DBApp {

	static DBS _mdb;

	public static void main(String[] args) throws Throwable {

		new SNX();

		_mdb = new DBS(5000, ":memory:"); // for memory
		_mdb = new DBS(5000, "/home/vic/db/sdb.db"); // or file path if not using RAM

		new LoadFakeDB(_mdb).loadDB(); // load fake data 
	}

	public void other() {
		// new SS();
	}
}
