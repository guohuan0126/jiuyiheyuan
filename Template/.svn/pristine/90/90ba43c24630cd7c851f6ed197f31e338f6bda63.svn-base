package base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * @author xiao
 * 
 */
public interface DBConnection {


	/**
	 * 获取数据库连接
	 * @return
	 */
	Connection getConn();	
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	Connection getConn(String user, String pass);	
	
	/**
	 * 关闭连接
	 * @param ps
	 * @param conn
	 */
	void close(PreparedStatement ps, Connection conn);
	
	/**
	 * 关闭连接
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	void close(ResultSet rs, PreparedStatement ps, Connection conn);
}
