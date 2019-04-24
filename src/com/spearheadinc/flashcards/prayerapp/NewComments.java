package com.spearheadinc.flashcards.prayerapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class NewComments extends Activity {

	private static NewComments screen;
	private EditText ed;
	private FCDBHelper mFCDbHelper;
	private String mFlashCardId = "";
	private String mNotesText = "";
	
	public static NewComments getInstance() {
		return screen;
	}

	private Button mDoneBut;
	private String mFromClass;
	private Button addButton;
	private Button edButton;
    
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		screen = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
        	mFlashCardId = extras.getString("com.fadavis.pharmphlashfc.phone.fk_FlashCardId");
        	mNotesText = extras.getString("com.fadavis.pharmphlashfc.phone.notesText");
//        	mFromClass = extras.getString("com.fadavis.pharmphlashfc.phone.fromclass");
        }
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
		
		Button backBut = (Button) findViewById(R.id.comments_back);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		
//		mDoneBut = (Button) findViewById(R.id.comments_done_but);
//		mDoneBut.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				saveDBinfoForAudio();
//				finish();
//				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
//			}
//		});
		
		ed = (EditText) findViewById(R.id.comments_edit_name);

		//
    	mFCDbHelper.openDataBase();
    	List<String[]> list = mFCDbHelper.getCommentSearchResultsForIndividualcard(mFlashCardId);
    	mFCDbHelper.close();
    	String notesText = "";
		ed.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		ed.setImeOptions(EditorInfo.IME_ACTION_DONE);
			
		ed.setVisibility(View.GONE);
		addButton = (Button) findViewById(R.id.comments_add_but);
		mDoneBut = (Button) findViewById(R.id.comments_done_but);
		edButton = (Button) findViewById(R.id.comments_edit_but);
		if(list.size() > 0)
		{
			notesText = list.get(0)[0];
			ed.setText(notesText);
			ed.setVisibility(View.VISIBLE);
			edButton.setVisibility(View.VISIBLE);
			mDoneBut.setVisibility(View.GONE);
			addButton.setVisibility(View.GONE);
			Selection.setSelection(ed.getText(), ed.getText().toString().length());
		}
//		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		mgr.hideSoftInputFromWindow(ed.getWindowToken(), 0);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ed.setText(" ");
				ed.setVisibility(View.VISIBLE);
				ed.setCursorVisible(true);
				ed.setEnabled(true);
				ed.setImeOptions(EditorInfo.IME_ACTION_DONE);
				ed.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.showSoftInput(ed, InputMethodManager.SHOW_FORCED);
				mDoneBut.setVisibility(View.VISIBLE);
				addButton.setVisibility(View.GONE);
//				ed.setHint("Enter comments here");
				

		     if (imm != null){
		         imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		         ed.setCursorVisible(true);
		         ed.setFocusable(true);
//		         ed.setSelectionStart();
		         ed.setSelection(ed.getText().length());
//		         newpasscode.setRawInputType(Configuration.KEYBOARD_12KEY);
//		         retypepasscode.setRawInputType(Configuration.KEYBOARD_12KEY);
		         
		     }
			}
		});
		
		mDoneBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if(ed.getText().length() < 1)
		    		showDialog("Please enter comment");
				else
				{
					saveDBinfoForAudio();
					InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					mgr.hideSoftInputFromWindow(ed.getWindowToken(), 0);
					edButton.setVisibility(View.VISIBLE);
					mDoneBut.setVisibility(View.GONE);
					
					ed.setCursorVisible(false);
					ed.setEnabled(false);
				}
//				finish();
//				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		
		edButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
				ed.setVisibility(View.VISIBLE);
				ed.setCursorVisible(true);
				ed.setEnabled(true);
				ed.setImeOptions(EditorInfo.IME_ACTION_DONE);
				ed.setInputType(InputType.TYPE_CLASS_TEXT);
				mDoneBut.setVisibility(View.VISIBLE);
				edButton.setVisibility(View.GONE);
			}
		});
		
		
		
		
//		if(mFromClass != null && mFromClass.equals("VIEW"))
//		{
//			mDoneBut.setVisibility(View.INVISIBLE);
//		}
		if(mNotesText != null && !mNotesText.equals(""))
		{
//			mDoneBut.setVisibility(View.INVISIBLE);
			ed.setText(mNotesText);
		}
		else
		{
//			mDoneBut.setVisibility(View.VISIBLE);
		}
	}

	private void saveDBinfoForAudio()
	{
//	    String DATE_FORMAT = "MM/dd/yy";
//	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//	    Calendar c1 = Calendar.getInstance(); 
//		String currentTime = sdf.format(c1.getTime());
//		
//    	String commentText = ed.getText().toString();
//    	if(commentText != null && !commentText.equals(""))
//    	{
//	    	mFCDbHelper.openDataBase();
//	    	if(mNotesText != null && !mNotesText.equals(""))
//	    		mFCDbHelper.updateCommentsInfo(mFlashCardId, commentText, currentTime);
//	    	else
//	    		mFCDbHelper.saveCommentsInfo(mFlashCardId, commentText, currentTime);
//	    	mFCDbHelper.close();
//	    	CommentDetails.getInstance().populateCommentDetailView();
//    	}
//    	else
//    		showDialog("Please enter comment");
	    String DATE_FORMAT = "MM/dd/yy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    Calendar c1 = Calendar.getInstance(); 
		String currentTime = sdf.format(c1.getTime());
		
    	String commentText = ed.getText().toString();
    	if(commentText != null && !commentText.equals(""))
    	{
	    	mFCDbHelper.openDataBase();
	    	List<String[]> list = mFCDbHelper.getCommentSearchResultsForIndividualcard(mFlashCardId);
    		String notesText = "";
    		if(list.size() > 0)
    			notesText = list.get(0)[0];
//	    	if(mNotesText != null && !mNotesText.equals(""))
	    	if(notesText != null && !notesText.equals(""))
	    		mFCDbHelper.updateCommentsInfo(mFlashCardId, commentText, currentTime);
	    	else
	    		mFCDbHelper.saveCommentsInfo(mFlashCardId, commentText, currentTime);
	    	mFCDbHelper.close();
			CardDetails.getScreen().changeOptionIcon();
//	    	CommentDetails.getInstance().populateCommentDetailView();
    	}
    	else
    		showDialog("Please enter comment");
	}
    
    public void showDialog(String message)
    {
    	AlertDialog.Builder alt_bld = new AlertDialog.Builder(NewComments.this);
    	alt_bld.setMessage(message).setCancelable(false)
    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			dialog.cancel();
			}
		});
    	AlertDialog alert = alt_bld.create();
		alert.setTitle("Connection Error");
		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
		alert.show();
    }
}
