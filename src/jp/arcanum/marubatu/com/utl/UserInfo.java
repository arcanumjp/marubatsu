package jp.arcanum.marubatu.com.utl;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * ユーザ種別・人
	 */
	public static final int TYPE_USER = 1;

	/**
	 * ユーザ種別・COM
	 */
	public static final int TYPE_COM = 2;

	/**
	 * マーク・○
	 */
	public static final int MARK_MARU = 1;

	/**
	 * マーク・×
	 */
	public static final int MARK_BATU = 2;


	/**
	 * ユーザ種別
	 */
	private int _usertype;
	public final int getUserType(){
		return _usertype;
	}
	public void setUserType(final int usertype){
		_usertype = usertype;
	}


	private int _mark;
	public final void setMark(final int mark){
		_mark = mark;
	}
	public final int getMark(){
		return _mark;
	}

	/**
	 * コンストラクタ
	 * @param usertype
	 */
	public UserInfo(final int usertype){
		_usertype = usertype;
	}




}
