package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VoiceNoteDetails extends Activity {

	private static VoiceNoteDetails screen;
	private MediaPlayer mediaPlayer;
	private String mFlashCardId;
	private RelativeLayout relCustomshapeQuiz;
	private FCDBHelper mFCDbHelper;
	private LinearLayout mainLinearLayoutView;
	private String mFromClass = "";
	
	public static VoiceNoteDetails getInstance() {
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.voicenotedetailview);
		screen = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
        	mFlashCardId = extras.getString("com.fadavis.pharmphlashfc.phone.fk_FlashCardId");
        	mFromClass  = extras.getString("com.fadavis.pharmphlashfc.phone.fromclass");
        }
		
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
        mainLinearLayoutView = (LinearLayout) findViewById(R.id.voicenote_details_edit_name_lin);
		
		Button backBut = (Button) findViewById(R.id.voicenote_details_back);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});

		Button addBut = (Button) findViewById(R.id.voicenote_details_add_but);
		if(mFlashCardId == null)
		{
			addBut.setEnabled(false);
			addBut.setVisibility(View.INVISIBLE);
		}
		if(mFromClass != null && mFromClass.equals("VIEW"))
			addBut.setVisibility(View.INVISIBLE);
		addBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(VoiceNoteDetails.this, NewVoiceNotes.class);				
				if(mFlashCardId != null)
					i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			}
		});
		if(mFromClass != null && mFromClass.equals("CARDNOTESDETAIL"))
			addBut.setVisibility(View.VISIBLE);
		
		populateVoiceNoteView();
    }
    
    public void populateVoiceNoteView() {
    	mainLinearLayoutView.removeAllViews();
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout seqOrderLinrOutOpt =  (RelativeLayout) mInflater.inflate( R.layout.sequense_order_view, null);
	    LinearLayout seqOrderLinrOpt =  (LinearLayout) seqOrderLinrOutOpt.findViewById( R.id.sequense_order_layout_bg);
	    List<String[]> list = null;
//		if(mFromClass != null && mFromClass.equals("CARDNOTESDETAIL"))
		{
	    	mFCDbHelper.openDataBase();
	    	list = mFCDbHelper.getVoiceSearchResultsForIndividualcard(mFlashCardId);
	    	mFCDbHelper.close();
		}
//		else
//		{
//	    	mFCDbHelper.openDataBase();
//	    	list = mFCDbHelper.getVoiceSearchResults();
//	    	mFCDbHelper.close();
//		}
 //  	if(list.size() == 0)
   // 	{
   			
     //	}
  // 	else
    //	{
	    	for (int i = 0; i < list.size(); i++) 
	    	{
	    		String strArr[] = list.get(i);
				CreateRowView(strArr[0], strArr[1], seqOrderLinrOpt);
	    		if(i < list.size() - 1)
	    			seqOrderLinrOpt.addView(createSeqUnderLine());
			}
    //	}
    	mainLinearLayoutView.addView(seqOrderLinrOutOpt);
	}
	
	public RelativeLayout createSeqUnderLine () {
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.sequential_textview, null);
		return detailRow;
	}
	
	private RelativeLayout CreateRowView(final String audTitle, String strFilePath, LinearLayout seqOrderLinrOpt)
    {
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.type_notes_row, null);
	    TextView detailValue1 = (TextView) detailRow.findViewById(R.id.type_notes_row_discription);
	    Button delButton = (Button) detailRow.findViewById(R.id.type_notes_row_delete_icon);
	    detailValue1.setText(Html.fromHtml(audTitle));

		relCustomshapeQuiz = null;
		relCustomshapeQuiz = (RelativeLayout) detailRow.findViewById(R.id.type_notes_row_rel_bg);
	    if(seqOrderLinrOpt != null)
	    {
			setDataTagsToView(strFilePath, relCustomshapeQuiz, detailRow);
			setDataTagsToDelete(mFlashCardId, audTitle, strFilePath, delButton);
	    	seqOrderLinrOpt.addView(detailRow);

			detailRow.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View v) 
				{
					setViewStatus(v, audTitle);
				}
			});
			
			delButton.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View v) 
				{
					if(mediaPlayer != null)
						mediaPlayer.stop();
					viewDel = v;
					customizeDialog = new CustomizeDialog(VoiceNoteDetails.this, "Confirm Delete !VoiceNoteDetails");
					customizeDialog.show();
//					deleteView(v);
				}
			});
	    }
		return detailRow;
    }
    View viewDel;  
    private CustomizeDialog customizeDialog;

	@SuppressWarnings("unchecked")
	private void setDataTagsToView(String strFilePath, RelativeLayout relCustomshapeQuizView, RelativeLayout detailRow1) 
	{
		List listRel = new ArrayList();
		listRel.add(strFilePath);
		listRel.add(relCustomshapeQuizView);
		detailRow1.setTag(listRel);
	}

	@SuppressWarnings("unchecked")
	private void setDataTagsToDelete(String strCardId, String audTitle, String strFilePath, Button detailRow1) 
	{
		List listRel = new ArrayList();
		listRel.add(strCardId);
		listRel.add(audTitle);
		listRel.add(strFilePath);
		detailRow1.setTag(listRel);
	}
    
	@SuppressWarnings("unchecked")
	public void deleteView()
    {
		List listRel = (List) viewDel.getTag();
		if(listRel != null && listRel.size() > 0)
		{
			String strCardId = (String) listRel.get(0);
			String audTitle = (String) listRel.get(1);
			String strFilePath = (String) listRel.get(2);
		    
	    	mFCDbHelper.openDataBase();
	    	mFCDbHelper.deleteSingleVoiceNotesForcardID(strCardId, audTitle, strFilePath);
	    	mFCDbHelper.close();
	    	populateVoiceNoteView();
		}
    }
    
	@SuppressWarnings("unchecked")
	private void setViewStatus(View v,String audTitle)
    {
//		if(!isSubmittedAns)
		{
			List listRel = (List) v.getTag();
			if(listRel != null && listRel.size() > 0)
			{
				String recordFilePath = (String) listRel.get(0);
				if(relCustomshapeQuiz != null)
					relCustomshapeQuiz.setBackgroundColor(Color.parseColor("#FACE63"));
				relCustomshapeQuiz = null;
				relCustomshapeQuiz = (RelativeLayout) listRel.get(1);
				relCustomshapeQuiz.setBackgroundColor(Color.argb(40, 20, 20, 50));
				Log.e("detailRow1", recordFilePath +"" + relCustomshapeQuiz);
				startPlaytoAudio(recordFilePath,audTitle);
			}
		}
    }
    
	private void startPlaytoAudio(String recordFilePath,String audTitle)
    {
		if(mediaPlayer != null)
			mediaPlayer.stop();
		mediaPlayer = new MediaPlayer();
		Log.e("recordFilePath = ", recordFilePath);
		try {
			mediaPlayer.setDataSource(recordFilePath);
			mediaPlayer.prepare();
			mediaPlayer.start();
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(VoiceNoteDetails.this);
			alt_bld.setTitle ("Voice Note");
	        alt_bld.setMessage ("Playing....." + audTitle).setCancelable(false)
		        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int id) {
		        	dialog.cancel();
		        	mediaPlayer.stop();
					mediaPlayer.release();
		        }
	        });
	       
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Override
	protected void onResume()
	{
		super.onResume();
		populateVoiceNoteView();
	}
//	
//	private RelativeLayout CreateRowView(String notesText, String strFilePath, LinearLayout seqOrderLinrOpt)
//    {
//		LayoutInflater mInflater = LayoutInflater.from(this);
//		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.type_notes_row, null);
//	    TextView detailValue1 = (TextView) detailRow.findViewById(R.id.type_notes_row_discription);
//	    detailValue1.setText(Html.fromHtml(notesText));
//
//		relCustomshapeQuiz = null;
//		relCustomshapeQuiz = (RelativeLayout) detailRow.findViewById(R.id.type_notes_row_rel_bg);
//	    if(seqOrderLinrOpt != null)
//	    {
//			setDataTagsToView(strFilePath, relCustomshapeQuiz, detailRow);
//	    	seqOrderLinrOpt.addView(detailRow);
//
//			detailRow.setOnClickListener(new OnClickListener() 
//	        {
//				@Override
//				public void onClick(View v) 
//				{
//					setViewStatus(v);
//				}
//			});
//	    }
//		return detailRow;
//    }
//
//	@SuppressWarnings("unchecked")
//	private void setDataTagsToView(String strFilePath, RelativeLayout relCustomshapeQuizView, RelativeLayout detailRow1) {
//		List listRel = new ArrayList();
//		listRel.add(strFilePath);
//		listRel.add(relCustomshapeQuizView);
//		detailRow1.setTag(listRel);
//	}
//    
//	@SuppressWarnings("unchecked")
//	private void setViewStatus(View v)
//    {
////		if(!isSubmittedAns)
//		{
//			List listRel = (List) v.getTag();
//			if(listRel != null && listRel.size() > 0)
//			{
//				String recordFilePath = (String) listRel.get(0);
//				if(relCustomshapeQuiz != null)
//					relCustomshapeQuiz.setBackgroundColor(Color.parseColor("#FACE63"));
//				relCustomshapeQuiz = null;
//				relCustomshapeQuiz = (RelativeLayout) listRel.get(1);
//				relCustomshapeQuiz.setBackgroundColor(Color.argb(40, 20, 20, 50));
//				Log.e("detailRow1", recordFilePath +"" + relCustomshapeQuiz);
//				startPlaytoAudio(recordFilePath);
//			}
//		}
//    }
//    
//	private void startPlaytoAudio(String recordFilePath)
//    {
//		if(mediaPlayer != null)
//		{
//			mediaPlayer.stop();
//		}
//		mediaPlayer = new MediaPlayer();
//		Log.e("recordFilePath = ", recordFilePath);
//		try {
//			mediaPlayer.setDataSource(recordFilePath);
//			mediaPlayer.prepare();
//			mediaPlayer.start();
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
}
