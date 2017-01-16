package base.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	public static Connection getConn() {
		java.sql.Connection conn = null;
		try {
			Class.forName(PropUtil.getInstance().getProperty("jdbc.driverClassName"));
			conn = DriverManager.getConnection(PropUtil.getInstance()
					.getProperty("jdbc.url"),
					PropUtil.getInstance().getProperty("jdbc.username"), PropUtil
							.getInstance().getProperty("jdbc.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}