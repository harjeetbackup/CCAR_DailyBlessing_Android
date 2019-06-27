package com.spearheadinc.flashcards.prayerapp;
//package com.spearheadinc.flashcards.prayerapp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class CommentDetails extends Activity {
//
//	private static CommentDetails screen;
//	private String mFlashCardId;
//	private RelativeLayout relCustomshapeQuiz;
//	private FCDBHelper mFCDbHelper;
//	private LinearLayout mainLinearLayoutView;
//	
////	public static CommentDetails getInstance() {
////		return screen;
////	}
//    
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) 
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) 
//        {
//			finish();
//			overridePendingTransition(R.anim.hold, R.anim.push_up_out);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//	private Button mAddBut;
//	private String mFromClass= "";
//	
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//		setContentView(R.layout.mycomments);
//		screen = this;
//
//        Bundle extras = getIntent().getExtras();
//        if(extras != null)
//        {
//        	mFlashCardId = extras.getString("com.fadavis.pharmphlashfc.phone.fk_FlashCardId");
//        	mFromClass = extras.getString("com.fadavis.pharmphlashfc.phone.fromclass");
//        }
//		
//        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
//        mainLinearLayoutView = (LinearLayout) findViewById(R.id.mycomments_details_edit_name_lin);
//		
//		Button backBut = (Button) findViewById(R.id.mycomments_details_back);
//		backBut.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				finish();
//				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
//			}
//		});
//		
//		mAddBut = (Button) findViewById(R.id.mycomments_details_add_but);
////		if(mFromClass != null && mFromClass.equals("MYCOMMENTS"))
////			mAddBut.setVisibility(View.VISIBLE);
//		mAddBut.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(CommentDetails.this, NewComments.class);				
//				if(mFlashCardId != null)
//					i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
//					i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", mFromClass);
//				startActivity(i);
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//		});
//
//    	mFCDbHelper.openDataBase();
//    	boolean hasComment = mFCDbHelper.hasCommentNotes(mFlashCardId);
//    	if(hasComment)
//    		mAddBut.setVisibility(View.INVISIBLE);
//    	mFCDbHelper.close();
//		populateCommentDetailView();
//	}
//    
//    public void populateCommentDetailView() {
//    	mainLinearLayoutView.removeAllViews();
//		LayoutInflater mInflater = LayoutInflater.from(this);
//		RelativeLayout seqOrderLinrOutOpt =  (RelativeLayout) mInflater.inflate( R.layout.sequense_order_view, null);
//	    LinearLayout seqOrderLinrOpt =  (LinearLayout) seqOrderLinrOutOpt.findViewById( R.id.sequense_order_layout_bg);
//
//	    List<String[]> list = null;
//    	mFCDbHelper.openDataBase();
//    	list = mFCDbHelper.getCommentSearchResultsForIndividualcard(mFlashCardId);
//    	mFCDbHelper.close();
//	    
//    	for (int i = 0; i < list.size(); i++) 
//    	{
//    		String strArr[] = list.get(i);
//			CreateRowView(strArr[0], seqOrderLinrOpt);
//    		if(i < list.size() - 1)
//    			seqOrderLinrOpt.addView(createSeqUnderLine());
//		}
//    	mainLinearLayoutView.addView(seqOrderLinrOutOpt);
//	}
//	
//	public RelativeLayout createSeqUnderLine () {
//		LayoutInflater mInflater = LayoutInflater.from(this);
//		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.sequential_textview, null);
//		return detailRow;
//	}
//	
//	private RelativeLayout CreateRowView(String notesText, LinearLayout seqOrderLinrOpt)
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
//			setDataTagsToView(notesText, relCustomshapeQuiz, detailRow);
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
//	private void setDataTagsToView(String notesText, RelativeLayout relCustomshapeQuizView, RelativeLayout detailRow1) {
//		List listRel = new ArrayList();
//		listRel.add(notesText);
//		listRel.add(relCustomshapeQuizView);
//		detailRow1.setTag(listRel);
//	}
//    
//	@SuppressWarnings("unchecked")
//	private void setViewStatus(View v)
//    {
//		List listRel = (List) v.getTag();
//		if(listRel != null && listRel.size() > 0)
//		{
//			String notesText = (String) listRel.get(0);
//			if(relCustomshapeQuiz != null)
//				relCustomshapeQuiz.setBackgroundColor(Color.parseColor("#FACE63"));
//			relCustomshapeQuiz = null;
//			relCustomshapeQuiz = (RelativeLayout) listRel.get(1);
//			relCustomshapeQuiz.setBackgroundColor(Color.argb(40, 20, 20, 50));
//			Log.e("detailRow1", notesText +"" + relCustomshapeQuiz);
//			showCommentsDetail(notesText);
//		}
//    }
//
//	private void showCommentsDetail(String notesText) {
//		Intent i = new Intent(CommentDetails.this, NewComments.class);				
//		i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);			
//		i.putExtra("com.fadavis.pharmphlashfc.phone.notesText", notesText);
//		startActivity(i);
//		overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//	}
//}