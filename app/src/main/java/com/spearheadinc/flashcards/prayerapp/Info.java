package com.spearheadinc.flashcards.prayerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Info extends Activity {
	private static Info screen;
	private WebSettings localWebView;
    public static Info getScreen() 
    {
    	return screen;
	}
//	private FCDBHelper mFCDbHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		screen = this;
		setContentView(R.layout.info);
        
        TextView tv = (TextView) findViewById(R.id.trade_title);
        tv.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
        
        WebView mWebView = (WebView) findViewById(R.id.webview);
        localWebView = mWebView.getSettings(); 
        localWebView.setBuiltInZoomControls(true);
        localWebView.setSupportZoom(true); 
        localWebView.setLoadWithOverviewMode(true);
        localWebView.setUseWideViewPort(true);
//    	mFCDbHelper.openDataBase();
//    	String text = mFCDbHelper.getInfoText();
//    	mFCDbHelper.close();     
//	    
//        String tex = 	"<html><body>" +
//                		"<p align=\"justify\"><br>" +               
//                		"<b><strong>Coming Soon"+
//                		"</p> " +
//                		"</body></html>";
//        
//        mWebView.loadData(tex, "text/html", "utf-8");
        mWebView.loadUrl("file:///android_asset/" + "info.html");
    }
}