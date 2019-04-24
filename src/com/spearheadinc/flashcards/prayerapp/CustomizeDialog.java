package com.spearheadinc.flashcards.prayerapp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomizeDialog extends Dialog 
{	
	Button cancelbutton;
	String [] addStr;
	private FCDBHelper mFCDbHelper;
	private String message;
	
	public CustomizeDialog(final Context context, final String mesage)
	{
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.statusdialog);
//		
//		okbutton = (Button)findViewById(R.id.okbutton);
//		okbutton.setOnClickListener(new Button.OnClickListener() 
//		{		
//			@Override
//			public void onClick(View v) 
//			{
//				dismiss();			
//			}
//		});			
//		
//		TextView tv = (TextView)findViewById(R.id.toptext);
//		tv.setText(message);
//		Button visaButton = (Button)findViewById(R.id.visabutton);
//		mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
		message = mesage;
		cancelbutton = (Button)findViewById(R.id.okbutton);
		cancelbutton.setOnClickListener(new Button.OnClickListener() 
		{		
			@Override
			public void onClick(View v) 
			{
				dismiss();			
			}
		});			
		
		TextView tv = (TextView)findViewById(R.id.toptext);
		TextView tvtitle = (TextView)findViewById(R.id.toptitle);
		if(message.contains("Confirm Delete !"))
		{
			message = "Confirm Delete !";
			tvtitle.setVisibility(View.INVISIBLE);
		}
		tv.setText(message);
		Button okButton = (Button)findViewById(R.id.visabutton);
		mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
		okButton.setOnClickListener(new Button.OnClickListener() 
		{		
			@Override
			public void onClick(View v) 
			{
				if(message.equals("Are you sure you want to reset all proficiencies ?"))
				{
			    	mFCDbHelper.openDataBase();
			    	mFCDbHelper.deleteAllProficiency();
			    	mFCDbHelper.close();     
					DeckView.getScreen().deleteAllProficiency();
					Log.e("proficiencies ***************  ", "proficiencies");
				}
				else if(message.equals("Are you sure you want to reset all bookmarks ?"))
				{
			    	mFCDbHelper.openDataBase();
			    	mFCDbHelper.deleteAllBookMark();
			    	mFCDbHelper.close();     
			    	DeckView.getScreen().deleteAllBookmarks();
					Log.e("bookmarks********************   ", "bookmarks");	
				}
				else if(message.equals("Are you sure you want to reset all voice notes ?"))
				{
			    	mFCDbHelper.openDataBase();
			    	mFCDbHelper.clearVoiceNotesInDB();
			    	mFCDbHelper.close();     
			    	DeckView.getScreen().deleteAllBookmarks();
					Log.e("voice notes********************   ", "voice notes");	
				}
				else if(message.equals("Are you sure you want to reset all comments ?"))
				{
			    	mFCDbHelper.openDataBase();
			    	mFCDbHelper.clearCommentsInDB();
			    	mFCDbHelper.close();     
			    	DeckView.getScreen().deleteAllBookmarks();
					Log.e("comments********************   ", "comments");	
				}
				else if(message.equals("Are you sure you want to reset Application ?"))
				{
			    	mFCDbHelper.openDataBase();
			    	mFCDbHelper.deleteAllProficiency();
			    	mFCDbHelper.deleteAllBookMark();
			    	mFCDbHelper.clearVoiceNotesInDB();
			    	mFCDbHelper.clearCommentsInDB();
//			    	mFCDbHelper.resetApplicationInDB();
			    	mFCDbHelper.close();     
			    	DeckView.getScreen().resetApplication();
					Log.e("reset Application********************   ", "reset Application");	
				}
				else if(mesage.equals("Confirm Delete !MyComments"))
				{
			    	MyComments.getInstance().deleteView();
					Log.e("MyComments********************   ", "reset Application");	
				}
				else if(mesage.equals("Confirm Delete !VoiceNoteDetails"))
				{
					VoiceNoteDetails.getInstance().deleteView();
					Log.e("VoiceNoteDetails********************   ", "reset Application");	
				}
				else if(mesage.equals("Confirm Delete !CardNotesDetail"))
				{
					CardNotesDetail.getInstance().deleteView();
					Log.e("CardNotesDetail*******************   ", "reset Application");	
				}
//				else if(message.equals(""))
//				{
//			    	mFCDbHelper.openDataBase();
//			    	mFCDbHelper.deleteAllBookMark();
//			    	mFCDbHelper.close();     
//			    	DeckView.getScreen().deleteAllBookmarks();
//					Log.e("bookmarks********************   ", "bookmarks");	
//				}
				dismiss();
			}
		});			
	}
}
