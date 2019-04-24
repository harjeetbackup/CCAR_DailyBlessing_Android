package com.spearheadinc.flashcards.prayerapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class Introduction extends Activity {
	private static Introduction screen;
	private WebSettings localWebView;
    public static Introduction getScreen() 
    {
    	return screen;
	}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = this;
        setContentView(R.layout.intro);
        TextView tv = (TextView) findViewById(R.id.backBtn);
        tv.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				finish();
				//overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
        
        WebView mWebView = (WebView) findViewById(R.id.iwebview);
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
        mWebView.loadUrl("file:///android_asset/" + "intro.html");
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.intro, menu);
        return true;
    }*/
}
