package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardDetails extends Activity//  implements OnTouchListener
{
	private static CardDetails screen;
    private WebView mWebView; 
    private WebView mWebViewBack; 
    private FCDBHelper mFCDbHelper;
	private Animation fadeIn = null;
	private TextView cardDetail_Front_HeaderCountText = null;
	private TextView cardDetail_Back__HeaderCountText = null;
	
	private RelativeLayout cardDetail_Front_View;
	private RelativeLayout cardDetail_Back_View;
	private ViewGroup mContainer;
	private ImageView flipcardDetail_Front_But;
	private ImageView flipcardDetail_Back_But;
//	private TextView soundSpeakercardDetail_Front_;
//	private TextView soundSpeakercardDetail_Back_;
	private ImageView bookMarkcardDetail_Front_;
	private ImageView bookMarkcardDetail_Back_;
	
	private ImageView iKnowcardDetail_Front_;
	private ImageView iKnowcardDetail_Back_;
	private ImageView iKnowcardDetail_Front_Button;
	private ImageView iKnowcardDetail_Back_Button;
	private ImageView bookMarkcardDetail_Front_Button;
	private ImageView bookMarkcardDetail_Back_Button;
	private int mStartCardNo;
	private int mStartCardNoBookMark;
	private int mLastCardNo;
	private ImageView cardDetail_Front_Next;
	private ImageView cardDetail_Back_Next;
	
	private ImageView cardDetail_Front_Prev;
	private ImageView cardDetail_Back_Prev;
//	private ImageView videoBut;
	private MediaPlayer mp;
	private String cardTypeSelected = "";
	private int profSelAllCard = 0;
	private int profSelBookMark = 0;
	private int []profSelGastro;// = 0;
    private int bookMarkProfcardStatus;
    String currentView = "cardDetail_Front_";
    private List<String> listpk_FlashCardId;
    
    private ImageButton voiceNotesBack;
    private ImageButton voiceNotesFront;
    private ImageButton commentNotesBack;
    private ImageButton commentNotesFront;
    private ImageButton cancelSlideBack;
    private ImageButton cancelSlideFront;
    private Button cardTopNotesBack;
    private Button cardTopNotesFront;
    private RelativeLayout sliderFront;
    private RelativeLayout sliderBack;

    protected List<Integer> soundFileList = new ArrayList<Integer>();
	private boolean isBookmark;
	private boolean isDelete;
	private WebSettings webSettingsBack;
	private List<String> listSearchCardid;
	private List<String> listSearchCardName;/* = new ArrayList<String>()*/;
	String mFromClass = "";
	private WebSettings webSettingsFront;
	private int mDeckIndex;
	private Cursor mCursorFlashCards;
	private RelativeLayout frontLayout;
	private RelativeLayout backLayout;
	private String highlightingJS, stopAudio;
	int searchlen=0;
	int verticalposition=0;
	boolean isUnmarkClick=false;
	private MediaPlayer mpPlayer;
    public static CardDetails getScreen() 
    {
    	return screen;
	}

    /** Called when the activity is first created. */
    @SuppressLint("JavascriptInterface")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		screen = this;
		setContentView(R.layout.carddetail);
		 String externalPath = "/data/data/com.spearheadinc.flashcards.prayer/files/";
	     highlightingJS = "javascript:var call = 0;var mp3FileName; var updateAudioSrcs = function UpdateSrcOfAudios() { var audios = document.getElementsByTagName('audio'); for( var i = 0; i<audios.length; ++i ){ var sources = audios[ i ].getElementsByTagName('source'); for( var j =0; j<sources.length; ++j ) { mp3FileName =sources[j].src.split('/');sources[j].src = \"" + externalPath + "\" + mp3FileName[mp3FileName.length-1];} if( mp3FileName.length ==5 ){ audios[i].src =\"" + externalPath + "\" + mp3FileName[mp3FileName.length-1]; } } };updateAudioSrcs();";
	     stopAudio = "javascript:var call = 0;var mp3FileName; var updateAudioSrcs = function UpdateSrcOfAudios() { var audios = document.getElementsByTagName('audio'); for( var i = 0; i<audios.length; ++i ){ audios[ i ].pause(); audios[ i ].currentTime = 0; }};updateAudioSrcs();";
	     
		mStartCardNo = 0;//DeckView.total;
		
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();

        SharedPreferences myPrefs = null;

		List<String> listPreferenceName = DeckView.getScreen().getListCardsPreferenceName();
        profSelGastro = new int[listPreferenceName.size()];
        if(DeckView.getScreen() != null && !DeckView.getScreen().isdeleteAllProficiency)
        {
	        myPrefs =  DeckView.getScreen().getSharedPreferences("StrAllCardPrefsGenericcd", MODE_PRIVATE);
	        profSelAllCard = myPrefs.getInt("STRALLCARDGENERICcd", 0);
	        myPrefs =  DeckView.getScreen().getSharedPreferences("StrBookMarkPrefsGenericcd", MODE_PRIVATE);
	        profSelBookMark = myPrefs.getInt("STRBOOKMARKGENERICcd", 0);
    		List<String> listPreferenceValue = DeckView.getScreen().getListCardsPreferenceValue();
//    		int indx = listPreferenceName.indexOf(cardTypeSelected);
	        for (int i = 0; i < listPreferenceName.size(); i++) {
		        myPrefs =  DeckView.getScreen().getSharedPreferences(listPreferenceName.get(i), MODE_PRIVATE);
		        profSelGastro[i] = myPrefs.getInt(listPreferenceValue.get(i), 0);
			}
	        
	        myPrefs =  DeckView.getScreen().getSharedPreferences("StrbookMarkProfcardsStatusPrefsGenericcd", MODE_PRIVATE);
	        bookMarkProfcardStatus = myPrefs.getInt("BOOKMARKPROFCARDSTATUScd", 0);
        }
        else
        {
        	profSelAllCard = 0;
        	profSelBookMark = 0;
        	for (int i = 0; i < listPreferenceName.size(); i++) {
        		profSelGastro[i] = 0;
			}
        }
    	Bundle extras = getIntent().getExtras();
		mDeckIndex = 0;
    	String positionstr = "";
    	String totalCardstr = "";
    	mFCDbHelper.openDataBase();
    	if(extras != null)
        {
    		mFromClass = extras.getString("FROM");//getStringExtra
    	//	if(mFromClass != null && mFromClass.equals("SEARCH"))
    		//{
    			positionstr = extras.getString("POSITION");
    			totalCardstr = extras.getString("TOTALCARDS");
    			mSearchString = extras.getString("SEARCHSTRING");
    			String searchStr = extras.getString("CLASSNAME");
        		mStartCardNo = Integer.parseInt(positionstr);
        		mStartCardNoBookMark = mStartCardNo;
        		mLastCardNo = Integer.parseInt(totalCardstr);
        		
        	//	mFCDbHelper.openDataBase();
        		if(Search.getInstance() != null && searchStr.equals("NORMALSEARCH"))
        		{
        			isBookmark = extras.getBoolean("isBookmarked");
        			listSearchCardid = Search.getInstance().getListSearchCardid();//Searchpk_FlashCardFrontBackDetailId();
        		}
        		else if(IndexSearch.getInstance() != null && searchStr.equals("INDEXSEARCH"))
        		{
        			isBookmark = extras.getBoolean("isBookmarked");
        			listSearchCardid = IndexSearch.getInstance().getListSearchCardid();
        		}
        			
        		/*else if(isBookmark)
        		{
        		 	mFCDbHelper.openDataBase();
//	        		listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
//	//            	mFCDbHelper.close();
//	        		mLastCardNo = listpk_FlashCardId.size();
	        		
	        		mCursorFlashCards = mFCDbHelper.getBookMarkedCardCursor();
	        		mLastCardNo = mCursorFlashCards.getCount();
	        		if(mLastCardNo<1)
	        		{
		    	        AlertDialog.Builder alt_bld = new AlertDialog.Builder(CardDetails.this);
		    	        alt_bld.setMessage("Please select some card first").setCancelable(false)
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
		        		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, mLastCardNo);
		        		mStartCardNoBookMark = 0;
//		    			String cardIDNo = listpk_FlashCardId.get(mStartCardNoBookMark);
//		        		mStartCardNo = Integer.parseInt(cardIDNo);
		        		isBookmark = true;
		        		isDelete = true;
	        		}
	        		listSearchCardid = ListCardName.getInstance().getListSearchCardid();
        		}*/
        		else
        		{
        			listSearchCardid = ListCardName.getInstance().getListSearchCardid();
        		    listSearchCardName = ListCardName.getInstance().getListSearchCardName();
        		    isBookmark = extras.getBoolean("isBookmarked");
                 	Log.i("CardDetails", "List Size: " + listSearchCardid.size());
        		}
        		mFCDbHelper.close();
    	//	}
    		/*else
    		{
    			mSearchString = "";
	        	cardTypeSelected = extras.getString("com.android.flashcard.screen.cardDetail");
	        	if(cardTypeSelected.equals("all_cards"))
	        	{
//	        		mStartCardNo = 1;
	        		mStartCardNoBookMark = 0;
	        		mCursorFlashCards = mFCDbHelper.getm_FlashCardAllCursor();
	        		mLastCardNo = mCursorFlashCards.getCount();
	        		isBookmark = false;
	        	}
	        	else if(cardTypeSelected.equals("bookmarked_cards"))
	        	{
	//            	mFCDbHelper.openDataBase();
//	        		listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
//	//            	mFCDbHelper.close();
//	        		mLastCardNo = listpk_FlashCardId.size();
	        		
	        		mCursorFlashCards = mFCDbHelper.getBookMarkedCardCursor();
	        		mLastCardNo = mCursorFlashCards.getCount();
	        		if(mLastCardNo<1)
	        		{
		    	        AlertDialog.Builder alt_bld = new AlertDialog.Builder(CardDetails.this);
		    	        alt_bld.setMessage("Please select some card first").setCancelable(false)
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
		        		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, mLastCardNo);
		        		mStartCardNoBookMark = 0;
//		    			String cardIDNo = listpk_FlashCardId.get(mStartCardNoBookMark);
//		        		mStartCardNo = Integer.parseInt(cardIDNo);
		        		isBookmark = true;
		        		isDelete = true;
	        		}
	        	}
	        	else 
        		{
	        		List<String> strArrPreferenceValue = DeckView.getScreen().getListDeckPreferenceValue();
	        		mDeckIndex = strArrPreferenceValue.indexOf(cardTypeSelected);
	            	mFCDbHelper.openDataBase();
	        		mStartCardNoBookMark = 0;
	        		
	        		mCursorFlashCards = mFCDbHelper.getm_FlashCardInfoCursor("" + mDeckIndex);
	        		mLastCardNo = mCursorFlashCards.getCount();
//	        		mLastCardNo = mFCDbHelper.getDeksTotalNumOfCards("" + indx);
//	            	mFCDbHelper.close();
	        		isBookmark = false;
	        	}
	    		mCursorFlashCards.moveToFirst();
	        }*/
        }

        voiceNotesBack = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_voicenotes_back);
        voiceNotesFront = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_voicenotes_front);
        commentNotesBack = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_comments_back);
        commentNotesFront = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_comments_front);
        cancelSlideBack = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_cancel_back);
        cancelSlideFront = (ImageButton) findViewById(R.id.carddetails_sliderinfo_but_cancel_front);
        cardTopNotesBack = (Button) findViewById(R.id.carddetail_backinfo_icon);
        cardTopNotesFront = (Button) findViewById(R.id.carddetail_frontinfo_icon);
        sliderFront = (RelativeLayout) findViewById(R.id.carddetails_sliderinfo_top_rel_front);
        sliderBack = (RelativeLayout) findViewById(R.id.carddetails_sliderinfo_top_rel_back);
        frontLayout = (RelativeLayout) findViewById(R.id.relativecarddetail_front);
        backLayout = (RelativeLayout) findViewById(R.id.carddetail_back_relativetop);
        
        voiceNotesBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {  
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderBack.startAnimation(slide);
			    sliderBack.setVisibility(View.GONE);
				Intent i = new Intent(CardDetails.this, VoiceNoteDetails.class);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
				backLayout.removeView(sliderBack);
			}
		});
        
        voiceNotesFront.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {  
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderFront.startAnimation(slide);
			    sliderFront.setVisibility(View.GONE);
				Intent i = new Intent(CardDetails.this, VoiceNoteDetails.class);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
				frontLayout.removeView(sliderFront);
			}
		});
        
        commentNotesBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {  
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderBack.startAnimation(slide);
			    sliderBack.setVisibility(View.GONE);
				Intent i = new Intent(CardDetails.this, NewComments.class);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "CARDDETAILS");
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
				backLayout.removeView(sliderBack);
			}
		});
        
        commentNotesFront.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{  
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderFront.startAnimation(slide);
			    sliderFront.setVisibility(View.GONE);
				Intent i = new Intent(CardDetails.this, NewComments.class);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fk_FlashCardId", mFlashCardId);
				i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "CARDDETAILS");
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
				frontLayout.removeView(sliderFront);
			}
		});
        
        cancelSlideBack.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{   
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderBack.startAnimation(slide);
			    sliderBack.setVisibility(View.GONE);
			    backLayout.removeView(sliderBack);
			}
		});
        
        cancelSlideFront.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{   
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 400);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderFront.startAnimation(slide);
			    sliderFront.setVisibility(View.GONE);
			    frontLayout.removeView(sliderFront);
			}
		});
        
        cardTopNotesBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backLayout.removeView(sliderBack);
				backLayout.addView(sliderBack);
				sliderBack.setVisibility(View.VISIBLE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 100, 0);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderBack.startAnimation(slide);
			}
		});
        
        cardTopNotesFront.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(CardDetails.this, Search.class);
				startActivity(i);
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			  /*	frontLayout.removeView(sliderFront);
				frontLayout.addView(sliderFront);
				sliderFront.setVisibility(View.VISIBLE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 100, 0);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    sliderFront.startAnimation(slide);*/
			}
		});
        
//    	mFCDbHelper.close();
		Log.e("cardTypeSelected ", mStartCardNoBookMark + " : " + mLastCardNo);
        mWebView = (WebView) findViewById(R.id.web_front); 
        mWebView.setBackgroundColor(0);
        /*
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.setHorizontalScrollbarOverlay(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mWebView.getSettings().setMixedContentMode(
					WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
		}
        webSettingsFront = mWebView.getSettings(); 
        webSettingsFront.setJavaScriptEnabled(true); 
        webSettingsFront.setLoadWithOverviewMode(true);
        webSettingsFront.setUseWideViewPort(true);
     //   final JavaScriptInterface myJavaScriptInterface
     //	= new JavaScriptInterface(this);
        mWebView.addJavascriptInterface(new JavaScriptInterface(this) , "android");
        mWebView.setWebChromeClient(new MyWebChromeClient());
        webSettingsFront.setBuiltInZoomControls(true);
        webSettingsFront.setSupportZoom(true); //set to true to suport the zoom feature
        webSettingsFront.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettingsFront.setLightTouchEnabled(true);

        mWebViewBack = (WebView) findViewById(R.id.web_back);  
        mWebViewBack.setBackgroundColor(0);
        webSettingsBack = mWebViewBack.getSettings(); 
        webSettingsBack.setJavaScriptEnabled(true);
        webSettingsBack.setLoadWithOverviewMode(true);
        webSettingsBack.setUseWideViewPort(true);
      
        mWebViewBack.addJavascriptInterface(new JavaScriptInterface(this), "android");
       // mWebViewBack.setWebChromeClient(new MyWebChromeClient());

	    webSettingsBack.setBuiltInZoomControls(true);
        webSettingsBack.setSupportZoom(true); //set to true to suport the zoom feature
//        webSettingsBack.setLightTouchEnabled(true); //set to true enables touches and mouseovers

  */
//        ZOOM_PARAMS.setMargins(0, 0, 0, 60);
//        FrameLayout mContentView = (FrameLayout) DeckView.getScreen().getWindow().getDecorView().findViewById(android.R.id.content);
//        final View zoom = this.mWebView.getZoomControls();
//        mContentView.addView(zoom, ZOOM_PARAMS);
        
        
        
        front_Animatable_View = (RelativeLayout) findViewById(R.id.carddetail_frontrelandroid);
    	setScreenData();
        
      /*  if(mStartCardNoBookMark<= 0)
    	{
        	cardDetail_Front_Prev.setEnabled(false);
        	cardDetail_Front_Prev.setClickable(false);
        	cardDetail_Back_Prev.setEnabled(false);
        	cardDetail_Back_Prev.setClickable(false);
    	}
        else
	    {
        	cardDetail_Front_Prev.setEnabled(true);
        	cardDetail_Front_Prev.setClickable(true);
        	cardDetail_Back_Prev.setEnabled(true);
        	cardDetail_Back_Prev.setClickable(true);
	    }*/
        
      /*   if( (mStartCardNoBookMark + 1) >= mLastCardNo)
        {
        	cardDetail_Front_Next.setEnabled(false);
	    	cardDetail_Front_Next.setClickable(false);
        	cardDetail_Back_Next.setEnabled(false);
        	cardDetail_Back_Next.setClickable(false);
         }
         else
 	    {
        	 cardDetail_Front_Next.setEnabled(true);
        	 cardDetail_Front_Next.setClickable(true);
        	 cardDetail_Front_Next.setEnabled(true);
        	 cardDetail_Front_Next.setClickable(true);
 	    }*/

	/*    else if(mStartCardNo >= 1 && mStartCardNo <= mLastCardNo)
    	{
		    manageCurrentCardStatus();
    	}

	    else if(mFromClass != null && mFromClass.equals("SEARCH") && mStartCardNo >= 0 && mStartCardNo <= mLastCardNo)
    	{
		    manageCurrentCardStatus();
    	}*/
        
    	bookMarkcardDetail_Back_Button.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				
				handleBookMarkTabButtonEvent();
				
			}
		});
        
    	bookMarkcardDetail_Front_Button.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				
		    		cardDetail_Front_Prev.setEnabled(false);
		        	cardDetail_Back_Prev.setEnabled(false);
		        	cardDetail_Front_Prev.setClickable(false);
		        	cardDetail_Back_Prev.setClickable(false);
		    	
		    		cardDetail_Front_Next.setEnabled(false);
		    		cardDetail_Back_Next.setEnabled(false);
		    		cardDetail_Front_Next.setClickable(false);
		    		cardDetail_Back_Next.setClickable(false);
		    	
				handleBookMarkTabButtonEvent();
				cardDetail_Front_Prev.setEnabled(true);
	        	cardDetail_Back_Prev.setEnabled(true);
	        	cardDetail_Front_Prev.setClickable(true);
	        	cardDetail_Back_Prev.setClickable(true);
	    	
	    		cardDetail_Front_Next.setEnabled(true);
	    		cardDetail_Back_Next.setEnabled(true);
	    		cardDetail_Front_Next.setClickable(true);
	    		cardDetail_Back_Next.setClickable(true);
			
			}
		});
    	iKnowcardDetail_Front_Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				handleIKnowTabButtonEvent();
			}
		});
        
    	iKnowcardDetail_Back_Button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				handleIKnowTabButtonEvent();
			}
		});
    	
    	  manageCurrentCardStatus();
    }
//	String mFlashCardId;

    private static final FrameLayout.LayoutParams ZOOM_PARAMS =  new FrameLayout.LayoutParams(
    						ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
    
    
    private void recalculateProficiency(int i)
    {
    	mFCDbHelper.openDataBase();
    	String strBookMark = mFCDbHelper.getDeckID(""+mFlashCardId);
    	int k = Integer.parseInt(strBookMark);
    	mFCDbHelper.close();
    	if( i == 1)
    	{
    		incrementProficiency(k);
    	}
    	else
    		decrementProficiency(k);
    }
    
	private void incrementProficiency(int i)
	{
		mFCDbHelper.openDataBase();
		profSelGastro[i] ++;
		int total = mFCDbHelper.getDeksTotalNumOfCards("" + i);
		DeckView.getScreen().setProfSelGastro(profSelGastro[i], total, i);
		DeckView.getScreen().storeDataInPreferences(profSelGastro[i], i);
		mFCDbHelper.close();
		profSelAllCard = 0;
    	for (int j = 0; j < profSelGastro.length; j++) {
			profSelAllCard += profSelGastro[j];
		}
    	DeckView.getScreen().setStrAllCard(profSelAllCard);
    	DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
	}
	
	private void decrementProficiency(int i)
	{
		mFCDbHelper.openDataBase();
		profSelGastro[i] --;
		int total = mFCDbHelper.getDeksTotalNumOfCards("0");
		DeckView.getScreen().setProfSelGastro(profSelGastro[i], total, i);
		DeckView.getScreen().storeDataInPreferences(profSelGastro[i], i);
		mFCDbHelper.close();
    	profSelAllCard = 0;
    	for (int j = 0; j < profSelGastro.length; j++) {
			profSelAllCard += profSelGastro[j];
		}
    	DeckView.getScreen().setStrAllCard(profSelAllCard);
    	DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
	}
    
    private void handleIKnowTabButtonEvent()
    {
		mFCDbHelper.openDataBase();
		String i = mFCDbHelper.getKnownCardStatus(""+mFlashCardId);
    	mFCDbHelper.close();
    	if(i.equals("1"))//currentiKnow.equals("1")
    	{
    		setiKnowPramsINVisible();
			manageUpdateKnowInDB("0");
	    	if(cardTypeSelected.equals("all_cards"))
	    	{
            	recalculateProficiency(0);
	    	}
            decrementProficiency();
            if(isBookmark)
            	recalculateProficiency(0);
    	}
    	else
    	{
    		
    		setiKnowPramsVisible();
			manageUpdateKnowInDB("1");
	    	if(cardTypeSelected.equals("all_cards"))
	    	{
            	recalculateProficiency(1);
	    	}
            incrementProficiency();
            if(isBookmark)
            	recalculateProficiency(1);
    	}
    }

    private void handleBookMarkTabButtonEvent()
    {
        isDelete = false;
       
		mFCDbHelper.openDataBase();
		String i = mFCDbHelper.getBookMarkedCardStatus(""+mFlashCardId);
		mFCDbHelper.close();
		Log.i( "CardDetails", "isBookmark: ############# " + isBookmark );
		if(i.equals("1"))
    	{
			manageUpdateBookMarkInDB("0");
			
		    isDelete = true;
			
    	}
		if(!isBookmark)
		{
    	if(i.equals("1"))
    	{
    		setBookMarkPramsINVisible();
    	}
    	else
    	{
    		setBookMarkPramsVisible();
			manageUpdateBookMarkInDB("1");
    	}
		}
    	mFCDbHelper.openDataBase();
    	
    	if(isBookmark)
    	{
			mCursorFlashCards = mFCDbHelper.getBookMarkedCardCursor();
			Log.i( "CardEtails", "mStartCardNoBookMark = " + mStartCardNoBookMark );
			mCursorFlashCards.moveToPosition(mStartCardNoBookMark);
			mLastCardNo = mCursorFlashCards.getCount();
    	}
    	
		List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
		//listpk_FlashCardId.remove(mStartCardNoBookMark);
		
	    bookMarkProfcardStatus = mFCDbHelper.getKnownBookMarkedCardStatus();
	    profSelBookMark = listpk_FlashCardId.size();
		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, profSelBookMark);
		DeckView.getScreen().storeDataInPreferences(profSelBookMark, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
		DeckView.getScreen().storeDataInPreferences(bookMarkProfcardStatus, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");
    	mFCDbHelper.close();

	    if(isDelete && isBookmark)
	    {
	    	isUnmarkClick=true;
	    	 listSearchCardid.remove(mStartCardNoBookMark);
	    	 listSearchCardName.remove(mStartCardNoBookMark);
	    	if(mCursorFlashCards != null && mCursorFlashCards.getCount() == 0)
//	    	if(listpk_FlashCardId.size() == 0)
	    	{
	    		finish();
	    		
	    	}
//	    	else if(listpk_FlashCardId.size() == 1)
	    	/*else if(mCursorFlashCards != null && mCursorFlashCards.getCount() == 1)
	    	{
	    		mLastCardNo = 1;
	    		mStartCardNoBookMark = 0;
//				String cardIDNo = listpk_FlashCardId.get(mStartCardNoBookMark);
//	    		mStartCardNo = Integer.parseInt(cardIDNo);
			    manageCurrentCardStatus();
	    		cardDetail_Back_Prev.setEnabled(false);
	    		cardDetail_Front_Prev.setEnabled(false);
	    	}*/
	    	else
	    	{
	    		mLastCardNo = profSelBookMark;
	    		if(mStartCardNoBookMark == 0)
	    		{
	    			mStartCardNoBookMark = 1;
	    			mStartCardNo = 1;  
	    		}
	    		//if(mStartCardNo>0)
	    		
	    		setPrevScreenParameters();
	    		//else if(mStartCardNo<mLastCardNo)
	    		//setNextScreenParameters();
	    	}
	    	
	    }
    }
    
	public int getmLastCardNo() {
		return mLastCardNo;
	}
    
	private void incrementProficiency()
	{
		int totals = getmLastCardNo();
    	if(cardTypeSelected.equals("all_cards"))
    	{
    		DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
    	}
    	else if(cardTypeSelected.equals("bookmarked_cards"))
    	{
    		DeckView.getScreen().storeDataInPreferences(profSelBookMark, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
    	}
    	else //if(cardTypeSelected.equals("clickGastro"))
    	{
    		List<String> strArrPreferenceValue = DeckView.getScreen().getListDeckPreferenceValue();
    		int indx = strArrPreferenceValue.indexOf(cardTypeSelected);
    		profSelGastro[indx] ++;
    		DeckView.getScreen().setProfSelGastro(profSelGastro[indx], totals, indx);
    		DeckView.getScreen().storeDataInPreferences(profSelGastro[indx], indx);
    	}
    	profSelAllCard = 0;
    	for (int i = 0; i < profSelGastro.length; i++) {
			profSelAllCard += profSelGastro[i];
		}
		mFCDbHelper.openDataBase();
		List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
	    bookMarkProfcardStatus = mFCDbHelper.getKnownBookMarkedCardStatus();
		mFCDbHelper.close();
	    profSelBookMark = listpk_FlashCardId.size();
		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, profSelBookMark);
    	DeckView.getScreen().setStrAllCard(profSelAllCard);
    	DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
	}
	
	private void decrementProficiency()
	{
		int totals = getmLastCardNo();
    	if(cardTypeSelected.equals("all_cards"))
    	{
    		DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
    	}
    	else if(cardTypeSelected.equals("bookmarked_cards"))
    	{
    		DeckView.getScreen().storeDataInPreferences(profSelBookMark, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
    	}
    	else //if(cardTypeSelected.equals("clickGastro"))
    	{
    		List<String> strArrPreferenceValue = DeckView.getScreen().getListDeckPreferenceValue();
    		int indx = strArrPreferenceValue.indexOf(cardTypeSelected);
    		profSelGastro[indx] --;
    		DeckView.getScreen().setProfSelGastro(profSelGastro[indx], totals, indx);
    		DeckView.getScreen().storeDataInPreferences(profSelGastro[indx], indx);
    	}
    	profSelAllCard = 0;
    	for (int i = 0; i < profSelGastro.length; i++) {
			profSelAllCard += profSelGastro[i];
		}
		mFCDbHelper.openDataBase();
		List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
	    bookMarkProfcardStatus = mFCDbHelper.getKnownBookMarkedCardStatus();
		mFCDbHelper.close();
	    profSelBookMark = listpk_FlashCardId.size();
		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, profSelBookMark);
    	DeckView.getScreen().setStrAllCard(profSelAllCard);
    	DeckView.getScreen().storeDataInPreferences(profSelAllCard, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
	}
    
    private void setNextScreenParameters()
    {
    	
    	if(currentView.equals("Trade"))
    	{
    		cardDetail_Back_View.setVisibility(View.GONE);
	        cardDetail_Front_View.setVisibility(View.VISIBLE);
	        cardDetail_Front_View.requestFocus();
    	}
//    	cardDetail_Back_Prev.setEnabled(true);
    	cardDetail_Front_Prev.setEnabled(true);
    	cardDetail_Front_Prev.setClickable(true);
    	cardDetail_Front_Prev.setImageResource(R.drawable.prev);
//	    fadeIn = AnimationUtils.loadAnimation(CardDetails.this, R.anim.push_left_in);
//	    front_Animatable_View.startAnimation(fadeIn);
//	    if(isBookmark)
//	    {
//    		mStartCardNoBookMark ++;
//			String cardIDNo = listpk_FlashCardId.get(mStartCardNoBookMark);
//    		mStartCardNo = Integer.parseInt(cardIDNo);
//    		
//		    manageCurrentCardStatus();
//        	
//        	if(mStartCardNoBookMark + 1 == mLastCardNo)
//        	{
//        		cardDetail_Back_Next.setEnabled(false);
//        		cardDetail_Front_Next.setEnabled(false);
//        	}
//	    }
//	    else
//	    {
//		    ++ mStartCardNo;
//    		mStartCardNoBookMark ++;
//		    manageCurrentCardStatus();
//	    	
//	    	if(/*mStartCardNo == mLastCardNo*/mStartCardNoBookMark + 1 >= mLastCardNo)
//	    	{
//	    		cardDetail_Back_Next.setEnabled(false);
//	    		cardDetail_Front_Next.setEnabled(false);
//	    	}
//	    }
	    ++ mStartCardNo;
	    //mStartCardNo++;
		mStartCardNoBookMark ++;
//	    manageCurrentCardStatus();
    	
    	if(mStartCardNoBookMark+1  >= mLastCardNo)
    	{
    		cardDetail_Front_Next.setImageResource(R.drawable.next_d);
    		cardDetail_Front_Next.setEnabled(false);
    		cardDetail_Back_Next.setEnabled(false);
    		cardDetail_Front_Next.setClickable(false);
    		cardDetail_Back_Next.setClickable(false);
    	}
    	else
    	{
    		cardDetail_Front_Next.setImageResource(R.drawable.next);
    		cardDetail_Front_Next.setEnabled(true);
    		cardDetail_Front_Next.setEnabled(true);
    		cardDetail_Front_Next.setClickable(true);
    		cardDetail_Front_Next.setClickable(true);
    	   
    	}
    	
    	 fadeIn = AnimationUtils.loadAnimation(DeckView.getScreen(), R.anim.push_left_in);
 	    front_Animatable_View.startAnimation(fadeIn);

 	    if(mCursorFlashCards != null)
 	    {
	    	    boolean isCursordata = mCursorFlashCards.moveToNext();
	    	    if(!isCursordata)
	    	    	mCursorFlashCards.moveToLast();
 	    }
	    	manageCurrentCardStatus();
		  
    	//Log.e("*********  ", mStartCardNo + "             " + mLastCardNo + "       "  + mFlashCardId );
    }
    private RelativeLayout front_Animatable_View = null;
//	private ImageView videoButBack;
//	private ImageView soundSpeakercardDetail_Front_Icon;
//	private ImageView soundSpeakercardDetail_Back_Icon;
    
    private void setPrevScreenParameters() 
    {
    	
    	if(currentView.equals("Trade"))
    	{
    		cardDetail_Back_View.setVisibility(View.GONE);
	        cardDetail_Front_View.setVisibility(View.VISIBLE);
	        cardDetail_Front_View.requestFocus();
    	}
		    --mStartCardNo;
		    --mStartCardNoBookMark;
	    	//mStartCardNo--;
			  //mStartCardNoBookMark--;

	    	if(mStartCardNoBookMark <=0)
	    	{
	    		cardDetail_Front_Prev.setImageResource(R.drawable.prev_d);
	    		cardDetail_Front_Prev.setEnabled(false);
	        	cardDetail_Back_Prev.setEnabled(false);
	        	cardDetail_Front_Prev.setClickable(false);
	        	cardDetail_Back_Prev.setClickable(false);
	    	}
	    	else
	    	{
	    		cardDetail_Front_Prev.setImageResource(R.drawable.prev);
	    		cardDetail_Front_Prev.setEnabled(true);
	    		cardDetail_Back_Prev.setEnabled(true);
	    		cardDetail_Front_Prev.setClickable(true);
	    		cardDetail_Back_Prev.setClickable(true);
	    	}
	    	if(mStartCardNoBookMark+1>=mLastCardNo)
	    	{
	    		cardDetail_Front_Next.setImageResource(R.drawable.next_d);
	    		cardDetail_Front_Next.setEnabled(false);
	    		cardDetail_Back_Next.setEnabled(false);
	    		cardDetail_Front_Next.setClickable(false);
	    		cardDetail_Back_Next.setClickable(false);
	    	}
	    	else
	    	{
	    		cardDetail_Front_Next.setImageResource(R.drawable.next);
	    		cardDetail_Front_Next.setEnabled(true);
	    		cardDetail_Back_Next.setEnabled(true);
	    		cardDetail_Front_Next.setClickable(true);
	    		cardDetail_Back_Next.setClickable(true);
	    	}
	    	
	    	    fadeIn = AnimationUtils.loadAnimation(DeckView.getScreen(), R.anim.push_left_out);
	    	    front_Animatable_View.startAnimation(fadeIn);
//	    	    if(!isBookmark)
	    	    if(mCursorFlashCards != null)
	    	    {
		    	    boolean isCursordata = mCursorFlashCards.moveToPrevious();
		    	    if(!isCursordata)
		    	    	mCursorFlashCards.moveToFirst();
	    	    }
			    manageCurrentCardStatus();
			  
	    }
    

    FrameLayout mContentView;
    
    private void showSoundVisible(String frorbk)
    {
//	  		soundSpeakercardDetail_Front_.setVisibility(View.VISIBLE);
//	  		soundSpeakercardDetail_Front_Icon.setVisibility(View.VISIBLE);
//	  		soundSpeakercardDetail_Back_.setVisibility(View.VISIBLE);
//	  		soundSpeakercardDetail_Back_Icon.setVisibility(View.VISIBLE);
    }
    
    private String mFlashCardId = "";
    
    private void manageCurrentCardStatus(/*String [] str*/)
    {
    	showSoundVisible("1");
    	mFCDbHelper.openDataBase();
    	cardDetail_Front_HeaderCountText.setText((mStartCardNoBookMark + 1) + " of " + (mLastCardNo));
    	cardDetail_Back__HeaderCountText.setText((mStartCardNoBookMark + 1) + " of " + (mLastCardNo));

    	String strBookMark = "";
    	String striKnow = "";
    	String htmlName = "";
		boolean hasValueVoice = false;
		boolean hasValueComment = false;
    	
    	if(mFromClass != null && mFromClass.equals("SEARCH") && !isUnmarkClick)
		{
	    	if(listSearchCardid != null && listSearchCardid.size() > 0)
			{
	    		mFlashCardId = listSearchCardid.get(mStartCardNo);
	    		String cardIDNo = mFCDbHelper.getCardDeckid(mFlashCardId);
        		int deckNo = Integer.parseInt(cardIDNo);
        		List<String> listDeckPreferenceValue = DeckView.getScreen().getListDeckPreferenceValue();
	    		cardTypeSelected = listDeckPreferenceValue.get(deckNo);
			}
//	    	if(listSearchCardid != null && listSearchCardid.size() > 0)
//			{
//	    		String cardIDNo = mFCDbHelper.getCardDeckid(""+listSearchCardid.get(mStartCardNo));
//        		int deckNo = Integer.parseInt(cardIDNo);
//        		List<String> listDeckPreferenceValue = DeckView.getScreen().getListDeckPreferenceValue();
//	    		cardTypeSelected = listDeckPreferenceValue.get(deckNo);
//	    	
//	    		hasValueVoice = mFCDbHelper.hasVoiceNotes(""+listSearchCardid.get(mStartCardNo));
//	    		hasValueComment = mFCDbHelper.hasCommentNotes(""+listSearchCardid.get(mStartCardNo));
//	    		
//	    		strBookMark = mFCDbHelper.getBookMarkedCardStatus(""+listSearchCardid.get(mStartCardNo));
//		    	striKnow = mFCDbHelper.getKnownCardStatus(""+ listSearchCardid.get(mStartCardNo));
//	    		Log.e("listfront)", mStartCardNo + "  ;  " + listSearchCardid.get(mStartCardNo) + "	;	" /*+ Search.getInstance().getListSearchCardName().get(mStartCardNo)*/);
//	    		
//	    		String str = listSearchCardid.get(mStartCardNo);
//	    		mFlashCardId = str;
//	    		int i = Integer.parseInt(str);
//	    		i = i * 2 - 1;
//	    		
//		    	htmlName = mFCDbHelper.getHTMLName(""+i, true);
//			}
		}
//    	else
//    	{
//	    	
//    		hasValueVoice = mFCDbHelper.hasVoiceNotes(""+mStartCardNo);
//    		hasValueComment = mFCDbHelper.hasCommentNotes(""+mStartCardNo);
//    		
//	    	strBookMark = mFCDbHelper.getBookMarkedCardStatus(""+mStartCardNo);
//	    	striKnow = mFCDbHelper.getKnownCardStatus(""+ mStartCardNo);
//
//    		mFlashCardId = ""+ mStartCardNo;
//	    	htmlName = mFCDbHelper.getHTMLName(""+((mStartCardNo*2)-1), true);
//    	}
    	else
//        	{
//        		if(mCursorFlashCards.)
        		mFlashCardId = mCursorFlashCards.getString(mCursorFlashCards.getColumnIndex(FCDBHelper.PK_FLASHCARDID));
    		    //mFlashCardId = ""+ mStartCardNo+1;
    			String soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
    			if(soundName != null && !soundName.equalsIgnoreCase(""))
    			{
    				showSoundIcon();
    			}
    			else
    				hideSoundIcon();
//        		mCursorFlashCards.moveToNext();
//    			mFlashCardId = mFCDbHelper.getFlashCardId(mStartCardNo, mDeckIndex);
        		hasValueVoice = mFCDbHelper.hasVoiceNotes(""+mFlashCardId);
        		hasValueComment = mFCDbHelper.hasCommentNotes(""+mFlashCardId);
        		
    	    	strBookMark = mFCDbHelper.getBookMarkedCardStatus(""+mFlashCardId);
    	    	striKnow = mFCDbHelper.getKnownCardStatus(""+ mFlashCardId);

//        		mCurrentCardID = ""+ mFlashCardId;
        		
    	    	htmlName = mFCDbHelper.getHTMLName(""+mFlashCardId, true);
        	//	Log.e("htmlName   htmlName )   ", mStartCardNo + "  ;  " + mFlashCardId + "	;	" + htmlName);
//        	}
    		
		
	/*	if(hasValueVoice || hasValueComment)
		{
    		cardTopNotesBack.setBackgroundResource(R.drawable.exter_window_ic_yel19);
    		cardTopNotesFront.setBackgroundResource(R.drawable.exter_window_ic_yel19);
		}
		else
		{
    		cardTopNotesBack.setBackgroundResource(R.drawable.exter_window_ic);
    		cardTopNotesFront.setBackgroundResource(R.drawable.exter_window_ic);
		}*/
		
    	mFCDbHelper.close(); 
    	
        mWebView.loadUrl("file:///android_asset/" + htmlName);
        
        if(mSearchString != null && !mSearchString.equals(""))
		{
        	//runFront("javascript:var removehighlights=function MyApp_RemoveAllHighlightsForElement(element) { if (element) {    if (element.nodeType == 1) {if (element.getAttribute(\"class\") == \"MyAppHighlight\") {var text = element.removeChild(element.firstChild);  element.parentNode.insertBefore(text,element); element.parentNode.removeChild(element);  return true;   } else {  var normalize = false;  for (var i=element.childNodes.length-1; i>=0; i--) { if (MyApp_RemoveAllHighlightsForElement(element.childNodes[i])) {   normalize = true;      }  } if (normalize) { element.normalize();  }} }} return false;}");
        	runFront("javascript:var highlightWords = function MyApp_HighlightAllOccurencesOfStringForElement(element,keyword) { var MyApp_SearchResultCount = 0; if (element) {if (element.nodeType == 3) {        while (true) {var value = element.nodeValue;  var idx = value.toLowerCase().indexOf(keyword);if (idx < 0) break;var span = document.createElement(\"span\");var text = document.createTextNode(value.substr(idx,keyword.length)); span.appendChild(text);span.setAttribute(\"class\",\"MyAppHighlight\");span.style.backgroundColor=\"yellow\";span.style.color=\"black\";text = document.createTextNode(value.substr(idx+keyword.length));element.deleteData(idx, value.length - idx);var next = element.nextSibling;element.parentNode.insertBefore(span, next);element.parentNode.insertBefore(text, next);element = text;MyApp_SearchResultCount++;	}} else if (element.nodeType == 1) { if (element.style.display != \"none\" && element.nodeName.toLowerCase() != 'select') {for (var i=element.childNodes.length-1; i>=0; i--) {MyApp_HighlightAllOccurencesOfStringForElement(element.childNodes[i],keyword);}}}}};highlightWords(document.body, \""+mSearchString+"\".toLowerCase());");
        	

		}
//        mWebView.reload();
        if(strBookMark != null && !strBookMark.equals(""))
        {
        	if(strBookMark.equals("1"))
        	{
        		setBookMarkPramsVisible();
        	}
        	else
        	{
        		setBookMarkPramsINVisible();
        	}
        }
    	else
    	{
    		setBookMarkPramsINVisible();
    	}
        if(striKnow != null && !striKnow.equals(""))
        {
        	if(striKnow.equals("1"))
        	{
        		setiKnowPramsVisible();
        	}
        	else
        	{
        		setiKnowPramsINVisible();
        	}
        }
    	else
    	{
    		setiKnowPramsINVisible();
    	}
    }
    
    private void hideSoundIcon() 
    {
//    	soundSpeakercardDetail_Back_.setVisibility(View.INVISIBLE);
//    	soundSpeakercardDetail_Front_.setVisibility(View.INVISIBLE);
//    	soundSpeakercardDetail_Back_.setEnabled(false);
//    	soundSpeakercardDetail_Front_.setEnabled(false);
//    	soundSpeakercardDetail_Back_Icon.setVisibility(View.INVISIBLE);
//    	soundSpeakercardDetail_Front_Icon.setVisibility(View.INVISIBLE);
	}
    
    private void showSoundIcon() 
    {
//    	soundSpeakercardDetail_Back_.setVisibility(View.VISIBLE);
//    	soundSpeakercardDetail_Front_.setVisibility(View.VISIBLE);
//    	soundSpeakercardDetail_Back_.setEnabled(true);
//    	soundSpeakercardDetail_Front_.setEnabled(true);
//    	soundSpeakercardDetail_Back_Icon.setVisibility(View.VISIBLE);
//    	soundSpeakercardDetail_Front_Icon.setVisibility(View.VISIBLE);
	}
    
    private void setiKnowPramsVisible()
    {
    	iKnowcardDetail_Front_.setVisibility(View.VISIBLE);
    	iKnowcardDetail_Back_.setVisibility(View.VISIBLE);
    	iKnowcardDetail_Front_Button.setImageResource(R.drawable.dnt_knw);
    	iKnowcardDetail_Back_Button.setImageResource(R.drawable.dnt_knw);
    }
    
    private void setiKnowPramsINVisible()
    {
    	iKnowcardDetail_Front_.setVisibility(View.INVISIBLE);
		iKnowcardDetail_Back_.setVisibility(View.INVISIBLE);
		iKnowcardDetail_Front_Button.setImageResource(R.drawable.know);
		iKnowcardDetail_Back_Button.setImageResource(R.drawable.know);
    }
    
    private void setBookMarkPramsVisible()
    {
    	bookMarkcardDetail_Front_.setVisibility(View.VISIBLE);
    	bookMarkcardDetail_Front_Button.setImageResource(R.drawable.unmark);
		bookMarkcardDetail_Back_.setVisibility(View.VISIBLE);
		bookMarkcardDetail_Back_Button.setImageResource(R.drawable.unmark);
    }
    
    private void setBookMarkPramsINVisible()
    {
    	bookMarkcardDetail_Front_.setVisibility(View.INVISIBLE);
    	bookMarkcardDetail_Front_Button.setImageResource(R.drawable.bookmark);
		bookMarkcardDetail_Back_.setVisibility(View.INVISIBLE);
		bookMarkcardDetail_Back_Button.setImageResource(R.drawable.bookmark);
    }
    
    private void manageUpdateKnowInDB(String knowcardCount)
    {
    	mFCDbHelper.openDataBase();
    	mFCDbHelper.UpdateKnownCardValues(knowcardCount, ""+(mFlashCardId));
    	mFCDbHelper.close();
    }
    
    private void manageUpdateBookMarkInDB(String bokmarkcardCount)
    {
    	mFCDbHelper.openDataBase();
    	mFCDbHelper.UpdateBookMarkCardValues(bokmarkcardCount, ""+(mFlashCardId));
    	mFCDbHelper.close(); 
    }

    @Override
	public void onPause()
	{
    	super.onPause();
		DeckView.getScreen().isdeleteAllProficiency = false;
		CardDetails.screen.mWebView.loadUrl(CardDetails.screen.stopAudio);		
	
	}

    @Override
	public void onStop()
	{
    	super.onStop();
    	CardDetails.screen.mWebView.destroy();
		DeckView.getScreen().isdeleteAllProficiency = false;
	} 
    
    private void setScreenData()
    {
    	Button tv = (Button) findViewById(R.id.carddetail_back_but);
        tv.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				DeckView.getScreen().isdeleteAllProficiency = false;
		    	mFCDbHelper.openDataBase();
        		List<String> listpk_CardId = mFCDbHelper.getBookMarkedCardStatus();
        	    bookMarkProfcardStatus = mFCDbHelper.getKnownBookMarkedCardStatus();
            	mFCDbHelper.close(); 
        	    profSelBookMark = listpk_CardId.size();
        		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, profSelBookMark);
        		DeckView.getScreen().storeDataInPreferences(profSelBookMark, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
        		DeckView.getScreen().storeDataInPreferences(bookMarkProfcardStatus, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");
				finish();
			}
		});
        Button tvfront = (Button) findViewById(R.id.carddetail_front_back);
        tvfront.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				
				DeckView.getScreen().isdeleteAllProficiency = false;
		    	mFCDbHelper.openDataBase();
        		List<String> listpk_CardId = mFCDbHelper.getBookMarkedCardStatus();
        	    bookMarkProfcardStatus = mFCDbHelper.getKnownBookMarkedCardStatus();
            	mFCDbHelper.close(); 
        	    profSelBookMark = listpk_CardId.size();
        		DeckView.getScreen().setStrBookMark(bookMarkProfcardStatus, profSelBookMark);
        		DeckView.getScreen().storeDataInPreferences(profSelBookMark, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
        		DeckView.getScreen().storeDataInPreferences(bookMarkProfcardStatus, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");
           	   finish();
        		
			}
		});

//        videoButBack = (ImageView) findViewById(R.id.carddetail_back_video_title);
//        videoButBack.setVisibility(View.INVISIBLE);
//        videoButBack.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//			    if(mStartCardNo == 0)
//			    {
////					startActivity(new Intent(CardDetails.this, Video.class));
//			    }
//			}
//		});
//        
//        videoBut = (ImageView) findViewById(R.id.video_title);
//        videoBut.setVisibility(View.INVISIBLE);
//	    videoBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//			    if(mStartCardNo == 0)
//			    {
////					startActivity(new Intent(CardDetails.this, Video.class));
//			    }
//			}
//		});
	    cardDetail_Front_HeaderCountText = (TextView) findViewById(R.id.carddetail_front_totalcardcount);
	    cardDetail_Back__HeaderCountText = (TextView) findViewById(R.id.carddetail_back_totalcardcount);
	    bookMarkcardDetail_Front_ = (ImageView) findViewById(R.id.carddetail_frontbookmark_icon);
	    iKnowcardDetail_Front_ = (ImageView) findViewById(R.id.carddetail_frontiknow_icon);
	    bookMarkcardDetail_Back_ = (ImageView) findViewById(R.id.carddetail_back_bookmark_icon);
	    iKnowcardDetail_Back_ = (ImageView) findViewById(R.id.carddetail_back_iknow_icon);
	    iKnowcardDetail_Front_Button = (ImageView) findViewById(R.id.carddetail_front_know_footer);
	    bookMarkcardDetail_Front_Button = (ImageView) findViewById(R.id.carddetail_front_bookmark_footer);
	    iKnowcardDetail_Back_Button = (ImageView) findViewById(R.id.carddetail_back_know_footer);
	    bookMarkcardDetail_Back_Button = (ImageView) findViewById(R.id.carddetail_back_bookmark_footer);
	    cardDetail_Front_Next = (ImageView) findViewById(R.id.carddetail_front_next_footer);
	    cardDetail_Front_Prev = (ImageView) findViewById(R.id.carddetail_front_prev_footer);
	    cardDetail_Back_Next = (ImageView) findViewById(R.id.carddetail_back_next_footer);
	    cardDetail_Back_Prev = (ImageView) findViewById(R.id.carddetail_back_prev_footer);

	   
		  cardDetail_Back_Prev.setOnClickListener(new OnClickListener() 
		  {
			  @Override
			  public void onClick(View v) 
			  {
				if(mStartCardNoBookMark > 0)
					setPrevScreenParameters();
				    isUnmarkClick=false;
			  }
		  });
	    	    
	   
	    	cardDetail_Front_Prev.setOnClickListener(new OnClickListener() 
	    	{
	    		@Override
	    		public void onClick(View v) 
	    		{
	    			 if(mStartCardNoBookMark>0)
	    				 setPrevScreenParameters();
	    			 isUnmarkClick=false;
	    			    
	    		 }
	    	 });
	    
        cardDetail_Back_Next.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				if(isBookmark)
				{
					
				}
				if(mStartCardNoBookMark + 1 < mLastCardNo)
					isUnmarkClick=false;
					setNextScreenParameters();
			}
		});
        cardDetail_Front_Next.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				
				if(mStartCardNoBookMark + 1 < mLastCardNo)
					setNextScreenParameters();
				   isUnmarkClick=false;
			}
		});
	    flipcardDetail_Back_But = (ImageView) findViewById(R.id.carddetail_back_rotatetrde_footer);
	    flipcardDetail_Front_But = (ImageView) findViewById(R.id.carddetail_front_footer_flip);
	    flipcardDetail_Front_But.setOnClickListener(mOnClickListener);
	    flipcardDetail_Back_But.setOnClickListener(mOnClickListener);
	    
//	    soundSpeakercardDetail_Front_ = (TextView) findViewById(R.id.carddetail_front_sound);
//	    soundSpeakercardDetail_Back_ = (TextView) findViewById(R.id.carddetail_back_sound);
//	    soundSpeakercardDetail_Front_Icon = (ImageView) findViewById(R.id.carddetail_frontsound_icon);
//	    soundSpeakercardDetail_Back_Icon = (ImageView) findViewById(R.id.carddetail_back_sound_icon);
//	    
//	    soundSpeakercardDetail_Front_.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				try
//				{
//					preapareMediaPlayerData();
//					if(mp != null)
//					{
//						mp.start();
//						mp.setVolume(300, 300);
//					}
//				}catch(Exception e){}
//			}
//		});
//	    soundSpeakercardDetail_Back_.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				try
//				{
//					preapareMediaPlayerData();
//					if(mp != null)
//					{
//						mp.start();
//						mp.setVolume(300, 300);
//					}
//				}catch(Exception e){}
//			}
//		});

        mContainer=(ViewGroup)findViewById(R.id.relative_carddetail_main);
        cardDetail_Front_View = (RelativeLayout)findViewById(R.id.relativecarddetail_front);
        cardDetail_Back_View = (RelativeLayout)findViewById(R.id.carddetail_back_relative);
    }
    
	View.OnClickListener mOnClickListener = new View.OnClickListener() 
	{
		public void onClick(View v) 
		{
		    sliderFront.clearAnimation();
		    sliderFront.setVisibility(View.GONE);
		    sliderBack.clearAnimation();
		    sliderBack.setVisibility(View.GONE);
		    
			if(v.getId() == (R.id.carddetail_front_footer_flip))
			{
				resetMediaPlayer();
				currentView = "Trade";
				applyRotation(-1, 0, 90, 0);
				preapareMediaPlayerData();

		    	String htmlName = "";
		    	mFCDbHelper.openDataBase();
				String soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
				if(soundName != null && !soundName.equalsIgnoreCase(""))
				{
					showSoundIcon();
				}
				else
					hideSoundIcon();
	    		htmlName = mFCDbHelper.getHTMLName(""+mFlashCardId, false);
		    	mFCDbHelper.close();
		    	mWebViewBack.removeAllViews();
		    	mWebViewBack.loadUrl("file:///android_asset/" + htmlName);
		    	
//		    	mWebViewBack.reload();
//		    	mFCDbHelper.close();
			}
			else if (v.getId() == (R.id.carddetail_back_rotatetrde_footer))
			{
				resetMediaPlayer();
				currentView = "Generic";
		    	mFCDbHelper.openDataBase();
				String soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
				if(soundName != null && !soundName.equalsIgnoreCase(""))
				{
					showSoundIcon();
				}
				else
					hideSoundIcon();
		    	mFCDbHelper.close();
				applyRotation(1, 0, 90, 0);
			}
			
			if(mSearchString != null && !mSearchString.equals(""))
			{
				runBack("javascript:var highlightWords = function MyApp_HighlightAllOccurencesOfStringForElement(element,keyword) { var MyApp_SearchResultCount = 0; if (element) {if (element.nodeType == 3) {        while (true) {var value = element.nodeValue;  var idx = value.toLowerCase().indexOf(keyword);if (idx < 0) break;var span = document.createElement(\"span\");var text = document.createTextNode(value.substr(idx,keyword.length)); span.appendChild(text);span.setAttribute(\"class\",\"MyAppHighlight\");span.style.backgroundColor=\"yellow\";span.style.color=\"black\";text = document.createTextNode(value.substr(idx+keyword.length));element.deleteData(idx, value.length - idx);var next = element.nextSibling;element.parentNode.insertBefore(span, next);element.parentNode.insertBefore(text, next);element = text;MyApp_SearchResultCount++;	}} else if (element.nodeType == 1) { if (element.style.display != \"none\" && element.nodeName.toLowerCase() != 'select') {for (var i=element.childNodes.length-1; i>=0; i--) {MyApp_HighlightAllOccurencesOfStringForElement(element.childNodes[i],keyword);}}}}};highlightWords(document.body, \""+mSearchString+"\".toLowerCase());");
	        	}
			
		}
	};
	public void runBack(final String scriptSrc) { 
		mWebViewBack.post(new Runnable() {
            @Override
            public void run() { 
            	mWebViewBack.loadUrl("javascript:" + scriptSrc); 
            }
        }); 
    }
	
	public void runFront(final String scriptSrc) { 
		mWebView.post(new Runnable() {
            @Override
            public void run() { 
            	mWebView.loadUrl("javascript:" + scriptSrc); 
            }
        }); 
    }
//	View.OnClickListener mOnClickListener = new View.OnClickListener() 
//	{
//		public void onClick(View v) 
//		{
//		    sliderFront.clearAnimation();
//		    sliderFront.setVisibility(View.GONE);
//		    sliderBack.clearAnimation();
//		    sliderBack.setVisibility(View.GONE);
//			if(v.getId() == (R.id.carddetail_front_footer_flip))
//			{
//				rotateToBack();
//			}
//			else if (v.getId() == (R.id.carddetail_back_rotatetrde_footer))// TODO  BACK
//			{
//				rotateTOFront();
//			}
//		}
//	};
	
	private void rotateToBack()
	{
		resetMediaPlayer();
//		DeckView.getScreen().closeSliderView();
		currentView = "Trade";
//		applyRotation(-1, 180, 90, 0);
		applyRotation(-1, 0, 90, 0);
//    	showSoundVisible("2");
//		preapareMediaPlayerData();

    	String htmlName = "";
    	mFCDbHelper.openDataBase();
		String soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
		if(soundName != null && !soundName.equalsIgnoreCase(""))
		{
			showSoundIcon();
		}
		else
			hideSoundIcon();
//    	else
    	{
//    		mCurrentCardID = ""+ mFlashCardId;
    		htmlName = mFCDbHelper.getHTMLName(""+mFlashCardId, false);
    	}
    	mFCDbHelper.close();
    	mWebViewBack.removeAllViews();
    	mWebViewBack.loadUrl("file:///android_asset/" + htmlName);
    	
//    	mWebViewBack.reload();
//    	mFCDbHelper.close();
	}
	
	private void rotateTOFront()
	{
		resetMediaPlayer();
		currentView = "Generic";
    	mFCDbHelper.openDataBase();
		String soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
		if(soundName != null && !soundName.equalsIgnoreCase(""))
		{
			showSoundIcon();
		}
		else
			hideSoundIcon();
    	mFCDbHelper.close();
//    	showSoundVisible("1");
//		applyRotation(0, 0, 90, 0);
		applyRotation(1, 0, 90, 0);
	}
	
	public void resetMediaPlayer()
	{
		if(mp != null)
		{
			mp.stop();
			mp.reset();
		}
	}
	
	String FrontOrBackSound;
	
	private void preapareMediaPlayerData()
	{
		if(mp != null)
		{
			mp.reset();
		}
		mp = null;
		mp = new MediaPlayer();
		try {
        	mFCDbHelper.openDataBase();
        	String soundName = "";
//        	if(mFromClass != null && mFromClass.equals("SEARCH"))
//    		{
//    	    	if(listSearchCardid != null && listSearchCardid.size() > 0)
//    			{
//    	    		String str = listSearchCardid.get(mStartCardNo);
//    	    		mFlashCardId = ""+ str;
//    	    		int i = Integer.parseInt(str);
////    	    		if(i % 2 != 0)
////    	    			i++;
////    	    		i = i * 2 ;
//    	    		Log.e("listback.get(mStartCardNo)", "" + i);
//    	    		soundName = mFCDbHelper.getSoundFileNme(""+i);
//    			}
//    		}
//        	else
        	{
//    	    	String flashCardId = mFCDbHelper.getFlashCardId(mStartCardNo);
        		soundName = mFCDbHelper.getSoundFileNme(""+(mFlashCardId));
    			if(soundName != null && !soundName.equalsIgnoreCase(""))
    			{
    				showSoundIcon();
    			}
    			else
    				hideSoundIcon();
        	}
   		  	System.out.println(soundName + " : " + "FrontOrBackSound");
        	mFCDbHelper.close(); 
			//http://stackoverflow.com/questions/3289038/play-audio-file-from-the-assets-directory    "abacavir.mp3"
			AssetFileDescriptor afd = getAssets().openFd(soundName);
			mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
		    mp.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void applyRotation(int position, float start, float end, int i) {
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 320.0f, true);
//        if(i == 1)
//            rotation.setDuration(2);
//        else
        	rotation.setDuration(200);
        
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        
        rotation.setAnimationListener(new DisplayNextView(position, i));
        mContainer.startAnimation(rotation);
    }

	private final class DisplayNextView implements Animation.AnimationListener 
	{
        private final int mPosition;
        private final int side;

        private DisplayNextView(int position, int i) {
            mPosition = position;
            side = i;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition/*, side*/));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
	
	private final class SwapViews implements Runnable 
	{
        private final int mPosition;
//        private final int side;

        public SwapViews(int position/*, int i*/) 
        {
            mPosition = position;
//            side = i;
        }

        public void run() 
        {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            if (mPosition == -1) 
            {
            	cardDetail_Front_View.setVisibility(View.GONE);
            	cardDetail_Back_View.setVisibility(View.VISIBLE);
            	cardDetail_Back_View.requestFocus();
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 320.0f, false);
            } 
            else 
            {
            	cardDetail_Back_View.setVisibility(View.GONE);
                cardDetail_Front_View.setVisibility(View.VISIBLE);
                cardDetail_Front_View.requestFocus();
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 320.0f, false);
            }
//            if(side == 1)
//            	rotation.setDuration(2);
//            else
            	rotation.setDuration(400);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            mContainer.startAnimation(rotation);
        }
    }
	
//	@Override
//	public boolean onTouch(View v, MotionEvent event) 
//	{
//		switch (event.getAction() & MotionEvent.ACTION_MASK) 
//	    {
//	    	case MotionEvent.ACTION_DOWN: //1
////	    		startPointx = event.getX();
////	    		startPointy = event.getY();
////	    		Log.d("TAG ACTION_DOWN ", startPointx + "   mode=NONE   " + startPointy);
//    		break;
//	    		
//	      	case MotionEvent.ACTION_POINTER_DOWN:
//	        break;
//	    		
//	    	case MotionEvent.ACTION_UP:  //3
////	    		if(startPointx - endPointx > 10 && startPointy - endPointy < 10 && (mStartCardNoBookMark +1) < mLastCardNo)
////		    		setNextScreenParameters();
////		    	if(startPointx - endPointx < -10 && startPointy - endPointy < 10 && mStartCardNoBookMark > 0)
////	    			setPrevScreenParameters();
//    		break;
//	    	case MotionEvent.ACTION_POINTER_UP: 
//    		break;
//	    	case MotionEvent.ACTION_MOVE: //2
////	    		endPointx = event.getX();
////	    		endPointy = event.getY();
////	    		mWebViewBack.scrollBy(x, y)
//    		break;
//      	}
//	    return true; 
//	}
    
    final class DemoJavaScriptInterface 
    { 
    	DemoJavaScriptInterface()
    	{ 
    	} 
	}

	final class CustomWebChromeClient extends WebChromeClient { 
		@Override 
		public boolean onJsAlert(WebView view, String url, String message, JsResult result)
		{ 
			Log.d("LOG_TAG", message); 
			result.confirm(); 
			return true; 
		} 
	}
	public class JavaScriptInterface {
		Context mContext;
		JavaScriptInterface(Context c) {
			mContext = c;
		}
		public void onSearchSelectionClick() {
			jsHandler.post(new Runnable() {
				public void run() 
				{
					if(mSearchString != null && !mSearchString.equals(""))
					{
						mWebView.findAll(mSearchString);
						  try
	                        {
							  
	                      //  Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
	                      //  m.invoke(mWebView, true);
	                        mWebView.loadUrl("javascript:highlightSearchTerms('"+mSearchString+"');");
							mWebViewBack.loadUrl("javascript:highlightSearchTerms('"+mSearchString+"');");
	                        }
	                    catch (Throwable ignored){}
						// TODO  BACK
					}
					// Java telling Javascript to do things  highlightSearchTerms(ca) popup()
//					mWebView.loadUrl("javascript:highlightSearchTerms('"+mSearchString+"');");
//					mWebViewBack.loadUrl("javascript:highlightSearchTerms('"+mSearchString+"');");
				}
			});
		}
	}
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("_TAG", message);
            return super.onJsAlert(view, url, message, result);
        }
        public void onProgressChanged(WebView view, int newProgress){
	    	 CardDetails.screen.mWebView.loadUrl(CardDetails.screen.highlightingJS);
	         System.out.println(  newProgress);

             return;
  
}
    }
    final Handler jsHandler = new Handler();
	//String mSearchString = "";
    String mSearchString;
	public void changeOptionIcon() 
	{
		cardTopNotesFront.setBackgroundResource(R.drawable.exter_window_ic_yel19);
		cardTopNotesBack.setBackgroundResource(R.drawable.exter_window_ic_yel19);
	}
	
		
	 @Override
	    protected void onResume()
	    {
	    	
	       super.onResume();
	      
	       
	       if(mStartCardNoBookMark+1>=mLastCardNo)
	    	{
	    	   cardDetail_Front_Next.setImageResource(R.drawable.next_d);
	    		cardDetail_Front_Next.setEnabled(false);
	    		cardDetail_Back_Next.setEnabled(false);
	    		cardDetail_Front_Next.setClickable(false);
	    		cardDetail_Back_Next.setClickable(false);
	    	}
	    	else
	    	{
	    		 cardDetail_Front_Next.setImageResource(R.drawable.next);
	    		cardDetail_Front_Next.setEnabled(true);
	    		cardDetail_Back_Next.setEnabled(true);
	    		cardDetail_Front_Next.setClickable(true);
	    		cardDetail_Back_Next.setClickable(true);
	    	}
	       
	       if(mStartCardNoBookMark<= 0)
	    	{
	    	   cardDetail_Front_Prev.setImageResource(R.drawable.prev_d); 
	        	cardDetail_Front_Prev.setEnabled(false);
	        	cardDetail_Front_Prev.setClickable(false);
	        	cardDetail_Back_Prev.setEnabled(false);
	        	cardDetail_Back_Prev.setClickable(false);
	    	}
	        else
		    {
	        	cardDetail_Front_Prev.setImageResource(R.drawable.prev); 
	        	cardDetail_Front_Prev.setEnabled(true);
	        	cardDetail_Front_Prev.setClickable(true);
	        	cardDetail_Back_Prev.setEnabled(true);
	        	cardDetail_Back_Prev.setClickable(true);
		    }
	   	
	    }
	
	

}
