package jp.arcanum.marubatu.com.page;

import jp.arcanum.marubatu.com.utl.MySession;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public abstract class AbstractPage extends WebPage {

	/**
	 * 画面でただひとつのフォームオブジェクト
	 */
	protected Form _form = new Form("form");


	/**
	 * コンストラクタ
	 *
	 */
	public AbstractPage(){

		MySession sess = (MySession)getSession();


		//　メッセージエリア
		addForm(new FeedbackPanel("feedback"));


		//　フォームオブジェクトの追加
		add(_form);


	}


	/**
	 * 画面にオブジェクトを追加する。<br>
	 * この経歴管理アプリケーションでは、画面にオブジェクトを
	 * 追加する場合は、このメソッドを使用してください。
	 * @param comp TextFieldなどのコンポーネント
	 */
	public void addForm(Component comp){
		_form.add(comp);
	}


}
