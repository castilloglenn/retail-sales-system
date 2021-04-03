package utils;

import java.io.File;
import java.sql.*;

import javax.swing.JOptionPane;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Database {
	
	public final double SSS_RATE = 0.045;
	public final double PHILHEALTH_RATE = 0.035;
	public final double PAGIBIG_RATE = 100;

	private String db_url;
	private String db_name;
	private String db_user;
	private String db_pass;
	
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private Utility ut;
	
	public Database(Utility ut) {
		this.ut = ut;
		setupDatabaseLogin();
		
		try {
			con = DriverManager.getConnection(
				db_url, db_user, db_pass
			);
			stmt = con.createStatement();
			createDatabase();
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
				null, 
				"Please open MySQL Server/XAMPP in order to continue.\n"
				+ "Communications with the server is unreachable.", 
				"No database found", 
				JOptionPane.WARNING_MESSAGE
			);
			System.exit(0);
		}
	}
	
	private void setupDatabaseLogin() {
		File f = new File("./config/setup.properties");
		if(f.isFile() && !f.isDirectory()) { 
		    String password = ut.decodeData(ut.getDatabaseProperty(ut.encodeData("password")));
		    db_url = ut.decodeData(ut.getDatabaseProperty(ut.encodeData("url")));
		    db_name = ut.decodeData(ut.getDatabaseProperty(ut.encodeData("database")));
		    db_user = ut.decodeData(ut.getDatabaseProperty(ut.encodeData("username")));
		    db_pass = (password.equals("emptypassword")) ? "" : password;
		} else {
			db_url = ut.getConfig("db_url");
			db_name = ut.getConfig("db_name");
			db_user = ut.getConfig("db_user"); 
			db_pass = ut.getConfig("db_pass");
		}
	}
	
	public void createDatabase() throws SQLException {
		stmt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s;", db_name));
		stmt.execute(String.format("USE %s;", db_name));
	}
	
	public void createTables() throws SQLException {
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS employee ("
					+ "employee_id BIGINT PRIMARY KEY,"
					+ "position VARCHAR(255) NOT NULL,"
					+ "fname VARCHAR(255) NOT NULL,"
					+ "mname VARCHAR(255) DEFAULT \"\","
					+ "lname VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "basic DOUBLE(8, 2) NOT NULL,"
					+ "incentives DOUBLE(8, 2) NOT NULL,"
					+ "contributions DOUBLE(8, 2) NOT NULL,"
					+ "penalty DOUBLE(8, 2) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS logs ("
					+ "log_id BIGINT PRIMARY KEY,"
					+ "employee_id BIGINT NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "description VARCHAR(512) NOT NULL,"
					+ "date DATETIME NOT NULL,"
					+ "FOREIGN KEY (employee_id)"
					+ "REFERENCES employee(employee_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS product ("
					+ "product_id BIGINT PRIMARY KEY,"
					+ "category VARCHAR(255) NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "uom VARCHAR(255) NOT NULL,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "purchase_value DOUBLE(8, 2) NOT NULL,"
					+ "sell_value DOUBLE(8, 2) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS supplier ("
					+ "supplier_id BIGINT PRIMARY KEY,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "contact_no VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS customer ("
					+ "customer_id BIGINT PRIMARY KEY,"
					+ "rebate DOUBLE(8, 2) NOT NULL,"
					+ "fname VARCHAR(255) NOT NULL,"
					+ "mname VARCHAR(255) DEFAULT \"\","
					+ "lname VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(255) NOT NULL,"
					+ "contact_no VARCHAR(255) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS transaction ("
					+ "transaction_id BIGINT PRIMARY KEY,"
					+ "employee_id BIGINT NOT NULL,"
					+ "customer_id BIGINT NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "total_amount DOUBLE(10, 2) NOT NULL,"
					+ "amount_tendered DOUBLE(8, 2) NOT NULL,"
					+ "date DATETIME NOT NULL,"
					+ "FOREIGN KEY (employee_id)"
					+ "REFERENCES employee(employee_id),"
					+ "FOREIGN KEY (customer_id)"
					+ "REFERENCES customer(customer_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS promo ("
					+ "product_id BIGINT NOT NULL,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "conditions VARCHAR(255) NOT NULL,"
					+ "discount DOUBLE(8, 2) NOT NULL,"
					+ "start_date DATETIME NOT NULL,"
					+ "end_date DATETIME NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS transacts ("
					+ "transaction_id BIGINT NOT NULL,"
					+ "product_id BIGINT NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "total_price DOUBLE(10, 2) NOT NULL,"
					+ "FOREIGN KEY (transaction_id)"
					+ "REFERENCES transaction(transaction_id),"
					+ "FOREIGN KEY (product_id)"
					+ "REFERENCES product(product_id)"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS supplies ("
					+ "supplier_id BIGINT NOT NULL,"
					+ "product_id BIGINT NOT NULL,"
					+ "quantity DOUBLE(8, 2) NOT NULL,"
					+ "total_price DOUBLE(10, 2) NOT NULL,"
					+ "FOREIGN KEY (supplier_id)"
					+ "REFERENCES supplier(supplier_id),"
					+ "FOREIGN KEY (product_id)"
					+ "REFERENCES product(product_id)"
			+ ");"
		);
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public boolean insertNewEmployee(Object[] data) {
		// data format: id, position, fname, mname, lname, adress, basic, password
		// data index:   0,        1,     2,     3,     4,      5,     6,		 7
		if (data.length != 8) return false;
		
		try {
			ps = con.prepareStatement(
				"INSERT INTO employee VALUES ("
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
				+ ");"
			);
			ps.setLong(1, (long) data[0]);
			ps.setString(2, (String) data[1]); 
			ps.setString(3, (String) data[2]);
			ps.setString(4, (String) data[3]);
			ps.setString(5, (String) data[4]);
			ps.setString(6, (String) data[5]);
			ps.setDouble(7, (double) data[6]);
			ps.setDouble(8, 0);	
			ps.setDouble(9, (
				(double) data[6] * 
					(SSS_RATE + PHILHEALTH_RATE)) 
					+ PAGIBIG_RATE
			);
			ps.setDouble(10, 0);					
			ps.setString(11, (String) data[7]);
			ps.executeUpdate();
			return true;
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkLogin(String user, String pass) {
		try {
			ps = con.prepareStatement(
				  "SELECT employee_id "
				+ "FROM employee "
				+ "WHERE employee_id=? "
				+ "AND password=?;"
			);
			ps.setLong(1, Long.parseLong(user));
			ps.setString(2, ut.hashData(pass));
			ResultSet check = ps.executeQuery();
			check.next();
			// If this is an empty ResultSet if will throw error
			check.getLong(1);
			return true;
		} catch (SQLException | NumberFormatException e) {
			return false;
		}
	}
	
	public int fetchManagers() {
		try {
			ps = con.prepareStatement(
				  "SELECT COUNT(employee_id) "
				+ "FROM employee "
				+ "WHERE position=?;"
			);
			ps.setString(1, ut.encodeData("MANAGER"));
			ResultSet count = ps.executeQuery();
			if (count.next()) return count.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Object[][] fetchEmployees() {
		try {
			int size = fetchEmployeeCount();
			if (size != 0) {
				Object[][] data = new Object[size][10];
				ResultSet fetchData = stmt.executeQuery(
					"SELECT employee_id, position, fname, mname, lname, address, "
					+ "basic, incentives, contributions, penalty FROM employee;"
				);
				
				int index = 0;
				while (fetchData.next()) {
					Object[] row = new Object[10];
					
					row[0] = fetchData.getLong(1);
					row[1] = ut.decodeData(fetchData.getString(2));
					row[2] = ut.decodeData(fetchData.getString(3));
					row[3] = ut.decodeData(fetchData.getString(4));
					row[4] = ut.decodeData(fetchData.getString(5));
					row[5] = ut.decodeData(fetchData.getString(6));
					row[6] = fetchData.getDouble(7);
					row[7] = fetchData.getDouble(8);
					row[8] = fetchData.getDouble(9);
					row[9] = fetchData.getDouble(10);
					
					data[index] = row;
					index++;
				}
				
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Object[][] {{}};
	}
	
	public int fetchEmployeeCount() {
		try {
			ResultSet fetchCount = stmt.executeQuery(
				"SELECT COUNT(employee_id) FROM employee;"
			);
			fetchCount.next();
			return fetchCount.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public long fetchLastIDByTable(String table, String idColumn) {
		try {
			ResultSet pid = stmt.executeQuery(
				"SELECT MAX(" + idColumn + ") FROM " + table + ";"
			);
			pid.next();
			if (pid.getLong(1) != 0) return pid.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Fetches the latest log id not by directly getting the max in id column,
	 * but by the entry number which is the substring of the full id <p>
	 * 
	 * @return returns the latest log by the substring 3 to end.
	 */
	public long fetchLastLog() {
		try {
			long max = 0;
			for (int type = 1; type <= 9; type++) {
				ps = con.prepareStatement(
					  "SELECT MAX(log_id) "
					+ "FROM logs "
					+ "WHERE log_id > ? "
					+ "AND log_id < ?"
					+ ";"
				);
				StringBuilder least = new StringBuilder("1");
				StringBuilder most = new StringBuilder("1");
				
				least.append(Integer.toString(type));
				most.append(Integer.toString(type + 1));
				
				least.append(String.format("0%08d0", 0));
				most.append(String.format("0%08d0", 0));
				
				ps.setLong(1, Long.parseLong(least.toString()));
				ps.setLong(2, Long.parseLong(most.toString()));
				
				ResultSet pid = ps.executeQuery();
				pid.next();
				long current = pid.getLong(1);
				
				if (max == 0) {
					max = current;
				} else if (current != 0) {
					long subs = Long.parseLong(Long.toString(current).substring(3));
					long maxsub = Long.parseLong(Long.toString(max).substring(3));
					
					if (subs > maxsub) max = current;
				}
			}
			
			if (max != 0) return max;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Fetches the latest employee id not by directly getting the max in id column,
	 * but by the entry number which is the substring of the full id <p>
	 * 
	 * @return returns the latest employee by the substring 3 to end.
	 */
	public long fetchLastEmployee() {
		try {
			long max = 0;
			for (int level = 0; level <= 5; level++) {
				ps = con.prepareStatement(
					  "SELECT MAX(employee_id) "
					+ "FROM employee "
					+ "WHERE employee_id > ? "
					+ "AND employee_id < ?"
					+ ";"
				);
				StringBuilder least = new StringBuilder("5");
				StringBuilder most = new StringBuilder("5");
				
				least.append(Integer.toString(level));
				most.append(Integer.toString(level + 1));
				
				least.append(String.format("%09d", 0));
				most.append(String.format("%09d", 0));
				
				ps.setLong(1, Long.parseLong(least.toString()));
				ps.setLong(2, Long.parseLong(most.toString()));
				
				ResultSet pid = ps.executeQuery();
				pid.next();
				long current = pid.getLong(1);
				
				if (max == 0) {
					max = current;
				} else if (current != 0) {
					long subs = Long.parseLong(Long.toString(current).substring(8));
					long maxsub = Long.parseLong(Long.toString(max).substring(8));
					
					if (subs > maxsub) max = current;
				}
			}
			
			if (max != 0) return max;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}