package jp.arcanum.marubatu.com.utl;

import java.io.Serializable;

public class Turn implements Serializable{

	/**
	 *
	 */
	private Turn _prev;

	/**
	 *
	 */
	private Turn _next;

	/**
	 * おいたセル番号（１～９）
	 */
	private int _cellnum;
	public final int getCellNum(){
		return _cellnum;
	}

	/**
	 * 置いた石（1=○、2=×）
	 */
	private int _mark;

	/**
	 * 最終的に勝った石
	 */
	private int _winmark;

	/**
	 * 重複数
	 */
	private int _dupcount = 0;
	public void addDuplicateCount(){
		if(_next == null){
			_dupcount++;
		}
		else{
			_next.addDuplicateCount();
		}
	}
	public final int getDuplicateCount(){
		if(_next == null){
			return _dupcount;
		}
		else{
			return _next.getDuplicateCount();
		}
	}


	/**
	 * コンストラクタ
	 * このクラスはイミュータブルクラス
	 * @param cellnum
	 * @param mark
	 */
	public Turn(final int cellnum, final int mark){
		_cellnum = cellnum;
		_mark = mark;

	}

	public void addTurn(final Turn turn){
		if(_next == null){
			_next = turn;
		}
		else{
			_next.addTurn(turn);
		}
	}

	/**
	 * 最終的に勝ったマークを設定
	 * @param winmark
	 */
	public void setWinMark(int winmark){

		if(_next == null){
			_winmark = winmark;
		}
		else{
			_next.setWinMark(winmark);
		}

	}

	/**
	 * 最終的に勝った石を取得
	 * @return
	 */
	public final int getWinMark(){
		int ret = 0; // draw
		if(_next == null){
			ret = _winmark;
		}
		else{
			ret = _next.getWinMark();
		}
		return ret;
	}



	/**
	 * 次の１手を考える
	 * @return
	 */
	public int getNextTurn(){
		return 0;
	}

	/**
	 * 試合の流れを文字列化する
	 * @param turn
	 * @return
	 */
	public final String toString(){
		String ret = "";

		ret = ret + _cellnum + ",";
		if(_next != null){
			ret = ret + _next.toString();
		}

		return ret;
	}


	/**
	 * この試合に最終的に勝つかどうか
	 * @return
	 */
	public final boolean willWin(int mark){
		int winmark = getWinMark();
		if(winmark == mark){
			return true;
		}
		return false;
	}

	public final int getLastTurn(int seed){
		if(_next == null){
			return seed;
		}
		else{
			seed++;
			return _next.getLastTurn(seed);
		}
	}

	public final Turn getTurn(int seed){
		Turn ret = null;
		if(seed == 0){
			ret = this;
		}
		else{
			seed--;
			if(_next != null){
				ret = _next.getTurn(seed);
			}
		}
		return ret;
	}

}
