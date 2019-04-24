package com.spearheadinc.flashcards.prayerapp;
//package com.spearheadinc.flashcards.prayerapp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.TranslateAnimation;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.social.share.facebook.FacebookConnector;
//import com.social.share.facebook.SessionEvents;
//import com.social.share.twitter.Constants;
//import com.social.share.twitter.PrepareRequestTokenActivity;
//import com.social.share.twitter.TwitterUtils;
//
//public class DeckView1 extends Activity {
//	private static DeckView1 screen;
//	TextView clickGastro;
//	TextView clickEndo;
//	TextView clickUro;
//	TextView ClickImmune;
//	TextView clickMusc;
//	TextView clickCentr;
//	TextView clickCardio;
//	TextView ClickRespir;
//	TextView ClickSensor;
//	
//	TextView profvalueAllCard;
//	TextView profvalueBookMark;
//	TextView profvalueGastro;
//	TextView profvalueEndo;
//	TextView profvalueUro;
//	TextView profvalueImmune;
//	TextView profvalueMusc;
//	TextView profvalueCentr;
//	TextView profvalueCardio;
//	TextView profvalueRespir;
//	TextView profvalueSensor;
//	
//	TextView totNumbOfAllCard;
//	TextView totNumbOfBookMarkCard;
//	TextView totNumbOfGastroCard;
//	TextView totNumbOfEndoCard;
//	TextView totNumbOfUroCard;
//	TextView totNumbOfImmuneCard;
//	TextView totNumbOfMuscCard;
//	TextView totNumbOfCentrCard;
//	TextView totNumbOfCardioCard;
//	TextView totNumbOfRespirCard;
//	TextView totNumbOfSensorCard;
//	
//	TextView titleGastro;
//	TextView titleEndo;
//	TextView titleUro;
//	TextView titleImmune;
//	TextView titleMusc;
//	TextView titleCentr;
//	TextView titleCardio;
//	TextView titleRespir;
//	TextView titleSensor;
//	FCDBHelper mFCDbHelper;
//	private String fromClassName;
//	
//    public static DeckView1 getScreen() 
//    {
//    	return screen;
//	}
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//		screen = this;
//		setContentView(R.layout.pharm);
//		
//        Bundle extras = getIntent().getExtras();
//        if(extras != null)
//        {
//        	fromClassName = extras.getString("com.nclex.qa.app.packageName.nclecquizview.class.name");
//        }
//		
//		clickGastro = (TextView) findViewById(R.id.drugs_first_clickable_view);
//		clickEndo = (TextView) findViewById(R.id.drugs_second_clickable_view);
//		clickUro = (TextView) findViewById(R.id.drugs_third_clickable_view);
//		ClickImmune = (TextView) findViewById(R.id.drugs_forth_clickable_view); ///
//		clickMusc = (TextView) findViewById(R.id.drugs_fifth_clickable_view);
//		clickCentr = (TextView) findViewById(R.id.drugs_sixth_clickable_view);
//		clickCardio = (TextView) findViewById(R.id.drugs_seventh_clickable_view);
//		ClickRespir = (TextView) findViewById(R.id.drugs_eighth_clickable_view);
//		ClickSensor = (TextView) findViewById(R.id.drugs_ninth_clickable_view);
//
//        profvalueAllCard = (TextView) findViewById(R.id.proficiency_value);
//    	profvalueBookMark = (TextView) findViewById(R.id.bookmarked_cards_proficiency_value);
//		profvalueGastro = (TextView) findViewById(R.id.drugs_proficiency_value_first);
//		profvalueEndo = (TextView) findViewById(R.id.drugs_proficiency_value_second);
//		profvalueUro = (TextView) findViewById(R.id.drugs_proficiency_value_third);
//		profvalueImmune = (TextView) findViewById(R.id.drugs_proficiency_value_forth);
//		profvalueMusc = (TextView) findViewById(R.id.drugs_proficiency_value_fifth);
//		profvalueCentr = (TextView) findViewById(R.id.drugs_proficiency_value_sixth);
//		profvalueCardio = (TextView) findViewById(R.id.drugs_proficiency_value_seventh);
//		profvalueRespir = (TextView) findViewById(R.id.drugs_proficiency_value_eighth);
//		profvalueSensor = (TextView) findViewById(R.id.drugs_proficiency_value_ninth);
//		
//		totNumbOfAllCard = (TextView) findViewById(R.id.drugs_cards_numb_all_cards);
//		totNumbOfBookMarkCard = (TextView) findViewById(R.id.no_of_bookmarked_cards);
//		totNumbOfGastroCard = (TextView) findViewById(R.id.drugs_cards_numb_first);
//		totNumbOfEndoCard = (TextView) findViewById(R.id.drugs_cards_numb_second);
//		totNumbOfUroCard = (TextView) findViewById(R.id.drugs_cards_numb_third);
//		totNumbOfImmuneCard = (TextView) findViewById(R.id.drugs_cards_numb_forth);
//		totNumbOfMuscCard = (TextView) findViewById(R.id.drugs_cards_numb_fifth);
//		totNumbOfCentrCard = (TextView) findViewById(R.id.drugs_cards_numb_sixth);
//		totNumbOfCardioCard = (TextView) findViewById(R.id.drugs_cards_numb_seventh);
//		totNumbOfRespirCard = (TextView) findViewById(R.id.drugs_cards_numb_eighth);
//		totNumbOfSensorCard = (TextView) findViewById(R.id.drugs_cards_numb_ninth);
//		
//		titleGastro = (TextView) findViewById(R.id.drugs_title_first);
//		titleEndo = (TextView) findViewById(R.id.drugs_title_second);
//		titleUro = (TextView) findViewById(R.id.drugs_title_third);
//		titleImmune = (TextView) findViewById(R.id.drugs_title_forth);
//		titleMusc = (TextView) findViewById(R.id.drugs_title_fifth);
//		titleCentr = (TextView) findViewById(R.id.drugs_title_sixth);
//		titleCardio = (TextView) findViewById(R.id.drugs_title_seventh);
//		titleRespir = (TextView) findViewById(R.id.drugs_title_eighth);
//		titleSensor = (TextView) findViewById(R.id.drugs_title_ninth);
//		
//		RelativeLayout relBgGastro = (RelativeLayout) findViewById(R.id.deck_in_layout_first);
//		RelativeLayout relBgEndo = (RelativeLayout) findViewById(R.id.deck_in_layout_second);
//		RelativeLayout relBgUro = (RelativeLayout) findViewById(R.id.deck_in_layout_third);
//		RelativeLayout relBgImmune = (RelativeLayout) findViewById(R.id.deck_in_layout_forth);
//		RelativeLayout relBgMusc = (RelativeLayout) findViewById(R.id.deck_in_layout_fifth);
//		RelativeLayout relBgCentr = (RelativeLayout) findViewById(R.id.deck_in_layout_sixth);
//		RelativeLayout relBgCardio = (RelativeLayout) findViewById(R.id.deck_in_layout_seventh);
//		RelativeLayout relBgRespir = (RelativeLayout) findViewById(R.id.deck_in_layout_eighth);
//		RelativeLayout relBgSensor = (RelativeLayout) findViewById(R.id.deck_in_layout_ninth);
//		
//        
//        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
//    	mFCDbHelper.openDataBase();
//    	List<String> list = mFCDbHelper.getDeksName_Color();
//    	List<String> listDeckName = new ArrayList<String>();
//    	List<String> listDeckColor = new ArrayList<String>();
//        for(int i = 0; i < list.size(); )
//        {
//        	listDeckName.add(list.get(i++));
//        	listDeckColor.add(list.get(i++));
//        }
//
//      	titleGastro.setText(listDeckName.get(0));
//      	titleEndo.setText(listDeckName.get(1));
//      	titleUro.setText(listDeckName.get(2));
//      	titleImmune.setText(listDeckName.get(3));
//      	titleMusc.setText(listDeckName.get(4));
//      	titleCentr.setText(listDeckName.get(5));
//      	titleCardio.setText(listDeckName.get(6));
//      	titleRespir.setText(listDeckName.get(7));
//      	titleSensor.setText(listDeckName.get(8));
//      	
//		relBgGastro.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(0)}));
//		relBgEndo.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(1)}));
//		relBgUro.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(2)}));
//		relBgImmune.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(3)}));
//		relBgMusc.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(4)}));
//		relBgCentr.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(5)}));
//		relBgCardio.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(6)}));
//		relBgRespir.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(7)}));
//		relBgSensor.setBackgroundColor(Color2Hex(new String[]{listDeckColor.get(8)}));
//
//		clickGastro.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = 1;
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickGastro");
//			    startActivity(i);
//			}
//        });
//        
//    	clickEndo.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickEndo");
//			    startActivity(i);
//			}
//        });
//        
//    	clickUro.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickUro");
//			    startActivity(i);
//			}
//        });
//        
//    	ClickImmune.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "ClickImmune");
//			    startActivity(i);
//			}
//        });
//        
//    	clickMusc.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + total3 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickMusc");
//			    startActivity(i);
//			}
//        });
//        
//    	clickCentr.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + total3 + total4 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickCentr");
//			    startActivity(i);
//			}
//        });
//        
//    	clickCardio.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + total3 + total4 + total5 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "clickCardio");
//			    startActivity(i);
//			}
//        });
//        
//    	ClickRespir.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + total3 + total4 + total5 + total6 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "ClickRespir");
//			    startActivity(i);
//			}
//        });
//        
//    	ClickSensor.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				total = total0 + total1 + total2 + total3 + total4 + total5 + total6 + total7 + 1;
//				System.out.println(total);
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "ClickSensor");
//			    startActivity(i);
//			}
//        });
//        
//
//        RelativeLayout allcardRel = (RelativeLayout) findViewById(R.id.all_cards);
//        allcardRel.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				Intent i = new Intent(DeckView1.this, CardDetails1.class);
//			    i.putExtra("com.android.flashcard.screen.cardDetail", "all_cards");
//			    startActivity(i);
//			}
//		});
//
//        RelativeLayout bookmarkcardRel = (RelativeLayout) findViewById(R.id.bookmarked_cards);
//        bookmarkcardRel.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				mFCDbHelper.openDataBase();
//        		List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
//            	mFCDbHelper.close();
//        		int lastCardNo = listpk_FlashCardId.size();
//        		if(lastCardNo<1)
//        		{
//	    	        AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView1.this);
//	    	        alt_bld.setMessage("There are no bookmarked items").setCancelable(false)
//	    		        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//	    		        public void onClick(DialogInterface dialog, int id) {
//	    		        	dialog.cancel();
//	    		        }
//	    	        });
//	    	        AlertDialog alert = alt_bld.create();
//	    	        alert.setTitle("Information");
//	    	        alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
//	    	        alert.show();
//        		}
//        		else
//        		{
//					Intent i = new Intent(DeckView1.this, CardDetails1.class);
//				    i.putExtra("com.android.flashcard.screen.cardDetail", "bookmarked_cards");
//				    startActivity(i);
//        		}
//			}
//		});
//
//        Button searchbut = (Button) findViewById(R.id.search_footer);
//        searchbut.setOnClickListener(new OnClickListener() 
//        {
//        	@Override
//			public void onClick(View v) 
//		
//			{
//				startActivity(new Intent(DeckView1.this, Search.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//
//        Button indxSerchBut = (Button) findViewById(R.id.index_footer);
//        indxSerchBut.setOnClickListener(new OnClickListener() 
//        {
//        	@Override
//			public void onClick(View v) 
//		
//			{
//				startActivity(new Intent(DeckView1.this, IndexSearch.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//
//        Button settingBut = (Button) findViewById(R.id.setting_footer);
//        settingBut.setOnClickListener(new OnClickListener() 
//        {
//        	@Override
//			public void onClick(View v) 
//		
//			{
//				startActivity(new Intent(DeckView1.this, Setting.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//
//        Button helpBut = (Button) findViewById(R.id.help_footer);
//        helpBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//				startActivity(new Intent(DeckView1.this, Help.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//
//        final RelativeLayout takePickPanal = (RelativeLayout) findViewById(R.id.sliderinfo_top_rel);
//        Button infoBut = (Button) findViewById(R.id.info_footer);
//        infoBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{
//			    takePickPanal.setVisibility(View.VISIBLE);
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 100, 0);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
//				startActivity(new Intent(DeckView1.this, Info.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//		});
//        
//        ImageButton infoSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_info);
//        infoSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//				startActivity(new Intent(DeckView1.this, Info.class));
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//
//        ImageButton voiceSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_voicenotes);
//        voiceSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
////				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//				Intent i = new Intent(DeckView1.this, VoiceNoteDetails.class);			
//				i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
//				startActivity(i);
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//        
//        ImageButton commentSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_comments);
//        commentSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
//				takePickPanal.setVisibility(View.GONE);
//				Intent i = new Intent(DeckView1.this, CommentDetails.class);				
//				i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
//				startActivity(i);
//				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
//			}
//        });
//        
//        ImageButton facebookSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_facebook);
//        facebookSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
//				takePickPanal.setVisibility(View.GONE);
//        		postMessage();
//			}
//        });
//        
//        ImageButton teitterSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_twitter);
//        teitterSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
//				takePickPanal.setVisibility(View.GONE);
//				postTweetMsg();
//			}
//        });
//        
//        ImageButton cancelSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_cancel);
//        cancelSliderBut.setOnClickListener(new OnClickListener() 
//        {
//			@Override
//			public void onClick(View v) 
//			{    
//				takePickPanal.clearAnimation();
//				takePickPanal.setVisibility(View.GONE);
//			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
//			    slide.setDuration(700);
//			    slide.setFillAfter(true);
//			    takePickPanal.startAnimation(slide);
//				takePickPanal.setVisibility(View.GONE);
//			}
//        });
//        
//    	totNumbOfAllCard.setText(389 + " cards");
//        
//        total0 = mFCDbHelper.getDeksTotalNumOfCards("0");
//    	totNumbOfGastroCard.setText(total0 + " cards");
//    	total1 = mFCDbHelper.getDeksTotalNumOfCards("1");
//    	totNumbOfEndoCard.setText(total1 + " cards");
//    	total2 = mFCDbHelper.getDeksTotalNumOfCards("2");
//    	totNumbOfUroCard.setText(total2 + " cards");
//    	total3 = mFCDbHelper.getDeksTotalNumOfCards("3");
//    	totNumbOfImmuneCard.setText(total3 + " cards");
//    	total4 = mFCDbHelper.getDeksTotalNumOfCards("4");
//    	totNumbOfMuscCard.setText(total4 + " cards");
//    	total5 = mFCDbHelper.getDeksTotalNumOfCards("5");
//    	totNumbOfCentrCard.setText(total5 + " cards");
//    	total6 = mFCDbHelper.getDeksTotalNumOfCards("6");
//    	totNumbOfCardioCard.setText(total6 + " cards");
//    	total7 = mFCDbHelper.getDeksTotalNumOfCards("7");
//    	totNumbOfRespirCard.setText(total7 + " cards");
//    	total8 = mFCDbHelper.getDeksTotalNumOfCards("8");
//    	totNumbOfSensorCard.setText(total8 + " cards");
//
//        SharedPreferences myPrefs = null;
//
//
//        myPrefs =  DeckView1.this.getSharedPreferences("StrAllCardPrefs", MODE_WORLD_READABLE);
//        String strAllCard = myPrefs.getString("STRALLCARD", "0.00%");
//        profvalueAllCard.setText(strAllCard);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("StrBookMarkPrefs", MODE_WORLD_READABLE);
//        String strBookMark = myPrefs.getString("STRBOOKMARK", "0.00%");
//    	profvalueBookMark.setText(strBookMark);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("GastroPrefsValue", MODE_WORLD_READABLE);
//        String stGastro = myPrefs.getString("STRCLICKGASTRO", "0.00%");
//        profvalueGastro.setText(stGastro);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("EndoPrefsValue", MODE_WORLD_READABLE);
//        String strEndo = myPrefs.getString("STRCLICKENDO", "0.00%");
//        profvalueEndo.setText(strEndo);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("UroPrefsValue", MODE_WORLD_READABLE);
//        String strUro = myPrefs.getString("STRCLICKURO", "0.00%");
//        profvalueUro.setText(strUro);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("ImmunePrefsValue", MODE_WORLD_READABLE);
//        String stImmune = myPrefs.getString("STRCLICKIMMUNE", "0.00%");
//        profvalueImmune.setText(stImmune);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("MuscPrefsValue", MODE_WORLD_READABLE);
//        String strMusc = myPrefs.getString("STRCLICKMUSC", "0.00%");
//        profvalueMusc.setText(strMusc);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("CentrPrefsValue", MODE_WORLD_READABLE);
//        String strCentr = myPrefs.getString("STRCLICKCENTR", "0.00%");
//        profvalueCentr.setText(strCentr);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("CardioPrefsValue", MODE_WORLD_READABLE);
//        String strCardio = myPrefs.getString("STRCLICKCARDIO", "0.00%");
//        profvalueCardio.setText(strCardio);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("RespirPrefsValue", MODE_WORLD_READABLE);
//        String strRespir = myPrefs.getString("STRCLICKRESPIR", "0.00%");
//        profvalueRespir.setText(strRespir);
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("SensorPrefsValue", MODE_WORLD_READABLE);
//        String strSensor = myPrefs.getString("STRCLICKSENSOR", "0.00%");
//        profvalueSensor.setText(strSensor);
//
//        
//        myPrefs =  DeckView1.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_WORLD_READABLE);
//        int bookMarkProfcardStatus = myPrefs.getInt("BOOKMARKEDCARDS", 0);
//        totNumbOfBookMarkCard.setText(bookMarkProfcardStatus + " cards");
//        
//    	mFCDbHelper.close();
//    	
//        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        this.facebookConnector = new FacebookConnector(Constants.FACEBOOK_APPID, this, getApplicationContext(), new String[] {Constants.FACEBOOK_PERMISSION});
//
//		if(fromClassName != null && fromClassName.equals("PREPAREREQUESTTOKENACTIVITY"))
//		{
//			startSendingMessage();
//		}
//    } 
//    
//    int total0;
//    int total1;
//    int total2;
//    int total3;
//    int total4;
//    int total5;
//    int total6;
//    int total7;
//    int total8;
//    static int total;
//    
//    public int Color2Hex( String[] args ) {
//    	int cc = 0;
//    	if (args.length != 3) {
//    		String col = args[0];
//    		String r = col.substring(0, col.indexOf(","));
//    		String g = col.substring(col.indexOf(",") + 1, col.lastIndexOf(","));
//    		String b = col.substring(col.lastIndexOf(",") + 1, col.length());
//    		int red = Integer.parseInt(r);
//    		int green = Integer.parseInt(g);
//    		int blue = Integer.parseInt(b);
//    		cc = Color.argb(255, red, green, blue);
//    		
//    	}
//    	else {
//    		int red = Integer.parseInt(args[0]);
//    		int green = Integer.parseInt(args[1]);
//    		int blue = Integer.parseInt(args[2]);
////    		Color c = new Color();
//    		cc = Color.argb(255, red, green, blue);//rgb(red, green, blue);
//        }
//		return cc;
//    }
//	
//	private void populateProficiencyValue(double dbl, TextView tv, String sharedPrefName, String sharedPrefParam)
//	{
////		int total = CardDetails.getScreen().getmLastCardNo();
////		dbl = (double)dbl/total;
//		int ix = (int)(dbl * 10000.0);
//		double dbl2 = ((double)ix)/100.0;
//		String value = dbl2 + "%";
//		tv.setText(value);
//		SharedPreferences myPrefs = DeckView1.this.getSharedPreferences(sharedPrefName, MODE_WORLD_READABLE);
//	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
//	    prefsEditor.putString(sharedPrefParam, value);		            
//	    prefsEditor.commit();
//	}
//	
//	public void deleteAllBookmarks()
//	{
//    	profvalueBookMark.setText("0.00%");
//    	totNumbOfBookMarkCard.setText(0 + " cards");
//		SharedPreferences myPrefs = DeckView1.this.getSharedPreferences("StrBookMarkPrefs", MODE_WORLD_READABLE);
//	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
//	    prefsEditor.putString("STRBOOKMARK", "0.00%");		            
//	    prefsEditor.commit();
//	    myPrefs = DeckView1.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_WORLD_READABLE);
//	    prefsEditor = myPrefs.edit();
//	    prefsEditor.putInt("BOOKMARKEDCARDS", 0);		            
//	    prefsEditor.commit();
//	}
//	
//	public void setStrAllCard(int strAllCard) {
//		Log.e("setStrAllCard", strAllCard + " : " + 389);
//		double dbl = (double)strAllCard/389;
//    	populateProficiencyValue(dbl, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
//	}
//
//	public void setStrBookMark(int strBookMark, int total)
//	{
////    	mFCDbHelper.openDataBase();
////		int totals = mFCDbHelper.getKnownBookMarkedCardStatus();
////    	mFCDbHelper.close();
//		Log.e("setStrBookMark", strBookMark + " : " + total);
//		double dbl = (double)strBookMark/total;
//    	populateProficiencyValue(dbl, profvalueBookMark, "StrBookMarkPrefs", "STRBOOKMARK");
//		SharedPreferences myPrefs = DeckView1.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_WORLD_READABLE);
//	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
//	    prefsEditor.putInt("BOOKMARKEDCARDS", total);		            
//	    prefsEditor.commit();
//	    totNumbOfBookMarkCard.setText(total + " cards");
//	}
//
//	public void setProfSelGastro(int strQZ, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelGastro", strQZ + " : " + totals);
//		double dbl = (double)strQZ/totals;
//    	populateProficiencyValue(dbl, profvalueGastro, "GastroPrefsValue", "STRCLICKGASTRO");
//	}
//
//	public void setProfSelEndo(int strDG, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelEndo", strDG + " : " + totals);
//		double dbl = (double)strDG/totals;
//    	populateProficiencyValue(dbl, profvalueEndo, "EndoPrefsValue", "STRCLICKENDO");
//	}
//	
//	public void setProfSelUro(int strQZ, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelUro", strQZ + " : " + totals);
//		double dbl = (double)strQZ/totals;
//    	populateProficiencyValue(dbl, profvalueUro, "UroPrefsValue", "STRCLICKURO");
//	}
//
//	public void setProfSelImmune(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelImmune", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueImmune, "ImmunePrefsValue", "STRCLICKIMMUNE");
//	}
//
//	public void setProfSelMusc(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelMusc", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueMusc, "MuscPrefsValue", "STRCLICKMUSC");
//	}
//
//	public void setProfSelCentr(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelCentr", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueCentr, "CentrPrefsValue", "STRCLICKCENTR");
//	}
//
//	public void setProfSelCardio(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelCardio", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueCardio, "CardioPrefsValue", "STRCLICKCARDIO");
//	}
//
//	public void setProfSelRespir(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelRespir", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueRespir, "RespirPrefsValue", "STRCLICKRESPIR");
//	}
//
//	public void setProfSelSensor(int strHP, int totals) {
////		int totals = CardDetails.getScreen().getmLastCardNo();
//		Log.e("setProfSelSensor", strHP + " : " + totals);
//		double dbl = (double)strHP/totals;
//    	populateProficiencyValue(dbl, profvalueSensor, "SensorPrefsValue", "STRCLICKSENSOR");
//	}
//	
//	public void deleteAllProficiency()
//	{
//    	profvalueBookMark.setText("0.00%");
//    	populateProficiencyValue(0.00, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
//    	populateProficiencyValue(0.00, profvalueGastro, "GastroPrefsValue", "STRCLICKGASTRO");
//    	populateProficiencyValue(0.00, profvalueEndo, "EndoPrefsValue", "STRCLICKENDO");
//    	populateProficiencyValue(0.00, profvalueUro, "UroPrefsValue", "STRCLICKURO");
//    	populateProficiencyValue(0.00, profvalueImmune, "ImmunePrefsValue", "STRCLICKIMMUNE");
//    	populateProficiencyValue(0.00, profvalueMusc, "MuscPrefsValue", "STRCLICKMUSC");
//    	populateProficiencyValue(0.00, profvalueCentr, "CentrPrefsValue", "STRCLICKCENTR");
//    	populateProficiencyValue(0.00, profvalueCardio, "CardioPrefsValue", "STRCLICKCARDIO");
//    	populateProficiencyValue(0.00, profvalueRespir, "RespirPrefsValue", "STRCLICKRESPIR");
//    	populateProficiencyValue(0.00, profvalueSensor, "SensorPrefsValue", "STRCLICKSENSOR");
//    	isdeleteAllProficiency = true;
//    	decrementProficiency();
//	}
//	
//	public boolean isdeleteAllProficiency = false;
//	
//
//	
//	private void decrementProficiency()
//	{
//		storeDataInPreferences(0, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
//		storeDataInPreferences(0, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
//		storeDataInPreferences(0, "GastroPrefsGenericcd", "STRCLICKGASTROcd");
//		storeDataInPreferences(0, "EndoPrefsGenericcd", "STRCLICKENDOcd");
//		storeDataInPreferences(0, "UroPrefsGenericcd", "STRCLICKUROcd");
//		storeDataInPreferences(0, "ImmunePrefsGenericcd", "STRCLICKIMMUNEcd");
//		storeDataInPreferences(0, "MuscPrefsGenericcd", "STRCLICKMUSCcd");
//		storeDataInPreferences(0, "CentrPrefsGenericcd", "STRCLICKCENTRcd");
//		storeDataInPreferences(0, "CardioPrefsGenericcd", "STRCLICKCARDIOcd");
//		storeDataInPreferences(0, "RespirPrefsGenericcd", "STRCLICKRESPIRcd");
//		storeDataInPreferences(0, "SensorPrefsGenericcd", "STRCLICKSENSORcd");
//		storeDataInPreferences(0, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
//		storeDataInPreferences(0, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");
//	}
//	
//    protected void storeDataInPreferences(int value, String sharedPrefName, String sharedPrefParam) 
//	{
//		SharedPreferences myPrefs = DeckView1.this.getSharedPreferences(sharedPrefName, MODE_WORLD_READABLE);
//	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
//	    prefsEditor.putInt(sharedPrefParam, value);
//	    prefsEditor.commit();
//	}
//    
////    private void clearVoiceNotesInDB()
////    {
////    	mFCDbHelper.openDataBase();
////    	mFCDbHelper.clearVoiceNotesInDB();
////    	mFCDbHelper.close();
////    }
////    
////    private void clearCommentsInDB()
////    {
////    	mFCDbHelper.openDataBase();
////    	mFCDbHelper.clearCommentsInDB();
////    	mFCDbHelper.close(); 
////    }
//    
//    public void resetApplication()
//    {
//    	decrementProficiency();
//    	deleteAllBookmarks();
//    }
//    
//
//    
//    //FACEBOOK
//    
//	private FacebookConnector facebookConnector;
//	private final Handler mFacebookHandler = new Handler();
//	private String quizScore = "50%";
//	
//    final Runnable mUpdateFacebookNotification = new Runnable() {
//        public void run() {
//        	mProgressDialog.dismiss();
//        	Toast.makeText(getBaseContext(), "Facebook updated !", Toast.LENGTH_LONG).show();
//        }
//    };
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		this.facebookConnector.getFacebook().authorizeCallback(requestCode, resultCode, data);
//	}
//	
//
//	private String getFacebookMsg() {
//		return Constants.MSGSUFF + quizScore + Constants.MSGPREF;//" at " + new Date().toLocaleString();
//	}	
//	
//	String mPostText = "";
//	
//	public void setmPostText(String mPostText) {
//		this.mPostText = mPostText;
//		postMessageInThreadFromDialog();
//	}
//
//	public void postMessage(/*String postText*/) {
//		if (facebookConnector.getFacebook().isSessionValid()) {
//			postMessageInThread();
//		} else {
//			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {
//				
//				@Override
//				public void onAuthSucceed() {
//					postMessageInThread();
//				}
//				
//				@Override
//				public void onAuthFail(String error) {
//					
//				}
//			};
//			SessionEvents.addAuthListener(listener);
//			facebookConnector.login();
//		}
//	}
//
//	private void postMessageInThread() {
//		FacebookDialog fb = new FacebookDialog(DeckView1.this, getFacebookMsg());
//        fb.show(); 
//	}
//
//	public void postMessageInThreadFromDialog() {
//        mProgressDialog = ProgressDialog.show(DeckView1.this, "", "...");
//		mProgressDialog.setContentView(R.layout.customprogress);
//		Thread t = new Thread() {
//			public void run() {
//		    	
//		    	try {
//		    		facebookConnector.postMessageOnWall(mPostText/*getFacebookMsg()*/);
//					mFacebookHandler.post(mUpdateFacebookNotification);
//				} catch (Exception ex) {
//					Log.e(Constants.TAG, "Error sending msg",ex);
//				}
//		    }
//		};
//		t.start();
//	}
//	
//	 //FACEBOOK
//	
//	 //TWITTER
//	private String getTweetMsg() {
//		return Constants.MSGSUFF + quizScore + Constants.MSGPREFTWT;
////		return "Tweeting from Android App at " + new Date().toLocaleString();
//	}
//
//	private SharedPreferences prefs;
//	private final Handler mTwitterHandler = new Handler();
//	
//    final Runnable mUpdateTwitterNotification = new Runnable() {
//        public void run() {
//        	mProgressDialog.dismiss();
//        	Toast.makeText(getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG).show();
//        }
//    };	
//	
//    final Runnable mUpdateTwitterNotificationwrongData = new Runnable() {
//        public void run() {
//        	mProgressDialog.dismiss();
//        	Toast.makeText(getBaseContext(), "Error Duplicate Data !", Toast.LENGTH_LONG).show();
//        }
//    };	
//    String mTweetMessage;
//	public void postTweetMsgfromTwitterDialog(String tweetMessage) 
//	{
//		mTweetMessage = tweetMessage;
//        mProgressDialog = ProgressDialog.show(DeckView1.this, "", "...");
//		mProgressDialog.setContentView(R.layout.customprogress);
//		sendTweet();
//	}
//	public void postTweetMsg(/*String tweetMessage*/) 
//	{
//    	if (TwitterUtils.isAuthenticated(prefs)) {
//    		startSendingMessage();
//    	} else {
//			Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
//			i.putExtra("tweet_msg",getTweetMsg());
//			startActivity(i);
//			finish();
//    	}
//	}
//	private ProgressDialog mProgressDialog;
//	
//	private void startSendingMessage() 
//    {
//        TwitterDialog twit = new TwitterDialog(DeckView1.this, prefs, getTweetMsg(),"RESULTVIEW");
//        twit.show();
//    }
//    
//	public void sendTweet() {
//		Thread t = new Thread() {
//	        public void run() {
//	        	boolean b = false;
//	        	try {
//	        		TwitterUtils.sendTweet(prefs, mTweetMessage);
//	        		b=true;
//	        		
//				} catch (Exception ex) {
//					b=false;
//					ex.printStackTrace();
//				}
//				if(b)
//					mTwitterHandler.post(mUpdateTwitterNotification);
//				else
//					mTwitterHandler.post(mUpdateTwitterNotificationwrongData);
//	        }
//	    };
//	    t.start();
//	}
//	
//	//TWITTER
//    
//    
//    
//    
//    
//    
//    
//}
//
////int totalDeckNum = mFCDbHelper.getTotalNumOfDeks();
////  Log.e("nfo " , totalDeckNum+"");
////for(int i = 0; i < totalDeckNum; i++)
////{
//////	  	int totalNumofCards = mFCDbHelper.getDeksTotalNumOfCards(i+"");
//////	  	Log.e("total Numof Cards in deck" + (i+1) + "  " , totalNumofCards+"");
////
//////    List<String> list = mFCDbHelper.getSoundFileNme();
////}
