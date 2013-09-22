package jp.arcanum.marubatu.com.utl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Game implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public Game(){

	}

	public void initGame(){
		_gamecount = 0;

	}

	/**
	 * 戦歴リスト
	 */
	private List<Turn> _gamehistory = new ArrayList<Turn>();
	public void setGameHistory(final List<Turn> turnlist){
		_gamehistory = turnlist;
	}
	public final List<Turn> getGameHistory(){
		return _gamehistory;
	}
	public void addGameHistory(final Turn turn){
		_gamehistory.add(turn);
	}

	/**
	 * COM勝利数
	 */
	private int _comwincount;
	public void setComWinCount(final int comwincount){
		_comwincount = comwincount;
	}
	public final int getComWinCount(){
		return _comwincount;
	}
	public void addComGameCount(){
		_comwincount++;
	}
	/**
	 * 勝率を取得
	 * @return 配列０にプレイヤー１、配列１にプレイヤー２
	 */
	public String[] getWinRate(){

		return null;
	}

	/**
	 * 試合数
	 */
	private int _gamecount;
	public void setGameCount(final int gamecount){
		_gamecount = gamecount;
	}
	public final int getGameCount(){
		return _gamecount;
	}
	public final void addGameCount(){
		_gamecount++;
	}







	public final List<Turn> getLikeTurnList(final Turn turn){
		List<Turn> ret = new ArrayList<Turn>();

		if(turn == null){
			return ret;
		}

		TreeMap<String, Turn> sorted = new TreeMap<String, Turn>();
		List<Turn> turnlist = getGameHistory();
		for(int i=0; i<turnlist.size(); i++){
			Turn wkturn = turnlist.get(i);
			String turnstr   = turn.toString();
			String wkturnstr = wkturn.toString();
			if(wkturnstr.startsWith(turnstr)){
				//ret.add(wkturn);
				String key = "0000000" + wkturn.getDuplicateCount();
				key = key.substring(key.length()-7);
				key = key + "_" + wkturn.toString();

				sorted.put(key, wkturn);
			}
		}

		Set keyset = sorted.keySet();
		Iterator<Turn> keyite = keyset.iterator();
		while(keyite.hasNext()){
			Object o = keyite.next();
			ret.add(sorted.get(o));
		}

		return ret;
	}

	public final List<Turn> getSortedTurnListByWin(final int mark){
		List<Turn> ret = new ArrayList<Turn>();

		TreeMap<String, Turn> sorted = new TreeMap<String, Turn>();
		List<Turn> turnlist = getGameHistory();
		for(int i=0; i<turnlist.size(); i++){
			Turn turn = turnlist.get(i);
			String key = "0000000" + turn.getDuplicateCount();
			key = key.substring(key.length()-7);
			key = key + "_" + turn.toString();

			sorted.put(key, turn);
		}


		Set keyset = sorted.keySet();
		Iterator<Turn> keyite = keyset.iterator();
		while(keyite.hasNext()){
			Object o = keyite.next();
			ret.add(sorted.get(o));
		}

		return ret;
	}


}

