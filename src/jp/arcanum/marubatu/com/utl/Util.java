package jp.arcanum.marubatu.com.utl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Util {

	/**
	 * プロパティファイルへのパス。（アプリ起動時に設定される）
	 */
	public static String PROP_PATH = "";

	/**
	 * エクセルファイルへのパス。（アプリ起動時に設定される）
	 */
	public static String EXCEL_PATH = "";

	//-------------------------------------------------------------------
	//　ページの定数関係
	//-------------------------------------------------------------------

	/**
	 * トップ画面をあらわす定数
	 */
	public static final String PAGE_MAIN     = "page.main";

	/**
	 * コンピュータ側の思考へのリダイレクト用
	 */
	public static final String PAGE_COMTURN =  "page.comturn";


	/**
	 * コンストラクタ
	 *
	 */
	private Util(){
		// ユーティリティクラスのため、コンストラクタは封印し、newできないようにする
	}

	/**
	 * プロパティ取得
	 * @param key 取得するためのキー
	 * @return 取得されたプロパティ値
	 */
	public static String getProperties(String key){

		String ret = null;

        FileInputStream fis = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
	    try {
	        fis = new FileInputStream(Util.PROP_PATH);
	        ir = new InputStreamReader(fis , "UTF-8");
	        br = new BufferedReader(ir);

	        String line;
	        while((line = br.readLine()) != null){
	        	line = line.trim();

	        	// コメントやイコールのない行は無視
	        	if(line.startsWith("#"))continue;
	        	if(line.indexOf("=")<0)continue;

	        	//　入力されたキーと同一のキーか調べる
	        	int eq = line.indexOf("=");
	        	String keyfile = line.substring(0,eq).trim();
	        	if(!keyfile.equals(key))continue;

	        	ret = line.substring(eq + 1, line.length()).trim();
	        	break;

	        }

	      } catch (Exception e) {
	    	  throw new RuntimeException("ファイル読み込み中に失敗",e);
	      }
	      finally{
	    	  try{
			        fis.close();
			        ir.close();
			        fis.close();
	    	  }
	    	  catch(Exception e){
	    		  throw new RuntimeException("ファイルを閉じるときに失敗", e);
	    	  }

	      }

		return ret;

	}

}
