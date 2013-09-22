package jp.arcanum.marubatu;

import jp.arcanum.marubatu.com.page.ExpiredPage;
import jp.arcanum.marubatu.com.utl.MySession;
import jp.arcanum.marubatu.page.com.ComPage;
import jp.arcanum.marubatu.page.index.IndexPage;

import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.protocol.http.WebApplication;

public class MyApp extends WebApplication {


    protected void init() {

    	//　保守者へ、以下のinit()は消さないでください。
    	super.init();

    	//　文字エンコーディング関係の設定
    	getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");


        mountBookmarkablePage("/comturn", ComPage.class);



        //　ページの有効期限がきれた場合の設定
        Application.get().getApplicationSettings().setPageExpiredErrorPage(ExpiredPage.class);

        addComponentInstantiationListener(
        		new IComponentInstantiationListener(){

        			public void onInstantiation(Component comp) {
        				if(comp instanceof FormComponent){
        					comp.add(new MessageErrorBehavior(comp));
        				}

					}

        		}
        );

    }


	/**
	 * 開始ページの取得
	 */
	public Class getHomePage() {

		return IndexPage.class;

	}

	/**
	 * セッションの生成（ここでnewされたセッションが、各リクエストと紐付けられる。）
	 */
	public Session newSession(Request request, Response response) {

		return new MySession(request);

	}



	class MessageErrorBehavior extends AttributeModifier{

		//private Component _related;

		public MessageErrorBehavior(final Component related){
			super(
				"class",
				true,
				new AbstractReadOnlyModel() {
					public String getObject() {
						if(Session.get().getFeedbackMessages().hasErrorMessageFor(related)){
							return "message";
						}
						return null;
					};

				}
			);
			//_related = related;
		}
	}


}
