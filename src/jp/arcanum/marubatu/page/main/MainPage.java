package jp.arcanum.marubatu.page.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.arcanum.marubatu.com.page.AbstractPage;
import jp.arcanum.marubatu.com.utl.MySession;
import jp.arcanum.marubatu.com.utl.SelectOption;
import jp.arcanum.marubatu.com.utl.Turn;
import jp.arcanum.marubatu.com.utl.UserInfo;
import jp.arcanum.marubatu.page.index.IndexPage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;


public class MainPage extends AbstractPage implements IHeaderContributor{

	// TODO debug
	private Label    _tostring = new Label("tostring", new Model(""));
	private TextArea _turnlist = new TextArea("turnlist",new Model(""));





	private MaruBatuLink _cell1 = new MaruBatuLink(1){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell2 = new MaruBatuLink(2){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell3 = new MaruBatuLink(3){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell4 = new MaruBatuLink(4){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell5 = new MaruBatuLink(5){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell6 = new MaruBatuLink(6){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell7 = new MaruBatuLink(7){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell8 = new MaruBatuLink(8){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};
	private MaruBatuLink _cell9 = new MaruBatuLink(9){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onClick() {
			putMark(this);
		}
	};

	private Button _comturn = new Button("comturn"){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onSubmit() {
			comTurn();
		}
	};

	private Button _restart = new Button("restart"){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onSubmit() {
			restartGame();
		}

	};
	private Button _quite = new Button("quite"){
		private static final long serialVersionUID = 7308614583288211827L;
		public void onSubmit() {
			quiteGame();
		}

	};

	private Label _turninfo = new Label("turninfo", new Model("")){
		private static final long serialVersionUID = 7308614583288211827L;
		protected void onBeforeRender() {
			MySession sess = (MySession)getSession();

			if(sess.getGameStatus() != 1){
				setModelObject("");
			}
			else{
				String markstr = "○";
				if(sess.getMark() == UserInfo.MARK_BATU){
					markstr = "×";
				}
				String playerstr = "YOU!";
				if(sess.getUserInfo(sess.getTurnIndex()).getUserType() == UserInfo.TYPE_COM){
					playerstr = "COM!";
				}
				setModelObject(playerstr + "(" + markstr + ")");
			}
			super.onBeforeRender();
		}
	};

	/**
	 * ゲーム数
	 */
	private Label _gamecount = new Label("gamecount", new Model("")){
		private static final long serialVersionUID = 7308614583288211827L;
		protected void onBeforeRender() {

			MySession sess = (MySession)getSession();

			int gamecount = sess.getGameInfo().getGameCount();
			if(gamecount == 0){
				setModelObject("初めてのゲーム");
			}
			else{
				setModelObject(" " + gamecount + "　回");
			}
			super.onBeforeRender();

		}

	};

	/**
	 * COM勝率
	 */
	private Label _comwinrate = new Label("comwinrate", new Model("")){
		private static final long serialVersionUID = 7308614583288211827L;
		protected void onBeforeRender() {

			MySession sess = (MySession)getSession();

			if(sess.getGameStatus() != 1){

				int gamecount = sess.getGameInfo().getGameCount();
				int comwincnt = sess.getGameInfo().getComWinCount();
				double rate = (comwincnt / gamecount)*100;
				int irate = (int)rate;


				setModelObject(" " + irate + " %");

			}
			super.onBeforeRender();

		}

	};

	private static final List<SelectOption> CHOICES = new ArrayList<SelectOption>(){
		{
			add(new SelectOption(String.valueOf(UserInfo.TYPE_USER), "人間"));
			add(new SelectOption(String.valueOf(UserInfo.TYPE_COM), "コンピュータ"));
		}
	};

	private DropDownChoice _selectplayer = new DropDownChoice(
													"selectplayer",
													CHOICES,
													new ChoiceRenderer("value","code")
													){
		{
			setModel(new Model(CHOICES.get(0)));
			setRequired(true);
		}
	};

	private WebMarkupContainer _panel = new WebMarkupContainer("panel");

	/**
	 * １｜２｜３
	 * ４｜５｜６
	 * ７｜８｜９
	 */
	private static final int HANTEI_WIN[][] = {
		{1,2,3},
		{4,5,6},
		{7,8,9},
		{1,4,7},
		{2,5,8},
		{3,6,9},
		{1,5,9},
		{3,5,7},
	};




	private static final Map<String, String> HANTEI_LIKE = new HashMap<String, String>();
	{
		HANTEI_LIKE.put("011", "0");
		HANTEI_LIKE.put("101", "1");
		HANTEI_LIKE.put("110", "2");
		HANTEI_LIKE.put("022", "0");
		HANTEI_LIKE.put("202", "1");
		HANTEI_LIKE.put("220", "2");
		System.out.println("さいず " + HANTEI_LIKE.keySet().toString());
	}

	/**
	 * 入力されたマークが勝つ寸前かどうかの、パターン判定
	 * @param mark
	 * @return
	 */
	private final int willWin(final int mark){
		int ret = 0;

		MySession sess = (MySession)getSession();
		int[] board = sess.getBoard();

		for(int i=0; i<HANTEI_WIN.length; i++){

			//　判定用の文字列を作成
			String wkhantei = "";
			for(int j=0; j<HANTEI_WIN[i].length; j++){
				wkhantei = wkhantei + board[HANTEI_WIN[i][j]];
			}
			//System.out.println("判定用      " + wkhantei);

			// パターン判定を行う
			String hanteistr = HANTEI_LIKE.get(wkhantei);
			//System.out.println("ある？      " + hanteistr);
			if(hanteistr == null){
				//System.out.println("    ⇒いや、無い・・・      " + hanteistr);
				continue;
			}

			//　見つかった場合は、その値分増分したのが、勝つマーク
			ret = HANTEI_WIN[i][Integer.parseInt(hanteistr)];
			System.out.println("勝つ手・・・" + ret);
		}

		return ret;
	}

	private final int getReachCount(final int mark){
		int ret = 0;

		MySession sess = (MySession)getSession();
		int[] board = sess.getBoard();

		for(int i=0; i<HANTEI_WIN.length; i++){

			//　判定用の文字列を作成
			String wkhantei = "";
			for(int j=0; j<HANTEI_WIN[i].length; j++){
				wkhantei = wkhantei + board[HANTEI_WIN[i][j]];
			}
			//System.out.println("判定用      " + wkhantei);

			// パターン判定を行う
			String hanteistr = HANTEI_LIKE.get(wkhantei);
			//System.out.println("ある？      " + hanteistr);
			if(hanteistr != null){
				ret++;
			}

		}

		return ret;
	}

	private final static int STATUS_NONE = 0;
	private final static int STATUS_GAMMING = 1;
	private final static int STATUS_DRAW = 4;
	private final static int STATUS_PLAYER1WIN = 5;
	private final static int STATUS_COMWIN = 6;

	/**
	 * ゲームを始めたときの始めのターンの人（１＝人、２＝コンピュータ）
	 */
	//private int _initturn;


	public MainPage(final PageParameters params){

		//===================================================================
		// TODO debug
		addForm(_tostring);
		addForm(_turnlist);
		//===================================================================

		MySession sess = (MySession)getSession();

		int usertype = params.getInt("USERTYPE");


		addForm(_cell1);
		addForm(_cell2);
		addForm(_cell3);
		addForm(_cell4);
		addForm(_cell5);
		addForm(_cell6);
		addForm(_cell7);
		addForm(_cell8);
		addForm(_cell9);

		addForm(_turninfo);

		addForm(_gamecount);
		addForm(_comwinrate);

		addForm(_comturn);

		addForm(_panel);
		//addForm(_restart);
		//addForm(_quite);
		//addForm(_selectplayer);
		_selectplayer.setModelObject(CHOICES.get(usertype-1));
		_panel.add(_restart);
		_panel.add(_quite);
		_panel.add(_selectplayer);
		_panel.setVisible(false);

		//　指定された回数シミュレーション

		int simcount = 0;
		if(params.get("SIMCOUNT") != null){
			simcount = params.getInt("SIMCOUNT");

			sess.newSession(UserInfo.TYPE_COM);
			for(int i=0; i<simcount; i++){

				sess.initGame();

				//　マークのリセット
				for(int j=1; j<10; j++){
					MaruBatuLink link = (MaruBatuLink)_form.get("cell" + j);
					link.reset();
				}

				while(true){
					comTurn();
					if(sess.getGameStatus()>1){
						break;
					}
				}

			}

			restartGame();
		}

		//　ゲームの各種フラグの初期化

		sess.newSession(usertype);

	}


	private void putMark(final MaruBatuLink comp){

		MySession sess = (MySession)getSession();

		// セッションの盤面に石を置く
		sess.setStone(comp.getNum(), sess.getMark());
		//　セルに石の情報を付加
		comp.setMark(sess.getMark());
		//　ターンを保存
		Turn turn = new Turn(comp.getNum(), sess.getMark());
		sess.addThisTurn(turn);

		//===================================================================
		// TODO debug
		_tostring.setModelObject(sess.getTurn().toString());
		String turninfo = "";
		List<Turn> tlist = sess.getGameInfo().getGameHistory();
		for(int i=0; i<tlist.size(); i++){
			Turn wkturn = tlist.get(i);
			turninfo = turninfo + wkturn.toString() + "\n";
		}
		_turnlist.setModelObject(turninfo);
		//===================================================================


		//　勝敗判定
		for(int i=1; i<3; i++){

			for(int j=0; j< HANTEI_WIN.length; j++){

				boolean win = true;
				for(int k=0; k<HANTEI_WIN[j].length; k++){
					if(sess.getBoard()[HANTEI_WIN[j][k]] != sess.getMark()){
						win = false;
						break;
					}
				}

				if(win){

					//　３つ並んだ石の色を変更
					for(int k=0; k<HANTEI_WIN[j].length; k++){
						MaruBatuLink chcom = (MaruBatuLink)_form.get("cell" + HANTEI_WIN[j][k]);
						chcom.setMark(sess.getMark()|4);
					}

					//　セッションの情報を設定
					//sess.gameOver(sess.getMark());
					if(sess.getTurnIndex() == 0){
						sess.gameOver(1);
					}
					else{
						sess.gameOver(2);
					}

					//　勝ったので重みを付ける
					turn.addDuplicateCount();
					turn.addDuplicateCount();

					//　もう一回ボタンとか表示して終了
					//_restart.setVisible(true);
					//_quite.setVisible(true);
					_panel.setVisible(true);
					return;

				}

			}

		}

		// 上記の判定で決まらなかった場合は、すべて置かれているか調べる
		int[] board = sess.getBoard();
		boolean putall=true;
		for(int i=1; i< board.length; i++){
			if(board[i]==0){
				putall = false;
			}
		}
		if(putall){
			sess.gameOver(0);
			//_restart.setVisible(true);
			//_quite.setVisible(true);
			_panel.setVisible(true);
			return;
		}

		//　ターンを変更（０→１、１→０）
		sess.changeTurn();

	}

	private final int getRandamCell(){
		int ret = 0;

		MySession sess = (MySession)getSession();
		int[] board = sess.getBoard();		while(true){
			ret = ((int)(Math.random()*9))+1;
			 if(board[ret] == 0){
				 break;
			 }
		}
		return ret;
	}

	/**
	 * リーチフラグ・自分または敵がリーチだったときtrue
	 */
	private boolean _reachflg;

	private void comTurn(){


		MySession sess = (MySession)getSession();
		int[] board = sess.getBoard();

		//　コンピュータが打つ番号
		int num = 0;

		//　一番初めの手
		if(sess.getTurn() ==null){

			//　勝率が一番いい手を取得
			List<Turn> list = sess.getGameInfo().getSortedTurnListByWin(sess.getMark());
			if(list.isEmpty()){

				// とりあえず、ランダムに
				num = getRandamCell();
				System.out.println("コンピュータが先行、でも戦歴が無いのでランダムに打つ");
			}
			else{

				//　一番初めのターンの手を取得
				Turn turn = list.get(0);
				num = turn.getCellNum();
				System.out.println("コンピュータが先行、戦歴があったので、それと同じように打つ");

			}

		}
		else{

			//　とにかく、２つ並んでいたら、打つ
			int mymark = sess.getUserInfo(sess.getTurnIndex()).getMark();
			int wknum = willWin(mymark);
			if(wknum != 0){
				_reachflg = true;
				//num = wknum;
				//info("勝った！");
			}
			else{
				//　敵のマークが２個ならんでいたら
				wknum = willWin(mymark==1?2:1);
				if(wknum != 0){
					_reachflg = true;
					//num = wknum;
					//info("ブロ~~~~~~~~~~~~~ック！！");
				}
			}

			//　今の状況が過去にあったか
			List<Turn> pastturnlist = sess.getGameInfo().getLikeTurnList(sess.getTurn());
			//　無い場合
			if(pastturnlist.size() == 0){

				// とりあえず、ランダムに
				num = getRandamCell();
				System.out.println("いまと同じ状況は過去にさっぱしないんでランダムにうつ");

			}
			//　今の状況と同じのが１回しかない場合
			else if(pastturnlist.size() == 1){
				Turn pastturn = pastturnlist.get(0);

				//　この後勝つ場合はその手を打つ
				if(pastturn.willWin(sess.getMark())){
					int turnnum = sess.getTurn().getLastTurn(1);	// 1はかならず１
					Turn nextturn = pastturn.getTurn(turnnum);
					num = nextturn.getCellNum();

					System.out.println("過去１回同じ状況になっていてその後勝つので同じように打つ");
				}
				else{
					// TODO とりあえずランダムに打つ
					num = getRandamCell();

					System.out.println("過去１回同じ状況にはなっているが、負けたのでとりあえずランダム");
				}

			}
			//　今の状況と同じのが沢山ある場合
			else{

				//　沢山同じ状況があるなからか、勝つ手順を取得
				boolean exist=false;
				for(int i=0; i<pastturnlist.size(); i++){
					Turn pastturn = pastturnlist.get(i);
					//　このあと勝つ場合
					if(pastturn.willWin(sess.getMark())){
						int turnnum = sess.getTurn().getLastTurn(1);	// 1はかならず１
						Turn nextturn = pastturn.getTurn(turnnum);
						num = nextturn.getCellNum();
						exist = true;
						System.out.println("過去複数回、同じ状況になっていて、その後勝つので同じように打つ");
						break;
					}

				}
				//　勝つ手順が無い場合はランダムに打つ
				if(!exist){
					num = getRandamCell();
					System.out.println("過去複数回、同じ状況になっているんだけど勝つ手がないのでランダムに打つ");
				}

			}

		}

		//　numが０のときは上記で決定できなかった場合（多分無いと思われる）
		if(num == 0){
			//　とりあえずランダムに打つ
			num = getRandamCell();
			System.out.println("なんか手が判定できなかったみたいなので、とりあえずランダムに打つ");
		}


		MaruBatuLink link = (MaruBatuLink)_form.get("cell" + num);
		putMark(link);

		sess.setComTurn(false);

		//　コンピュータが勝利した場合、
		if(sess.getGameStatus() ==6){
			//sess.addComGameCount();
			sess.getGameInfo().addComGameCount();
		}

	}


	private void restartGame(){

		//　セッション情報を初期化
		MySession sess = (MySession)getSession();
		sess.initGame();

		SelectOption select = (SelectOption)_selectplayer.getModelObject();
		UserInfo user = sess.getUserInfo(0);
		user.setUserType(Integer.parseInt(select.getCode()));

		//　マークのリセット
		for(int i=1; i<10; i++){
			MaruBatuLink link = (MaruBatuLink)_form.get("cell" + i);
			link.reset();
		}

		//　もう一回やる？ボタンなどを非表示
		_panel.setVisible(false);
	}

	/**
	 * やめるボタンの処理
	 */
	private void quiteGame(){

		setResponsePage(IndexPage.class);

	}


	public void renderHead(IHeaderResponse response) {

		MySession sess = (MySession)getSession();
		//　ゲーム中
		if(sess.getGameStatus() == STATUS_GAMMING){
			//　COMのターンの場合
			if(sess.getUserInfo(sess.getTurnIndex()).getUserType() == UserInfo.TYPE_COM){
		        response.renderOnDomReadyJavascript("javascript:disableAll();setTimeout('callComTurn()',1000);");
			}
			else{
				//System.out.print("");
			}
		}
		else {

			if(sess.getGameStatus() == STATUS_DRAW){
				response.renderOnDomReadyJavascript("javascript:drawGame();");
			}
			if(sess.getGameStatus() == STATUS_PLAYER1WIN){
				if(sess.getUserInfo(0).getUserType() == UserInfo.TYPE_USER){
					response.renderOnDomReadyJavascript("javascript:youWin();");
				}
				else{
					response.renderOnDomReadyJavascript("javascript:com1Win();");
				}
			}
			if(sess.getGameStatus() == 6){
				response.renderOnDomReadyJavascript("javascript:youLoose();");
			}

		}

	}



}
