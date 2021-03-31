import java.sql.*;

import javax.swing.JOptionPane;

public class Database {
	
	private String db;
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private Utility ut;
	
	Database(Utility ut) {
		this.ut = ut;
		this.db = ut.getConfig("db_name");
		
		try {
			con = DriverManager.getConnection(
				ut.getConfig("db_url"), 
				ut.getConfig("db_user"), 
				ut.getConfig("db_pass")
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
	
	public void createDatabase() throws SQLException {
		stmt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s;", db));
		stmt.execute(String.format("USE %s;", db));
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
					+ "penalty DOUBLE(8, 2) NOT NULL"
			+ ");"
		);
		stmt.execute(
			"CREATE TABLE IF NOT EXISTS logs ("
					+ "log_id BIGINT PRIMARY KEY,"
					+ "employee_id BIGINT NOT NULL,"
					+ "type VARCHAR(255) NOT NULL,"
					+ "description VARCHAR(255) NOT NULL,"
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
	
	public int fetchManagers() {
		try {
			ps = con.prepareStatement(
				  "SELECT COUNT(employee_id) "
				+ "FROM employee "
				+ "WHERE position=?;"
			);
			ps.setString(1, "Manager");
			ResultSet count = ps.executeQuery();
			if (count.next()) return count.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}