package jp.arcanum.marubatu.com.utl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import jp.arcanum.marubatu.com.page.AbstractPage;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class MySession extends WebSession{


	/**
	 * コンストラクタ
	 * @param request リクエスト情報
	 */
	public MySession(Request request){
		super(request);
	}

	/**
	 * 試合情報。
	 */
	private Game _gameinfo = new Game();
	public final Game getGameInfo(){
		return _gameinfo;
	}

	/**
	 * ユーザ情報リスト。
	 */
	private UserInfo[] _users = new UserInfo[2];
	public final UserInfo getUserInfo(final int index){
		return _users[index];
	}
	public void setUserInfo(final UserInfo user1, final UserInfo user2){
		_users[0] = user1;
		_users[1] = user2;
	}

	/**
	 * 現在のターンは誰か。　０または１
	 */
	private int _turnindex;
	public void setTurnIndex(final int turnindex){
		_turnindex = turnindex;
	}
	public void changeTurn(){
		if(_turnindex == 0){
			_turnindex = 1;
		}
		else{
			_turnindex =0;
		}
	}
	public int getTurnIndex(){
		return _turnindex;
	}

	/**
	 * コンピュータ側のターンかどうか
	 */
	private boolean _comturn;
	public void setComTurn(boolean comturn){
		_comturn = comturn;
	}
	//public boolean getComTurn(){
	//	return _comturn;
	//}




	/**
	 * 盤面情報
	 * 0:何もおいていない
	 * 1:○
	 * 2:×
	 */
	private int[] _board = {0,0,0,0,0,0,0,0,0,0,};

	public void setStone(final int num, final int flg){
		_board[num] = flg;
	}
	public int[] getBoard(){
		return _board;
	}

	/**
	 * いま、ターンを持っている人のマーク
	 */
	/*
	private int _mark =1;
	public void setNextMark(final int mark){
		_mark = mark;
	}
	*/

	public final int getMark(){
		return getUserInfo(getTurnIndex()).getMark();
	}


	/**
	 * ゲーム中かどうか
	 * 0	ゲームしていない
	 * 1	ゲーム中
	 * 4	ゲームオーバー（引き分け）
	 * 5	ゲームオーバー（１Win）
	 * 6	ゲームオーバー（２Win）
	 */
	private int _gamestatus;
	public final int getGameStatus(){
		return _gamestatus;
	}
	public void setGameStatus(final int gaming){
		_gamestatus = gaming;
	}


	/**
	 * 0	Draw
	 * 1	１Win
	 * 2	２Win
	 * @param winner
	 */
	public final void gameOver(final int winner){

		setGameStatus(winner | 4);

		//　ゲーム数
		//addGameCount();
		_gameinfo.addGameCount();

		// 同じ試合情報はひとまとまりにする
		boolean exist = false;
		List<Turn> turnlist = getGameInfo().getGameHistory();
		for(int i=0; i<turnlist.size(); i++){
			Turn wkturn = turnlist.get(i);
			if(wkturn.toString().equals(_turn.toString())){
				exist = true;
				wkturn.addDuplicateCount();
				break;
			}
		}

		if(!exist){
			_turn.setWinMark(winner);
			turnlist.add(_turn);
		}

	}

	/**
	 * ゲーム初回ターンの保持者（０，１）
	 */
	private int _initturn;
	public void setInitTurn(final int initturn){
		_initturn = initturn;
	}
	public final int getInitTurn(){
		return _initturn;
	}

	/**
	 * メニューからきたときの初期化
	 * @param initturn
	 */
	public void newSession(final int player1type){

		UserInfo user1 = new UserInfo(player1type);
		user1.setMark(UserInfo.MARK_MARU);
		UserInfo user2 = new UserInfo(UserInfo.TYPE_COM);
		user2.setMark(UserInfo.MARK_BATU);
		setUserInfo(user1, user2);

		//　その他ゲームの初期化
		_gameinfo.initGame();
		setGameStatus(1);
		_board = new int[]{0,0,0,0,0,0,0,0,0,0,};
		_turn = null;

	}

	/**
	 * ゲーム初期化
	 * @param initturn
	 */
	public void initGame(){
		setGameStatus(1);
		_board = new int[]{0,0,0,0,0,0,0,0,0,0,};
		_turn = null;

		//　プレイヤー１と２のマークを反転
		UserInfo user1 = getUserInfo(0);
		UserInfo user2 = getUserInfo(1);
		if(user1.getMark() == UserInfo.MARK_MARU){
			user1.setMark(UserInfo.MARK_BATU);
			user2.setMark(UserInfo.MARK_MARU);
		}
		else{
			user1.setMark(UserInfo.MARK_MARU);
			user2.setMark(UserInfo.MARK_BATU);
		}

		//　次のゲームの初回ターンを決定
		if(getInitTurn() == 0){
			setTurnIndex(1);
		}
		else{
			setTurnIndex(0);
		}

		//　初回ターン保持者を変更
		if(getInitTurn() == 0){
			setInitTurn(1);
		}
		else{
			setInitTurn(0);
		}
	}

	/**
	 * この試合のターン情報
	 */
	private Turn _turn;
	public void addThisTurn(final Turn turn){
		if(_turn == null){
			_turn = turn;
		}
		else{
			_turn.addTurn(turn);
		}
	}
	public final Turn getTurn(){
		return _turn;
	}




}
