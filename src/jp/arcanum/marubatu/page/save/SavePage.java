package jp.arcanum.marubatu.page.save;

import java.util.ArrayList;
import java.util.List;

import jp.arcanum.marubatu.com.page.AbstractPage;
import jp.arcanum.marubatu.com.utl.SelectOption;
import jp.arcanum.marubatu.com.utl.UserInfo;
import jp.arcanum.marubatu.page.main.MainPage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;


public class SavePage extends AbstractPage{

	/*
	private Button _start = new Button("start"){
		public void onSubmit() {
			onClickStart();
		}
	};
	*/

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

	private Button _start = new Button("start"){
		public void onSubmit() {
			onClickStart();
		}
	};


	public SavePage(){

		_form.add(_start);
		_form.add(_selectplayer);
		//_selectplayer.setModelObject(String.valueOf(UserInfo.TYPE_USER));

	}

	private void onClickStart(){

		SelectOption option = (SelectOption)_selectplayer.getModelObject();
		PageParameters params = new PageParameters();
		params.put("USERTYPE", option.getCode());
		setResponsePage(MainPage.class, params);
	}


}
