package base.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.alibaba.druid.pool.DruidDataSource;


/**
 * druid 数据源
 * 
 * @author xiao
 * 
 */
public class DruidConnection extends DefaultConnection {

	DruidDataSource dataSource;

	@Override
	public DruidDataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}


	@Override
	public Connection getConn() {
		return super.getConn();
	}


	@Override
	public Connection getConn(String user, String pass) {
		return super.getConn(user, pass);
	}


	@Override
	public void close(PreparedStatement ps, Connection conn) {
		super.close(ps, conn);
	}


	@Override
	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		super.close(rs, ps, conn);
	}
	
	

}