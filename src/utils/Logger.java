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
	
	private Database db;
	private Utility ut;
	private Connection con;
	private PreparedStatement ps;
	
	public Logger(Database db, Utility ut) {
		this.db = db;
		this.con = db.getConnection();
	}
	
	public boolean newLog(long employee, String type, String description) {
		try {
			ps = con.prepareStatement(
				"INSERT INTO logs ("
				+ "?, ?, ?, ?, NOW()"
				+ ");"
			);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
