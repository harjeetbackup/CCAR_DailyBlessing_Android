package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardNotesDetail  extends Activity {

	private static CardNotesDetail screen;
//	private String mFlashCardId;
	private RelativeLayout relCustomshapeQuiz;
	private FCDBHelper mFCDbHelper;
	private LinearLayout mainLinearLayoutView;
	protected CustomizeDialog customizeDialog;
//	private String mFromClass = "";
	protected View viewDel;
	
	public static CardNotesDetail getInstance() {
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
		setContentView(R.layout.mynotes);
		screen = this;

//        Bundle extras = getIntent().getExtras();
//        if(extras != null)
//        {
//        	mFlashCardId = extras.getString("com.fadavis.pharmphlashfc.phone.fk_FlashCardId");
//        	mFromClass  = extras.getString("com.fadavis.pharmphlashfc.phone.fromclass");
//        }
		
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
        mainLinearLayoutView = (LinearLayout) findViewById(R.id.mynotes_details_edit_name_lin);
		
		Button backBut = (Button) findViewById(R.id.mynotes_details_back);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});

		
    }
    
    public void populateVoiceNoteView() {
    	mainLinearLayoutView.removeAllViews();
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout seqOrderLinrOutOpt =  (RelativeLayout) mInflater.inflate( R.layout.sequense_order_view, null);
	    LinearLayout seqOrderLinrOpt =  (LinearLayout) seqOrderLinrOutOpt.findViewById( R.id.sequense_order_layout_bg);
	    
    	mFCDbHelper.openDataBase();
    	List<String> list = mFCDbHelper.getVoiceSearchResultsForIndividual();
    	mFCDbHelper.close();
    	if(list.size() == 0)
    	{
    		AlertDialog.Builder alt_bld = new AlertDialog.Builder(CardNotesDetail.this);
   			alt_bld.setMessage("No Voice Notes Found!").setCancelable(false)
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	dialog.cancel();
	        }
	        });
   			AlertDialog alert = alt_bld.create();
   			alert.setTitle("Information");
   			alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
   			alert.show();
    	}
    	else
    	{
	    	for (int i = 0; i < list.size(); i++) 
	    	{
	    		String strCardId = list.get(i);
		    	mFCDbHelper.openDataBase();
		    	String strname = mFCDbHelper.getCardName(strCardId);
		    	mFCDbHelper.close();
				CreateRowView(strname, strCardId, seqOrderLinrOpt);
	    		if(i < list.size() - 1)
	    			seqOrderLinrOpt.addView(createSeqUnderLine());
			}
    	}
    	mainLinearLayoutView.addView(seqOrderLinrOutOpt);
	}
	
    
	public RelativeLayout createSeqUnderLine () {
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.sequential_textview, null);
		return detailRow;
	}
	
	private RelativeLayout CreateRowView(String notesText, String strCardId, LinearLayout seqOrderLinrOpt)
    {
		LayoutInflater mInflater = LayoutInflater.from(this);
//		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.type_notes_row, null);
//	    TextView detailValue1 = (TextView) detailRow.findViewById(R.id.type_notes_row_discription);
//	    detailValue1.setText(Html.fromHtml(notesText));
//
//		relCustomshapeQuiz = null;
//		relCustomshapeQuiz = (RelativeLayout) detailRow.findViewById(R.id.type_notes_row_rel_bg);
//	    if(seqOrderLinrOpt != null)
//	    {
//			setDataTagsToView(strCardId, relCustomshapeQuiz, detailRow);
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
		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.type_notes_row, null);
	    TextView detailValue1 = (TextView) detailRow.findViewById(R.id.type_notes_row_discription);
	    Button delButton = (Button) detailRow.findViewById(R.id.type_notes_row_delete_icon);
	    detailValue1.setText(Html.fromHtml(notesText));

	    delButton.setVisibility(View.GONE);
		//ImageView im = (ImageView) detailRow.findViewById(R.id.type_notes_row_icon);
		//im.setVisibility(View.GONE);
		relCustomshapeQuiz = null;
		relCustomshapeQuiz = (RelativeLayout) detailRow.findViewById(R.id.type_notes_row_rel_bg);
	    if(seqOrderLinrOpt != null)
	    {
	    	seqOrderLinrOpt.addView(detailRow);
	    	if(!strCardId.equals(""))
	    	{
	    		//im.setVisibility(View.VISIBLE);
	    	    delButton.setVisibility(View.VISIBLE);
				setDataTagsToView(strCardId, relCustomshapeQuiz, detailRow);
				setDataTagsToDelete(strCardId, delButton);
	
				detailRow.setOnClickListener(new OnClickListener() 
		        {
					@Override
					public void onClick(View v) 
					{
						setViewStatus(v);
					}
				});
				
				delButton.setOnClickListener(new OnClickListener() 
		        {
					@Override
					public void onClick(View v) 
					{
						viewDel = v;
						customizeDialog = new CustomizeDialog(CardNotesDetail.this, "Confirm Delete !CardNotesDetail");
						customizeDialog.show();
//						deleteView(v);
					}
				});
	    	}
	    }
		return detailRow;
    }

	@SuppressWarnings("unchecked")
	private void setDataTagsToView(String strCardId, RelativeLayout relCustomshapeQuizView, RelativeLayout detailRow1) {
		List listRel = new ArrayList();
		listRel.add(strCardId);
		listRel.add(relCustomshapeQuizView);
		detailRow1.setTag(listRel);
	}
    
	@SuppressWarnings("unchecked")
	private void setViewStatus(View v)
    {
		List listRel = (List) v.getTag();
		if(listRel != null && listRel.size() > 0)
		{
			String strCardId = (String) listRel.get(0);
			Intent i = new Intent(CardNotesDetail.this, VoiceNoteDetails.class);
			i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", strCardId);			
			i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "CARDNOTESDETAIL");
			startActivity(i);
			overridePendingTransition(R.anim.push_up_in, R.anim.hold);
		}
    }

	@SuppressWarnings("unchecked")
	private void setDataTagsToDelete(String strCardId, Button detailRow1) {
		List listRel = new ArrayList();
		listRel.add(strCardId);
		detailRow1.setTag(listRel);
	}
    
	@SuppressWarnings("unchecked")
	public void deleteView()
    {
		List listRel = (List) viewDel.getTag();
		if(listRel != null && listRel.size() > 0)
		{
			String strCardId = (String) listRel.get(0);
	    	mFCDbHelper.openDataBase();
	    	mFCDbHelper.deleteVoiceNotesForcardID(strCardId);
	    	mFCDbHelper.close();
	    	populateVoiceNoteView();
		}
    }
	
	@Override
	protected void onResume()
	{
		super.onResume();
		populateVoiceNoteView();
	}
}

