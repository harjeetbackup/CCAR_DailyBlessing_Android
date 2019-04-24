package com.spearheadinc.flashcards.prayerapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Setting extends Activity {
	private static Setting screen;
    private	FCDBHelper mFCDbHelper;
    public static Setting getScreen() 
    {
    	return screen;
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) 
        {
			finish();
			overridePendingTransition(R.anim.hold, R.anim.push_up_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		screen = this;
		setContentView(R.layout.setting);
		
//        TextView tv = (TextView) findViewById(R.id.settingback);
		
		Button back_but = (Button) findViewById(R.id.settingback);
		back_but.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		 mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();

		final Button offBtn = (Button) findViewById(R.id.offBtn);
		final Button onBtn = (Button) findViewById(R.id.onBtn);
		offBtn.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				offBtn.setVisibility(View.GONE);
				onBtn.setVisibility(View.VISIBLE);
				mFCDbHelper.setRandomized(true);
				
			}
		});
		
		onBtn.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				offBtn.setVisibility(View.VISIBLE);
				onBtn.setVisibility(View.GONE);
				mFCDbHelper.setRandomized(false);
				
			}
		});
		
	  if(mFCDbHelper.getRandomized())
	  {
	        onBtn.setVisibility(View.VISIBLE);
	        offBtn.setVisibility(View.GONE);
	  }
	  else
	  {
		  onBtn.setVisibility(View.GONE);
	        offBtn.setVisibility(View.VISIBLE); 
	  }
//        RelativeLayout tv1 = (RelativeLayout) findViewById(R.id.setting_delete_bookmarks);
        RelativeLayout clearbook = (RelativeLayout) findViewById(R.id.setting_delete_bookmarks);
        clearbook.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all bookmarks ?");
				customizeDialog.show();
			}
		});
//        RelativeLayout tv2 = (RelativeLayout) findViewById(R.id.setting_delete_proficiency);
        RelativeLayout clearprof = (RelativeLayout) findViewById(R.id.setting_delete_proficiency);
        clearprof.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all proficiencies ?");
				customizeDialog.show();
			}
		});
        RelativeLayout clearvoice = (RelativeLayout) findViewById(R.id.setting_delete_voicenotes);
        clearvoice.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all voice notes ?");
				customizeDialog.show();
			}
		});
        RelativeLayout clearcomment = (RelativeLayout) findViewById(R.id.setting_delete_comments);
        clearcomment.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset all comments ?");
				customizeDialog.show();
			}
		});
        RelativeLayout resetapp = (RelativeLayout) findViewById(R.id.resetApplication);
        resetapp.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				customizeDialog = new CustomizeDialog(Setting.this, "Are you sure you want to reset Application ?");
				customizeDialog.show();
			}
		});
    /*    Button apprating = (Button) findViewById(R.id.newsetting_but_apprating);
        apprating.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
//				String url = "https://market.android.com/details?id=com.spearheadinc.flashcards.prayerapp&feature=search_result";
				String url = "http://www.amazon.com/gp/mas/dl/android?p=com.spearheadinc.flashcards.prayerapp&showAll=1";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});*/
    }
    
    private CustomizeDialog customizeDialog;
}
