package base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


	/**
	 * 
	 * @author xiao
	 * 
	 */
	abstract class DefaultConnection implements DBConnection {

	/**
	 * 数据源
	 */
	abstract DataSource getDataSource();


	/**
	 * 获取数据库连接
	 */
	public Connection getConn(){
		try {			
			return (Connection) getDataSource().getConnection();
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}  		    
	}
	
	/**
	 * 获取数据库连接
	 */
	public Connection getConn(String user, String pass){
		try {			
			return (Connection) getDataSource().getConnection(user, pass);
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}  		    
	}

	/**
	 * 关闭数据库连接
	 */
	public void close(PreparedStatement ps, Connection conn) {

		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 关闭数据库连接
	 */
	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
