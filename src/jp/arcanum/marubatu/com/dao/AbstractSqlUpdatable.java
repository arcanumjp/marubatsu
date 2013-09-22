package jp.arcanum.marubatu.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlUpdatable extends AbstractSql{


	public int execute(){

		int ret = 0;

		clearParams();

		String sql = getSql();

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");

			con = DriverManager.getConnection("dbのURL","user","password");
			PreparedStatement pst = con.prepareStatement(sql);
			for(int i = 0 ; i < getParams().size(); i++){
				pst.setObject(i+1, getParams().get(i));
			}

        	ret = pst.executeUpdate();

		}
		catch (Exception e) {
			throw new RuntimeException("更新系SQLに失敗", e);
		}

		return ret;
	}

	/**
	 * ｓｑｌを取得（サブクラスでオーバーライドする）
	 * @return 編集されたｓｑｌ
	 */
	public abstract String getSql();


}
