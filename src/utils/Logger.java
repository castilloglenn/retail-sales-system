package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Logger {
	
	private String[] logTypes = {
			"PRODUCT INQUIRY",
			"ATTENDACE",
			"LATE/ABSENT",
			"LOST PASSWORD",
			"SCHEDULE",
			"PRODUCT REQUESTS",
			"DELIVERY",
			"PULL-OUT",
			"SYSTEM LOG"
	};
	
	private Database db;
	private Utility ut;
	
	private Connection con;
	private PreparedStatement ps;
	
	public Logger(Database db, Utility ut) {
		this.db = db; this.ut = ut;
		this.con = db.getConnection();
		newLog(55210401001L, 2, 0, "On time");
	}
	
	public boolean newLog(long employee, int type, int messagePart, String description) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO logs VALUES ("
				+ "?, ?, ?, ?, NOW()"
				+ ");"
			);
			ps.setLong(1, ut.generateLogID(
				db.fetchLastLog(), type, messagePart)
			);
			ps.setLong(2, employee);
			ps.setString(3, logTypes[type - 1]);
			ps.setString(4, description);
			ps.executeUpdate();
			System.out.println("SUCCESS!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
