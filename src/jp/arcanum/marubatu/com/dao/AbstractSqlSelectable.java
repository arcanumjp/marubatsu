package jp.arcanum.marubatu.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.arcanum.marubatu.com.utl.Util;

public abstract class AbstractSqlSelectable extends AbstractSql{

	/**
	 * レコード
	 */
	private Map _record = new HashMap();
	public void put(final String key, final String value){
		_record.put(key, value);
	}
	public String get(final String key){
		return (String)_record.get(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public List execute(Connection con){
		
		List ret = new ArrayList();
		
		
		// このクラスがダミーリスト指定か？
		String prop = Util.getProperties("dev." + getClass().getName() + "dummylist");
		if(prop != null){
			return getDummyList();
		}
		
		//　ＳＱＬの編集
		clearParams();
		String sql = getSql();

		ResultSet result = null;
		try {
			
			PreparedStatement pst = con.prepareStatement(sql);
			for(int i = 0 ; i < getParams().size(); i++){
				pst.setObject(i+1, getParams().get(i));
			}

        	result = pst.executeQuery();

        	while(result.next()){
        		AbstractSqlSelectable record = getInstance();
        		ResultSetMetaData meta = result.getMetaData();
        		for(int i = 1 ; i < meta.getColumnCount()+1; i++){
        			String colname = meta.getColumnName(i);
        			String value   = result.getString(i);
        			record.put(colname, value);
        			
        		}
        		ret.add(record);
        	}
			
		} 
		catch (Exception e) {
			throw new RuntimeException("SELECTに失敗", e);
		}
		
		return ret;
	}

	private AbstractSqlSelectable getInstance(){
		AbstractSqlSelectable ret = null;
		
		try{
			
			Class clazz = getClass();
			ret = (AbstractSqlSelectable)clazz.newInstance();
			
		}
		catch(Exception e){
			throw new RuntimeException("しっぱーい", e);
		}
		
		return ret;
	}
	
}
