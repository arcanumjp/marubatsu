package jp.arcanum.marubatu.page.com;

import org.apache.wicket.markup.html.pages.RedirectPage;

import jp.arcanum.marubatu.com.page.AbstractPage;
import jp.arcanum.marubatu.page.main.MainPage;

public class ComPage extends AbstractPage {

	public ComPage(MainPage page){



		setResponsePage(page);
	}
}
