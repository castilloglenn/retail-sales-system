package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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
			"PAYROLL",
			"PRODUCT INQUIRY",
			"ATTENDANCE",
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
			ps.setString(3, logTypes[type]);
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
				  "SELECT `log_id`, `date`, `description`, `employee_id`"
				+ "FROM logs "
				+ "WHERE log_id "
				+ "BETWEEN 151000000010 "
				+ "AND 161000000010 "
				+ "AND log_id % 10 = 0;"
			);
			ResultSet data = ps.executeQuery();
			Object[][] fetched = new Object[count.getInt(1)][4];
			
			int index = 0;
			while (data.next()) {
				Object[] row = new Object[4];
				row[0] = data.getLong(1);
				row[1] = data.getString(2);
				row[2] = data.getString(3);
				row[3] = db.fetchEmployeeByID(data.getLong(4))[3];
				fetched[index] = row;
				index++;
			}
			
			return fetched;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empty;
	}
	
	public Object[][] fetchPayrolls() {
		Object[][] empty = new Object[][] {{}};
		try {
			ps = con.prepareStatement(
				  "SELECT COUNT(*) "
				+ "FROM logs "
				+ "WHERE log_id "
				+ "BETWEEN 101000000010 "
				+ "AND 111000000010 "
				+ "AND log_id % 10 = 0;"
			);
			ResultSet count = ps.executeQuery();
			count.next();
			
			if (count.getInt(1) == 0) return empty;
			
			ps = con.prepareStatement(
				  "SELECT `log_id`, `date`, `description`, `employee_id`"
				+ "FROM logs "
				+ "WHERE log_id "
				+ "BETWEEN 101000000010 "
				+ "AND 111000000010 "
				+ "AND log_id % 10 = 0;"
			);
			ResultSet data = ps.executeQuery();
			Object[][] fetched = new Object[count.getInt(1)][4];
			
			int index = 0;
			while (data.next()) {
				Object[] row = new Object[4];
				row[0] = data.getLong(1);
				row[1] = data.getString(2);
				row[2] = data.getString(3);
				row[3] = db.fetchEmployeeByID(data.getLong(4))[3];
				fetched[index] = row;
				index++;
			}
			
			return fetched;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empty;
	}
	
	public boolean removeLog(long id, String date) {
		try {
			ps = con.prepareStatement(
				  "DELETE FROM logs "
				+ "WHERE log_id = ? "
				+ "AND date = ?;"
			);
			ps.setLong(1, id);
			ps.setString(2, date);
			int affected = ps.executeUpdate();
			return (affected > 0) ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkScheduleLog(String date) {
		return false;
	}
	
	public HashMap<Long, HashMap<String, String[]>> getEmployeeScheduleMap(long[] ids) {
		HashMap<Long, HashMap<String, String[]>> schedules = new HashMap<>();
		for (long id : ids) {
			try {
				HashMap<String, String[]> sched = new HashMap<>();
				ps = con.prepareStatement(
					  "SELECT log_id "
					+ "FROM logs "
					+ "WHERE employee_id = ? "
					+ "AND type = \"SCHEDULE\" "
					+ "AND log_id % 10 = 1;"
				);
				ps.setLong(1, id);
				ResultSet log_ids = ps.executeQuery();
				while (log_ids.next()) {
					try {
						sched.put(
							parseScheduleDate(log_ids.getLong(1)), 
							parseScheduleTime(log_ids.getLong(1))
						);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				schedules.put(id, sched);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return schedules;
	}
	
	public String parseScheduleDate(long id) {
		if (id % 10 == 0) return null;
		try {
			ps = con.prepareStatement(
				  "SELECT description "
				+ "FROM logs "
				+ "WHERE log_id = ("
					+ "SELECT log_id "
					+ "FROM logs "
					+ "WHERE log_id > ? "
					+ "AND log_id % 10 = 0 "
					+ "LIMIT 1"
				+ ");"
			);
			ps.setLong(1, id);
			ResultSet dates = ps.executeQuery();
			dates.next();
			
			return dates.getString(1).substring(0, 10);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] parseScheduleTime(long id) {
		try {
			ps = con.prepareStatement(
				  "SELECT description "
				+ "FROM logs "
				+ "WHERE log_id = ?;"
			);
			ps.setLong(1, id);
			ResultSet times = ps.executeQuery();
			times.next();
			String s = times.getString(1);
			String[] time = new String[2];
			time[0] = s.substring(0, 5);
			time[1] = s.substring(6);
			return time;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] parseAttendanceDateTime(long emp_id, String date) {
		try {
			ps = con.prepareStatement(
				  "SELECT date "
				+ "FROM logs "
				+ "WHERE employee_id = ? "
				+ "AND type = \"ATTENDANCE\" "
				+ "AND date "
				+ "BETWEEN ?"
				+ "AND ? "
				+ "LIMIT 2;"
			);
			
			date = date.substring(6) + "/" + date.substring(0, 5);
			date += " 00:00:00";
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date from = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(from);
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date to = c.getTime();
			String fromString = sdf.format(from);
			String toString = sdf.format(to);
			
			ps.setLong(1, emp_id);
			ps.setString(2, fromString);
			ps.setString(3, toString);
			
			ResultSet rs = ps.executeQuery();
			String[] attendance = new String[2];
			String[] entry = new String[3];
			int index = 0;
			while (rs.next()) {
				if (rs.getString(1) == null) break;
				attendance[index] = rs.getString(1);
				index++;
			}
			
			entry[0] = attendance[0].substring(0, 10);
			entry[1] = attendance[0].substring(11, 16);
			entry[2] = attendance[1].substring(11, 16);
			
			return entry;
		} catch (SQLException | ParseException | NullPointerException e) {
			return null;
		}
	}
	
}
