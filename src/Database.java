import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
	
	private String url, user, pass, db;
	
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	
	private Utility ut;
	
	Database(Utility ut) {
		this.ut = ut;
		this.url = ut.getConfig("db_url");
		this.user = ut.getConfig("db_user");
		this.pass = ut.getConfig("db_pass");
		this.db = ut.getConfig("db_name");
		
		try {
			con = DriverManager.getConnection(url, user, pass);
			stmt = con.createStatement();
			createDatabase();
			createTable();
		} catch (SQLException e) {
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
	
	public void createTable() throws SQLException {
		// todo
	}
	
}