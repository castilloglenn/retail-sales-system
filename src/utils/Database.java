package utils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Main;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Database {
	
	public final double SSS_RATE = 0.045;
	public final double PHILHEALTH_RATE = 0.035;
	public final double PAGIBIG_RATE = 100;
	public final double LATE_FEE = 50;

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
				"Communications with the server is unreachable.\n"
				+ "Error message:\n" + e.getMessage(), 
				"Error | " + Main.SYSTEM_NAME, 
				JOptionPane.WARNING_MESSAGE
			);
			System.exit(0);
		}
	}
	
	private void setupDatabaseLogin() {
		File f = new File("./config/setup.properties");
		if(f.isFile() && !f.isDirectory()) { 
		    String password = ut.getDatabaseProperty("password");
		    db_url = ut.getDatabaseProperty("url");
		    db_name = ut.getDatabaseProperty("database");
		    db_user = ut.getDatabaseProperty("username");
		    db_pass = password;
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
					+ "description VARCHAR(4096) NOT NULL,"
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
					+ "end_date DATETIME NOT NULL,"
					+ "FOREIGN KEY(product_id) "
					+ "REFERENCES product(product_id)"
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
		stmt.execute(
			  "INSERT INTO customer "
			+ "SELECT * FROM (SELECT 1, 0, \"WALK\", \"-\", \"IN\", \"System\", \"None\") AS tmp "
			+ "WHERE NOT EXISTS ("
			+ "    SELECT customer_id FROM customer WHERE customer_id = 1"
			+ ") LIMIT 1;"
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
	
	public int insertNewProduct(long product_id, String category, double quantity, 
			String uom, String name, double purchase_value, double sell_value) 
		throws SQLException {
			ps = con.prepareStatement(
				  "INSERT INTO product "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);"
			);
			ps.setLong(1, product_id);
			ps.setString(2, category);
			ps.setDouble(3, quantity);
			ps.setString(4, uom);
			ps.setString(5, name);
			ps.setDouble(6, purchase_value);
			ps.setDouble(7, sell_value);
			
			return ps.executeUpdate();
			
	}
	
	public boolean insertNewSupplier(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO supplier "
				+ "VALUES (?, ?, ?, ?);"
			);
			ps.setLong(1, (long) data[0]);
			ps.setString(2, data[1].toString());
			ps.setString(3, data[2].toString());
			ps.setString(4, data[3].toString());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewSupplies(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO supplies "
				+ "VALUES (?, ?, ?, ?);"
			);
			ps.setLong(1, (long) data[0]);
			ps.setLong(2, (long) data[1]);
			ps.setDouble(3, (double) data[2]);
			ps.setDouble(4, (double) data[3]);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewPromo(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO promo "
				+ "VALUES (?, ?, ?, ?, ?, ?);"
			);
			ps.setLong(1, (long) data[0]);
			ps.setString(2, data[1].toString());
			ps.setString(3, data[2].toString());
			ps.setDouble(4, (double) data[3]);
			ps.setString(5, data[4].toString());
			ps.setString(6, data[5].toString());
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewTransaction(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO transaction "
				+ "VALUES (?, ?, ?, ?, ?, ?, NOW());"
			);
			ps.setLong(1, (long) data[0]);
			ps.setLong(2, (long) data[1]);
			ps.setLong(3, (long) data[2]);
			ps.setString(4, data[3].toString());
			ps.setDouble(5, (double) data[4]);
			ps.setDouble(6, (double) data[5]);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewSubTransaction(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO transacts "
				+ "VALUES (?, ?, ?, ?);"
			);
			ps.setLong(1, (long) data[0]);
			ps.setLong(2, (long) data[1]);
			ps.setDouble(3, (double) data[2]);
			ps.setDouble(4, (double) data[3]);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertNewCustomer(Object[] data) {
		try {
			ps = con.prepareStatement(
				  "INSERT INTO customer "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);"
			);
			ps.setLong(1, (long) data[0]);
			ps.setDouble(2, (double) data[1]);
			ps.setString(3, (String) data[2]);
			ps.setString(4, (String) data[3]);
			ps.setString(5, (String) data[4]);
			ps.setString(6, (String) data[5]);
			ps.setString(7, (String) data[6]);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateExistingEmployee(Object[] data) {
		// data format: id, position, fname, mname, lname, adress, basic, password
		// data index:   0,        1,     2,     3,     4,      5,     6,		 7
		if (data.length != 8) return false;
		
		try {
			ps = con.prepareStatement(
				  "UPDATE employee "
				+ "SET position=?, "
				+ "fname=?, "
				+ "mname=?, "
				+ "lname=?, "
				+ "address=?, "
				+ "basic=?, "
				+ "contributions=? "
				+ ((data[7] == null) ? "" : ", password=? ")
				+ "WHERE employee_id=? ;"
			);
			ps.setString(1, (String) data[1]);
			ps.setString(2, (String) data[2]);
			ps.setString(3, (String) data[3]);
			ps.setString(4, (String) data[4]);
			ps.setString(5, (String) data[5]);
			ps.setDouble(6, (double) data[6]);
			ps.setDouble(7, ((double) data[6] *
				(SSS_RATE + PHILHEALTH_RATE)) 
				+ PAGIBIG_RATE);
			if (data[7] == null) {
				ps.setLong(8, (long) data[0]);
			} else {
				ps.setString(8, (String) data[7]);
				ps.setLong(9, (long) data[0]);
			}
			ps.executeUpdate();
			return true;
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateProduct(long product_id, String[] columns, Object[] datas) {
		if (columns.length != datas.length) return false;
		
		int totalUpdated = 0;
		for (int index = 0; index < columns.length; index++) {
			try {
				ps = con.prepareStatement(
					String.format(
						"UPDATE product"
						+ " SET %s = ?"
						+ " WHERE product_id = ?;"
					, columns[index]));
				
				switch (columns[index]) {
					case "category": case "uom": case "name":
						ps.setString(1, datas[index].toString());
						break;
					case "quantity": case "purchase_value": case "sell_value":
						ps.setDouble(1, Double.parseDouble(datas[index].toString()));
						break;
				}
				
				ps.setLong(2, product_id);
				ps.executeUpdate();
				totalUpdated++;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return (totalUpdated == columns.length);
	}
	
	public boolean updateSupplier(Object[] data) {
		try {
			ps = con.prepareStatement(
				    "UPDATE supplier "
				  + "SET name=?, "
				  	  + "address=?, "
				  	  + "contact_no=? "
				  + "WHERE supplier_id=?;"
			);
			ps.setString(1, data[1].toString());
			ps.setString(2, data[2].toString());
			ps.setString(3, data[3].toString());
			ps.setLong(4, Long.parseLong(data[0].toString()));
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateCustomer(Object[] data) {
		try {
			ps = con.prepareStatement(
				    "UPDATE customer "
				  + "SET fname=?, "
				  	  + "mname=?, "
				  	  + "lname=?, "
				  	  + "address=?, "
				  	  + "contact_no=? "
				  + "WHERE customer_id=?;"
			);
			ps.setString(1, data[1].toString());
			ps.setString(2, (String) data[2]);
			ps.setString(3, data[3].toString());
			ps.setString(4, data[4].toString());
			ps.setString(5, data[5].toString());
			ps.setLong(6, Long.parseLong(data[0].toString()));
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean increaseProductStocks(long product_id, double amount) {
		try {
			ps = con.prepareStatement(
				    "UPDATE product "
				  + "SET quantity = quantity + ? "
				  + "WHERE product_id=?;"
			);
			ps.setDouble(1, amount);
			ps.setLong(2, product_id);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean decreaseProductStocks(long product_id, double amount) {
		try {
			ps = con.prepareStatement(
				    "UPDATE product "
				  + "SET quantity = quantity - ? "
				  + "WHERE product_id=?;"
			);
			ps.setDouble(1, amount);
			ps.setLong(2, product_id);
			
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteEntry(String table, String column, long id) {
		try {
			ps = con.prepareStatement(
				  "DELETE FROM " + table + " "
				+ "WHERE " + column + " = ? ;"
			);
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
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
	
	public boolean checkProduct(long product_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT product_id "
				+ "FROM product "
				+ "WHERE product_id=?;"
			);
			ps.setLong(1, product_id);
			ResultSet check = ps.executeQuery();
			check.next();
			// If this is an empty ResultSet if will throw error
			check.getLong(1);
			return true;
		} catch (SQLException | NumberFormatException e) {
			return false;
		}
	}
	
	public boolean checkSupplier(long supplier_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT supplier_id "
				+ "FROM supplier "
				+ "WHERE supplier_id=?;"
			);
			ps.setLong(1, supplier_id);
			ResultSet check = ps.executeQuery();
			check.next();
			// If this is an empty ResultSet if will throw error
			check.getLong(1);
			return true;
		} catch (SQLException | NumberFormatException e) {
			return false;
		}
	}
	
	public boolean checkCustomer(Object[] data) {
		try {
			ps = con.prepareStatement(
			    "SELECT COUNT(*) "
			  + "FROM customer "
			  + "WHERE fname=? "
			  + "AND mname=? "
			  + "AND lname=? "
			  + "AND address=? "
			  + "AND contact_no=?;"
			);
			ps.setString(1, (String) data[2]);
			ps.setString(2, (String) data[3]);
			ps.setString(3, (String) data[4]);
			ps.setString(4, (String) data[5]);
			ps.setString(5, (String) data[6]);
			
			ResultSet check = ps.executeQuery();
			check.next();
			// If this is an empty ResultSet if will throw error
			return (check.getInt(1) == 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int fetchManagers() {
		try {
			ps = con.prepareStatement(
				  "SELECT COUNT(employee_id) "
				+ "FROM employee "
				+ "WHERE position=?;"
			);
			ps.setString(1, "MANAGER");
			ResultSet count = ps.executeQuery();
			if (count.next()) return count.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Object[][] fetchDataQuery(String table, String column, String query, String sort, String order) {
		try {
			int size = fetchDataQueryCount(table, column, query);
			if (size != 0) {
				Object[][] data = new Object[size][11];
				ResultSet fetchData = stmt.executeQuery(
					  "SELECT * "
					+ "FROM " + table + " "
					+ "WHERE " + column + " "
					+ "LIKE \"%" + query + "%\" "
					+ "ORDER BY " + sort + " "
					+ order + ";"
				);
				
				int index = 0;
				while (fetchData.next()) {
					Object[] row = new Object[11];
					
					for (int index2 = 0; index2 < row.length; index2++) {
						try {
							row[index2] = fetchData.getObject(index2 + 1);
						} catch (SQLException e) {
							break;
						}
					}
					
					data[index] = row;
					index++;
				}
				
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int fetchDataQueryCount(String table, String column, String query) {
		try {
			ResultSet fetchCount = stmt.executeQuery(
				  "SELECT COUNT(" + column + ") "
				+ "FROM " + table + " "
				+ "WHERE " + column + " "
				+ "LIKE \"%" + query + "%\";"
			);
			fetchCount.next();
			return fetchCount.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public long[] fetchAllEmployeeID() {
		try {
			ps = con.prepareStatement(
				  "SELECT employee_id "
				+ "FROM employee;"
			);
			ResultSet rs = ps.executeQuery();
			ArrayList<Long> ids = new ArrayList<>();
			while (rs.next()) {
				ids.add(rs.getLong(1));
			}
			long[] longlist = new long[ids.size()];
			ids.forEach((id) -> longlist[ids.indexOf(id)] = id);
			
			return longlist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Key-sensitive query used for automatic-search when typing in the <br>
	 * update employee search bar. <p>
	 * The SQL Exception is not handled here. 
	 * @return null = if the query finds an error, Object[] = if the query fetches an ID
	 */
	public Object[] fetchEmployeeByID(long employee_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM employee "
				+ "WHERE employee_id = ?;"
			);
			ps.setLong(1, employee_id);
			ResultSet details = ps.executeQuery();
			details.next();
			if (details.getLong(1) != 0) {
				Object[] data = new Object[10];
				data[0] = details.getString(2);
				data[1] = details.getString(3);
				data[2] = details.getString(4);
				data[3] = details.getString(5);
				data[4] = details.getString(6);
				data[5] = details.getDouble(7);
				data[6] = details.getString(11);
				data[7] = details.getDouble(8);
				data[8] = details.getDouble(9);
				data[9] = details.getDouble(10);
				return data;
			}
		} catch (SQLException e) {}
		return null;
	}
	
	public Object[] fetchProductByID(long product_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM product "
				+ "WHERE product_id = ?;"
			);
			ps.setLong(1, product_id);
			Object[] data = new Object[7];
			ResultSet details = ps.executeQuery();
			details.next();
			
			data[0] = details.getLong(1);	// product id
			data[1] = details.getString(2);	// category
			data[2] = details.getDouble(3); // stock/qty
			data[3] = details.getString(4); // uom
			data[4] = details.getString(5); // name
			data[5] = details.getDouble(6); // purch price
			data[6] = details.getDouble(7); // sell price
			
			return data;
		} catch (SQLException e) {}
		return null;
	}
	
	public Object[][] fetchProductByKeyword(String keyword) {
		try {
			keyword = "%" + keyword + "%";
			ps = con.prepareStatement(
				  "SELECT COUNT(*) FROM product "
				+ "WHERE product_id LIKE ? "
				+ "OR category LIKE ? "
				+ "OR quantity LIKE ? "
				+ "OR uom LIKE ? "
				+ "OR name LIKE ? "
				+ "OR purchase_value LIKE ? "
				+ "OR sell_value LIKE ?;"
			);
			ps.setString(1, keyword);
			ps.setString(2, keyword);
			ps.setString(3, keyword);
			ps.setString(4, keyword);
			ps.setString(5, keyword);
			ps.setString(6, keyword);
			ps.setString(7, keyword);
			ResultSet count = ps.executeQuery();
			count.next();
			int size = count.getInt(1);
			
			ps = con.prepareStatement(
				  "SELECT * FROM product "
				+ "WHERE product_id LIKE ? "
				+ "OR category LIKE ? "
				+ "OR quantity LIKE ? "
				+ "OR uom LIKE ? "
				+ "OR name LIKE ? "
				+ "OR purchase_value LIKE ? "
				+ "OR sell_value LIKE ?;"
			);
			ps.setString(1, keyword);
			ps.setString(2, keyword);
			ps.setString(3, keyword);
			ps.setString(4, keyword);
			ps.setString(5, keyword);
			ps.setString(6, keyword);
			ps.setString(7, keyword);
			
			ResultSet data = ps.executeQuery();
			Object[][] products = new Object[size][7];
			int index = 0;
			while (data.next()) {
				Object[] row = new Object[7];
				row[0] = (long) data.getLong(1);
				row[1] = (String) data.getString(2);
				row[2] = (double) data.getDouble(3);
				row[3] = (String) data.getString(4);
				row[4] = (String) data.getString(5);
				row[5] = (double) data.getDouble(6);
				row[6] = (double) data.getDouble(7);
				products[index] = row;
				index++;
			}
			
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object[] fetchSupplierByID(long supplier_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM supplier "
				+ "WHERE supplier_id = ?;"
			);
			ps.setLong(1, supplier_id);
			ResultSet details = ps.executeQuery();
			details.next();
			if (details.getLong(1) != 0) {
				Object[] data = new Object[4];
				data[0] = details.getLong(1);
				data[1] = details.getString(2);
				data[2] = details.getString(3);
				data[3] = details.getString(4);
				return data;
			}
		} catch (SQLException e) {}
		return null;
	}
	
	public Object[] fetchCustomerByID(long customer_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM customer "
				+ "WHERE customer_id = ?;"
			);
			ps.setLong(1, customer_id);
			ResultSet details = ps.executeQuery();
			details.next();
			if (details.getLong(1) != 0) {
				Object[] data = new Object[7];
				data[0] = details.getLong(1);
				data[1] = details.getDouble(2);
				data[2] = details.getString(3);
				data[3] = details.getString(4);
				data[4] = details.getString(5);
				data[5] = details.getString(6);
				data[6] = details.getString(7);
				return data;
			}
		} catch (SQLException e) {}
		return null;
	}
	
	public ArrayList<ArrayList<ArrayList<Object>>> fetchPromos() {
		try {
			ArrayList<ArrayList<Object>> activePromos = new ArrayList<>();
			ArrayList<ArrayList<Object>> inactivePromos = new ArrayList<>();
			
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM promo "
				+ "WHERE end_date > now();"
			);
			ResultSet active = ps.executeQuery();
			active.next();
			try {
				do {
					ArrayList<Object> row = new ArrayList<>();
					row.add(active.getLong(1));
					row.add(active.getString(2));
					row.add(active.getString(3));
					row.add(active.getDouble(4));
					row.add(active.getString(5));
					row.add(active.getString(6));
					activePromos.add(row);
					
				} while (active.next());
			} catch (SQLException e) {}
			
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM promo "
				+ "WHERE end_date <= now();"
			);
			ResultSet inactive = ps.executeQuery();
			inactive.next();
			try {
				do {
					ArrayList<Object> row = new ArrayList<>();
					row.add(inactive.getLong(1));
					row.add(inactive.getString(2));
					row.add(inactive.getString(3));
					row.add(inactive.getDouble(4));
					row.add(inactive.getString(5));
					row.add(inactive.getString(6));
					inactivePromos.add(row);
				} while (inactive.next());
			} catch (SQLException e) {}
			
			ArrayList<ArrayList<ArrayList<Object>>> data = new ArrayList<>();
			data.add(activePromos);
			data.add(inactivePromos);
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
			for (int type = 0; type <= 9; type++) {
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
	
	public long fetchLastEntryByTable(String table, String column) {
		try {
			ps = con.prepareStatement(
				  "SELECT MAX(" + column + ") "
				+ "FROM " + table + ";"
			);
			
			ResultSet pid = ps.executeQuery();
			pid.next();
			if (pid.getLong(1) != 0) return pid.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String[] fetchProductCategories() {
		try {
			ps = con.prepareStatement(
				  "SELECT DISTINCT category "
				+ "FROM product "
				+ "ORDER BY category;"
			);
			ResultSet categories = ps.executeQuery();
			ArrayList<String> temp = new ArrayList<>();
			while (categories.next()) {
				temp.add(categories.getString(1));
			}
			if (temp.toArray(new String[0]).length == 0)
				return new String[] {"--No existing record--"};
			
			return temp.toArray(new String[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object[] fetchPromoByID(long product_id) {
		try {
			ps = con.prepareStatement(
				  "SELECT * "
				+ "FROM promo "
				+ "WHERE product_id=? "
				+ "AND end_date > now() "
				+ "LIMIT 1;"
			);
			ps.setLong(1, product_id);
			ResultSet promo = ps.executeQuery();
			promo.next();
			
			Object[] details = new Object[6];
			details[0] = (String) promo.getString(2); // name
			details[1] = (String) promo.getString(3); // condition
			details[2] = (double) promo.getDouble(4); // discount
			
			return details;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public int fetchTotalCount() {
		try {
			ps = con.prepareStatement(
				"SELECT COUNT(product_id) FROM product;"
			);
			ResultSet count = ps.executeQuery();
			count.next();
			return count.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double fetchPurchaseValueByName(String name) {
		if (name == null) return -1;
		try {
			ps = con.prepareStatement(
				"SELECT purchase_value FROM product"
				+ " WHERE name=?;"
			);
			ps.setString(1, name);
			ResultSet value = ps.executeQuery();
			if (value.next()) return value.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double fetchSellValueByName(String name) {
		if (name == null) return -1;
		try {
			ps = con.prepareStatement(
				"SELECT sell_value FROM product"
				+ " WHERE name=?;"
			);
			ps.setString(1, name);
			ResultSet value = ps.executeQuery();
			if (value.next()) return value.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String fetchMaxPurchaseValue() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE purchase_value = ("
					+ "SELECT MAX(purchase_value) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchMinPurchaseValue() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE purchase_value = ("
					+ "SELECT MIN(purchase_value) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchMaxSellValue() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE sell_value = ("
					+ "SELECT MAX(sell_value) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchMinSellValue() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE sell_value = ("
					+ "SELECT MIN(sell_value) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchProductMaxStock() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE quantity = ("
					+ "SELECT MAX(quantity) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchProductMinStock() {
		try {
			ps = con.prepareStatement(
				"SELECT name FROM product"
				+ " WHERE quantity = ("
					+ "SELECT MIN(quantity) "
					+ "FROM product"
					+ ");"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public double fetchSumQuantity() {
		try {
			ps = con.prepareStatement(
				"SELECT SUM(quantity) FROM product;"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String fetchCategoryMostProduct() {
		String[] categories = fetchProductCategories();
		if (categories == null) return null;
		
		String category = "";
		int max = 0;
		
		for (String c : categories) {
			try {
				ps = con.prepareStatement(
					"SELECT COUNT(product_id) "
					+ " FROM product"
					+ " WHERE category=?"
				);
				ps.setString(1, c);
				ResultSet candidate = ps.executeQuery();
				if (candidate.next()) {
					if (max == 0 || candidate.getInt(1) >= max) {
						category = c;
						max = candidate.getInt(1);
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return (category == "") ? null : category;
	}
	
	public String fetchCategoryLeastProduct() {
		String[] categories = fetchProductCategories();
		if (categories == null) return null;
		
		String category = "";
		int min = 0;
		
		for (String c : categories) {
			try {
				ps = con.prepareStatement(
					"SELECT COUNT(product_id) "
					+ " FROM product"
					+ " WHERE category=?"
				);
				ps.setString(1, c);
				ResultSet candidate = ps.executeQuery();
				if (candidate.next()) {
					if (min == 0 || candidate.getInt(1) <= min) {
						category = c;
						min = candidate.getInt(1);
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return (category == "") ? null : category;
	}
	
	public double fetchAveragePurchasePrice() {
		try {
			ps = con.prepareStatement(
				"SELECT AVG(purchase_value) FROM product;"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double fetchAverageSellingPrice() {
		try {
			ps = con.prepareStatement(
				"SELECT AVG(sell_value) FROM product;"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double fetchSumProductPurchase() {
		try {
			ps = con.prepareStatement(
				"SELECT SUM(purchase_value * quantity) FROM product;"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public double fetchSumProductSell() {
		try {
			ps = con.prepareStatement(
				"SELECT SUM(sell_value * quantity) FROM product;"
			);
			ResultSet name = ps.executeQuery();
			if (name.next()) return name.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}