package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Logger {
	
	private Database db;
	private Utility ut;
	
	private Connection con;
	private PreparedStatement ps;
	
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
	
	public Logger(Database db, Utility ut) {
		this.db = db; this.ut = ut;
		this.con = db.getConnection();
		
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Object[][] fetchSchedules() {
		Object[][] empty = new Object[][] {{}};
		try {
			ps = con.prepareStatement(
				  "SELECT COUNT(*) "
				+ "FROM logs "
				+ "WHERE log_id "
				+ "BETWEEN 151000000010 "
				+ "AND 161000000010 "
				+ "AND log_id % 10 = 0;"
			);
			ResultSet count = ps.executeQuery();
			count.next();
			
			if (count.getInt(1) == 0) return empty;
			
			ps = con.prepareStatement(
				  "SELECT `date`, `description`, `employee_id`"
				+ "FROM logs "
				+ "WHERE log_id "
				+ "BETWEEN 151000000010 "
				+ "AND 161000000010 "
				+ "AND log_id % 10 = 0;"
			);
			ResultSet data = ps.executeQuery();
			Object[][] fetched = new Object[count.getInt(1)][3];
			
			int index = 0;
			while (data.next()) {
				Object[] row = new Object[3];
				row[0] = data.getString(1);
				row[1] = data.getString(2);
				row[2] = data.getLong(3);
				fetched[index] = row;
				index++;
			}
			
			return fetched;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empty;
	}
	
}
