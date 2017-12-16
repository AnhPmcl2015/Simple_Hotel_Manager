package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
	private static Connection conn;

	public static Connection getConnection(String serverName, String databaseName) {
		//servername: DESKTOP-7G62O4Q
		//databaseName: HotelManager
		conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://"+serverName+":1433;databaseName="+databaseName+";integratedSecurity=true;";
			conn = DriverManager.getConnection(url);
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println("SQL Exception: " + e.toString());
		}
		catch(ClassNotFoundException e1) {
			System.err.println("Class Not Found Exception: " + e1.toString());
		}
		return conn;
	}
}
