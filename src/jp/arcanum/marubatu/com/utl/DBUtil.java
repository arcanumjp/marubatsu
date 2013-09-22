package jp.arcanum.marubatu.com.utl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.arcanum.marubatu.com.dao.AbstractSqlSelectable;

public class DBUtil {

	
	public static final Connection getConnection(){

		Connection ret = null;
		try {
			
			Class.forName(Util.getProperties("database.class"));
			
			// TODO ここはコネクションプールから持ってくるようにする
			ret = DriverManager.getConnection(
					Util.getProperties("database.url"),
					Util.getProperties("database.user"),
					Util.getProperties("database.pass" )
			);
		} 
		catch (Exception e) {
			throw new RuntimeException("SELECTに失敗", e);
		}
		
		return ret;
	}
	
	
	public static final void commit(Connection con){
		try {
			con.commit();
		} catch (SQLException e) {
			throw new RuntimeException("コミットに失敗");
		}
	}
}
