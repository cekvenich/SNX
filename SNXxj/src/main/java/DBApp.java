import org.SNXex.db.DBS;
import org.SNXex.db.LoadFakeDB;
import org.apache.SNX.SNX;

public class DBApp {

	static DBS _db;

	public static void main(String[] args) throws Throwable {

		new SNX();

		//_db = new DB(5000, ":memory:"); // for memory
		_db = new DBS(5000, "/home/vic/db/sdb.db"); // or file path if not using RAM

		new LoadFakeDB(_db).loadDB(); // load fake data 
	}

	public void other() {
		// new SS();
	}
}
