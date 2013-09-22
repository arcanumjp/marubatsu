package jp.arcanum.marubatu.page.main;

import java.util.List;

import jp.arcanum.marubatu.com.utl.MySession;
import jp.arcanum.marubatu.com.utl.UserInfo;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

abstract class MaruBatuLink extends Link{



	private static final SimpleAttributeModifier ONCLICK_DISABLE_CELL = new SimpleAttributeModifier("onclick","return false;");
	private static final AttributeAppender ONCLICK_CELL = new AttributeAppender("onclick",new Model("disableAll()"), ";");


	private static final ResourceReference IMG_SPACE = new ResourceReference(MainPage.class, "../../space.gif");
	private static final ResourceReference IMG_MARU = new ResourceReference(MainPage.class, "../../maru.gif");
	private static final ResourceReference IMG_BATU = new ResourceReference(MainPage.class, "../../batu.gif");
	private static final ResourceReference IMG_MARUWIN = new ResourceReference(MainPage.class, "../../maruwin.gif");
	private static final ResourceReference IMG_BATUWIN = new ResourceReference(MainPage.class, "../../batuwin.gif");

	private static final ResourceReference MARK[] = {
		null,
		IMG_MARU,
		IMG_BATU,
		null,
		null,
		IMG_MARUWIN,
		IMG_BATUWIN,
	};

	/**
	 * ○×が置かれた場所１～９
	 */
	private int _num;
	public final int getNum(){
		return _num;
	}

	/**
	 * コンストラクタ
	 * @param num 置く場所
	 */
	public MaruBatuLink(final int num){
		super("cell" + num);
		_num = num;
		add(new Image("cellimage" + _num, IMG_SPACE));
		add(ONCLICK_CELL);
	}


	public void setMark(final int mark){
		//if((mark & 4) != UserInfo.MARK_MARU && (mark & 4) != UserInfo.MARK_BATU){
		//	throw new RuntimeException("セットされたマークが異常　" + mark);
		//}
		remove("cellimage" + _num);
		add(new Image("cellimage" + _num, MARK[mark]));
	}

	public void reset(){

		remove("cellimage" + _num);
		add(new Image("cellimage" + _num, IMG_SPACE));

	}


	protected void onBeforeRender() {

		MySession sess = (MySession)getSession();

		if(sess.getGameStatus() == 1){
			int[] board = sess.getBoard();
			if(board[_num] != 0){
				removeOnClick();
				add(ONCLICK_DISABLE_CELL);
			}
			else{

				if(!getBehaviors(SimpleAttributeModifier.class).isEmpty()){
					removeOnClick();
				}

			}
		}
		else{
			removeOnClick();
			add(ONCLICK_DISABLE_CELL);
		}

		super.onBeforeRender();
	}

	private void removeOnClick(){
		List<IBehavior> list = getBehaviors(SimpleAttributeModifier.class);
		for(int i = 0 ; i < list.size(); i++){
			IBehavior obj = list.get(i);
			if(obj == ONCLICK_DISABLE_CELL){
				remove(obj);
			}
		}
	}
}