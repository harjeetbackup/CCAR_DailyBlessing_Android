package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class DeckView extends Activity {
	private static DeckView screen;
//	TextView clickGastro;
	
	TextView profvalueAllCard;
	TextView profvalueBookMark;
//	TextView profvalueGastro;
	TextView totNumbOfAllCard;
	TextView totNumbOfBookMarkCard;
//	TextView totNumbOfGastroCard;
	
//	TextView titleGastro;

	private FCDBHelper mFCDbHelper;
	private String fromClassName;
	private LinearLayout mainDeckViewLinear;
//	private RelativeLayout relCustomshapeQuiz;
    protected int MAX_CARD_NUMBER = 0;
    private CustomizeDialog customizeDialog;
    private String deckcolor="";
    public static DeckView getScreen() 
    {
    	return screen;
	}
	
	public RelativeLayout createSeqUnderLine () 
	{
		LayoutInflater mInflater = LayoutInflater.from(this);
		RelativeLayout detailRow =  (RelativeLayout) mInflater.inflate( R.layout.sequential_textview, null);
		return detailRow;
	}
	
	private LinearLayout CreateRowView(int totalCards, String declTitle, int icon, String deckColor, String deckProf, int totl, int i)// notesText, String strFilePath, LinearLayout seqOrderLinrOpt)
    {
		LayoutInflater mInflater = LayoutInflater.from(this);
				
		LinearLayout detailRow =  (LinearLayout) mInflater.inflate( R.layout.deckviewcellrow, null);
		RelativeLayout relBgGastro = (RelativeLayout) detailRow.findViewById(R.id.deck_in_layout_eighth);
		relBgGastro.setBackgroundColor(Color2Hex(new String[]{deckColor}));
	    TextView deckTitleView = (TextView) detailRow.findViewById(R.id.drugs_title_eighth);
	    deckTitleView.setText(declTitle);
	    
	    TextView totalCardsView = (TextView) detailRow.findViewById(R.id.drugs_cards_numb_eighth);
	    totalCardsView.setText(totalCards + " cards");
	    TextView profValueView = (TextView) detailRow.findViewById(R.id.drugs_proficiency_value_eighth);
	    profValueView.setText(deckProf);
	    listDeckPreferenceView.add(profValueView);
	    System.out.println("CreateRowViewlistDeckPreferenceView  =  " + profValueView);
	    ImageView iconView = (ImageView) detailRow.findViewById(R.id.drugs_cardicon_eighth);
	    iconView.setImageResource(icon);

	    TextView relCustomClicker = (TextView) detailRow.findViewById(R.id.drugs_eighth_clickable_view);
	    {
			setDataTagsToView(i, totl, profValueView, relCustomClicker);
	    	relCustomClicker.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View v) 
				{
				
					setViewStatus(v);
				}
			});
	    }
		return detailRow;
    }

	@SuppressWarnings("unchecked")
	private void setDataTagsToView(int i, int totl, TextView strFilePath, TextView relCustomshapeQuizView) {
		List listRel = new ArrayList();
		
		listRel.add(strFilePath);
		listRel.add(totl);
		listRel.add(i);
	
			
		relCustomshapeQuizView.setTag(listRel);
	}
    
	@SuppressWarnings("unchecked")
	private void setViewStatus(View v)
    {
		
		List listRel = (List) v.getTag();
		total = (Integer) listRel.get(1);
		if(listRel != null && listRel.size() > 0)
		{
			TextView recordFilePath = (TextView) listRel.get(0);
			Intent i = new Intent(DeckView.this, ListCardName.class);
			int in  = (Integer) listRel.get(2);
			
		    i.putExtra("com.android.flashcard.screen.cardDetail", in);
		    //i.putExtra("com.android.flashcard.screen.cardDetail", listDeckPreferenceValue.get(in));
		    System.out.println("setViewStatus  =  " + listDeckPreferenceValue.get(in));
		    startActivity(i);
			Log.e("detailRow1", recordFilePath +"" + "");
		}
    }

	public List<String> getListCardsPreferenceName() {
		return listCardsPreferenceName;
	}

	public List<String> getListCardsPreferenceValue() {
		return listCardsPreferenceValue;
	}

	List<String> listCardsPreferenceName;
	List<String> listCardsPreferenceValue;
	
	List<String> listDeckPreferenceName;
	List<String> listDeckPreferenceValue;
	
	List<TextView> listDeckPreferenceView;

	public List<String> getListDeckPreferenceName() {
		return listDeckPreferenceName;
	}

	public List<String> getListDeckPreferenceValue() {
		return listDeckPreferenceValue;
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
		screen = this;
		setContentView(R.layout.pharm);
		
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
        	fromClassName = extras.getString("com.nclex.qa.app.packageName.nclecquizview.class.name");
        }
		
		mainDeckViewLinear = (LinearLayout) findViewById(R.id.deckview_lin_drugs_main_layout);
		
        profvalueAllCard = (TextView) findViewById(R.id.proficiency_value);
    	profvalueBookMark = (TextView) findViewById(R.id.bookmarked_cards_proficiency_value);
		totNumbOfAllCard = (TextView) findViewById(R.id.drugs_cards_numb_all_cards);
		totNumbOfBookMarkCard = (TextView) findViewById(R.id.no_of_bookmarked_cards);
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
    	
        mFCDbHelper.openDataBase();
//    	List<String[]> totalDeckInfo = mFCDbHelper.getDeksTotalIfo();
        Cursor cursorDeck = mFCDbHelper.getDeksInfoCursor();
    	//mFCDbHelper.close();
//    	int arrayDeckIcon[] = new int[totalDeckInfo.size()];
    	int arrayDeckIcon[] = new int[cursorDeck.getCount()];
    	arrayDeckIcon[0] = R.drawable.deck_1;
    	arrayDeckIcon[1] = R.drawable.deck_2;
    	arrayDeckIcon[2] = R.drawable.deck_3;
    	arrayDeckIcon[3] = R.drawable.deck_4;
    	arrayDeckIcon[4] = R.drawable.deck_5;
    	arrayDeckIcon[5] = R.drawable.deck_6;
        arrayDeckIcon[6] = R.drawable.deck_7;
    	arrayDeckIcon[7] = R.drawable.deck_8;
    	arrayDeckIcon[8] = R.drawable.deck_9;
    	 mFCDbHelper.close();
        SharedPreferences myPrefs = null;

        listDeckPreferenceName = new ArrayList<String>();
        listDeckPreferenceValue = new ArrayList<String>();
        listCardsPreferenceName = new ArrayList<String>();
        listCardsPreferenceValue = new ArrayList<String>();
        listDeckPreferenceView = new ArrayList<TextView>();
        
        cursorDeck.moveToFirst();
        for (int i = 0; i < cursorDeck.getCount(); i++)
        {
        
//        for (int i = 0; i < totalDeckInfo.size(); i++)
//        {
//    		String strArr[] = totalDeckInfo.get(i);
//			String strDeckPrefName = strArr[1];
//			String strDeckPrefValue = strArr[1];
//			String strCardPrefsName = strArr[1];
//			String strCardPrefsValue = strArr[1];
			
    		String strArr = cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKTITLE));
			String strDeckPrefName = strArr;
			String strDeckPrefValue = strArr;
			String strCardPrefsName = strArr;
			String strCardPrefsValue = strArr;
			
			strDeckPrefName = strDeckPrefName.substring(0, 6/*strDeckPrefName.indexOf(" ") - 1*/) + "CARDPREFS";
			strDeckPrefValue = strDeckPrefValue.substring(0, 6/*strDeckPrefValue.indexOf(" ") - 1*/) + "CARDPREFSVALUE";
			strCardPrefsName = strCardPrefsName.substring(0, 6/*strCardPrefsName.indexOf(" ") - 1*/) + "PREFSGENERICCD";
			strCardPrefsValue = "STRCLICK" + strCardPrefsValue.substring(0, 6/*strCardPrefsValue.indexOf(" ") - 1*/) + "CD";
			
			listDeckPreferenceName.add(strDeckPrefName);
		    System.out.println("DOCstrDeckPrefName   =  " + strDeckPrefName);
			listDeckPreferenceValue.add(strDeckPrefValue);
		    System.out.println("DOCstrDeckPrefValue  =  " + strDeckPrefValue);
			listCardsPreferenceName.add(strCardPrefsName);
		    System.out.println("DOCstrCardPrefsName  =  " + strCardPrefsName);
			listCardsPreferenceValue.add(strCardPrefsValue);
		    System.out.println("DOCstrCardPrefsValue  =  " + strCardPrefsValue);
	        cursorDeck.moveToNext();
		}
        myPrefs =  DeckView.this.getSharedPreferences("StrAllCardPrefs", MODE_PRIVATE);
        String strAllCard = myPrefs.getString("STRALLCARD", "0.00%");
        profvalueAllCard.setText(strAllCard);
        
        myPrefs =  DeckView.this.getSharedPreferences("StrBookMarkPrefs", MODE_PRIVATE);
        String strBookMark = myPrefs.getString("STRBOOKMARK", "0.00%");
    	profvalueBookMark.setText(strBookMark);
        
        myPrefs =  DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
        int bookMarkProfcardStatus = myPrefs.getInt("BOOKMARKEDCARDS", 0);
        totNumbOfBookMarkCard.setText(bookMarkProfcardStatus + " cards");

		mFCDbHelper.openDataBase();
        MAX_CARD_NUMBER = mFCDbHelper.getDeksTotalNumOfCards("");
        mFCDbHelper.close();
    	totNumbOfAllCard.setText(MAX_CARD_NUMBER + " cards");
    	total = 1;
    	
        cursorDeck.moveToFirst();
    	for (int i = 0; i < cursorDeck.getCount(); i++)
    	{
//    	for (int i = 0; i < totalDeckInfo.size(); i++)
//    	{
//    		String strArr[] = totalDeckInfo.get(i);
    		mFCDbHelper.openDataBase();
            int totalCards = mFCDbHelper.getDeksTotalNumOfCards(i + "");
            mFCDbHelper.close();
            myPrefs =  DeckView.this.getSharedPreferences(listDeckPreferenceName.get(i), MODE_PRIVATE);
            String prefVal = myPrefs.getString(listDeckPreferenceValue.get(i), "0.00%");

//          LinearLayout detailRow =  CreateRowView(totalCards, strArr[1], arrayDeckIcon[i], strArr[3], prefVal, total, i);
          LinearLayout detailRow =  CreateRowView(totalCards, cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKTITLE)), 
          		arrayDeckIcon[i], cursorDeck.getString(cursorDeck.getColumnIndexOrThrow(FCDBHelper.DECKCOLOR)), prefVal, total, i);
  		
//    		if(i < totalDeckInfo.size() - 1)
    		if(i <= cursorDeck.getCount()+1)
    			mainDeckViewLinear.addView(createSeqUnderLine());
    		mainDeckViewLinear.addView(detailRow);
			System.out.println("DOCTotalcards =  "+total);
    		total = total + totalCards + 1;
			System.out.println("DOCTotalcardsafter =  "+total);
	        cursorDeck.moveToNext();
		}
    	
        RelativeLayout allcardRel = (RelativeLayout) findViewById(R.id.all_cards);
        allcardRel.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(DeckView.this, ListCardName.class);
				Integer in = -1;
				i.putExtra("com.android.flashcard.screen.cardDetail", in);
			    startActivity(i);
			}
		});

        RelativeLayout bookmarkcardRel = (RelativeLayout) findViewById(R.id.bookmarked_cards);
        bookmarkcardRel.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				mFCDbHelper.openDataBase();
        		List<String> listpk_FlashCardId = mFCDbHelper.getBookMarkedCardStatus();
            	mFCDbHelper.close();
        		int lastCardNo = listpk_FlashCardId.size();
        		if(lastCardNo<1)
        		{
	    	        AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
	    	        alt_bld.setMessage("There are no bookmarked items").setCancelable(false)
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
					Intent i = new Intent(DeckView.this, ListCardName.class);
					Integer in = -2;
					i.putExtra("com.android.flashcard.screen.cardDetail", in);
				    startActivity(i);
        		}
			}
		});

        Button searchbut = (Button) findViewById(R.id.search_footer);
        searchbut.setOnClickListener(new OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
		
			{
				startActivity(new Intent(DeckView.this, Search.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			}
        });

        Button indxSerchBut = (Button) findViewById(R.id.index_footer);
        indxSerchBut.setOnClickListener(new OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
		
			{
				startActivity(new Intent(DeckView.this, IndexSearch.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			}
        });

        Button settingBut = (Button) findViewById(R.id.setting_footer);
        settingBut.setOnClickListener(new OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
		
			{
				startActivity(new Intent(DeckView.this, Setting.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			}
        });

        Button helpBut = (Button) findViewById(R.id.help_footer);
        helpBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(DeckView.this, Help.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
			}
        });
        final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        final RelativeLayout takePickPanal = (RelativeLayout) findViewById(R.id.sliderinfo_top_rel);
        Button infoBut = (Button) findViewById(R.id.info_footer);
        infoBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(DeckView.this, Info.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
               /* mainLayout.removeView(takePickPanal);
                mainLayout.addView(takePickPanal);
			    takePickPanal.setVisibility(View.VISIBLE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 100, 0);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    takePickPanal.startAnimation(slide);*/
			}
		});
        
        RelativeLayout introLayout = (RelativeLayout) findViewById(R.id.introduction);
        introLayout.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(DeckView.this, Introduction.class);
			   // i.putExtra("com.android.flashcard.screen.cardDetail", "all_cards");
			    startActivity(i);
			}
		});
        
        ImageButton infoSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_info);
        infoSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{    
				takePickPanal.clearAnimation();
				takePickPanal.setVisibility(View.GONE);
				startActivity(new Intent(DeckView.this, Info.class));
				overridePendingTransition(R.anim.push_up_in, R.anim.hold);
				mainLayout.removeView(takePickPanal);
			}
        });
         
        
        ImageButton voiceSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_voicenotes);
        voiceSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				mFCDbHelper.openDataBase();
		    	List<String> list = mFCDbHelper.getVoiceSearchResultsForIndividual();
		    	mFCDbHelper.close();
		    	takePickPanal.clearAnimation();
		    	if(list.size() == 0)
		    	{
		    		 AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
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
		    		TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
		    		slide.setDuration(700);
		    		slide.setFillAfter(true);
		    		takePickPanal.startAnimation(slide);
		    		takePickPanal.setVisibility(View.GONE);
		    		Intent i = new Intent(DeckView.this, CardNotesDetail.class);//VoiceNoteDetails			
		    		i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
		    		startActivity(i);
		    		overridePendingTransition(R.anim.push_up_in, R.anim.hold);
		    	}
		    	 mainLayout.removeView(takePickPanal);
			}
        });
        
        ImageButton commentSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_comments);
        commentSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{ 
				mFCDbHelper.openDataBase();
		    	List<String> list = mFCDbHelper.getCommentSearchResultsForIndividual();
		    	mFCDbHelper.close();
		    	if(list.size() == 0)
		    	{
		    		 AlertDialog.Builder alt_bld = new AlertDialog.Builder(DeckView.this);
		    	        alt_bld.setMessage("No Comments Found!").setCancelable(false)
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
		    		takePickPanal.clearAnimation();
					takePickPanal.setVisibility(View.GONE);
				    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
				    slide.setDuration(700);
				    slide.setFillAfter(true);
				    takePickPanal.startAnimation(slide);
					takePickPanal.setVisibility(View.GONE);
					Intent i = new Intent(DeckView.this, MyComments.class);		//CommentDetails		
					i.putExtra("com.fadavis.pharmphlashfc.phone.fromclass", "VIEW");
					startActivity(i);
					overridePendingTransition(R.anim.push_up_in, R.anim.hold);
		    	}
				mainLayout.removeView(takePickPanal);
			}
        });
        
        ImageButton facebookSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_facebook);
        facebookSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{    
				takePickPanal.clearAnimation();
				takePickPanal.setVisibility(View.GONE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    takePickPanal.startAnimation(slide);
				takePickPanal.setVisibility(View.GONE);
        		//postMessage();
        		mainLayout.removeView(takePickPanal);
			}
        });
        
        ImageButton teitterSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_twitter);
        teitterSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{    
				takePickPanal.clearAnimation();
				takePickPanal.setVisibility(View.GONE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    takePickPanal.startAnimation(slide);
				takePickPanal.setVisibility(View.GONE);
				String twitterUri = "http://m.twitter.com/?status=";
		        String marketUri = "TESTING ";
		        Intent shareOnTwitterIntent = new Intent(Intent.ACTION_VIEW,
		                Uri.parse(twitterUri + marketUri));
		        startActivity(shareOnTwitterIntent);
			//	postTweetMsg();
				mainLayout.removeView(takePickPanal);
			}
        });
        
        ImageButton cancelSliderBut = (ImageButton) findViewById(R.id.sliderinfo_but_cancel);
        cancelSliderBut.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{    
				takePickPanal.clearAnimation();
				takePickPanal.setVisibility(View.GONE);
			    TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 1000);
			    slide.setDuration(700);
			    slide.setFillAfter(true);
			    takePickPanal.startAnimation(slide);
				takePickPanal.setVisibility(View.GONE);
				mainLayout.removeView(takePickPanal);
			}
        });
    	
       // this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
     //   this.facebookConnector = new FacebookConnector(TwitterConstants.FACEBOOK_APPID, this, getApplicationContext(), new String[] {TwitterConstants.FACEBOOK_PERMISSION});

		if(fromClassName != null && fromClassName.equals("PREPAREREQUESTTOKENACTIVITY"))
		{
			//startSendingMessage();
		}
		
    } 
    static int total;
    
    public int Color2Hex( String[] args ) {
    	int cc = 0;
    	if (args.length != 3) {
    		String col = args[0];
    		String r = col.substring(0, col.indexOf(","));
    		String g = col.substring(col.indexOf(",") + 1, col.lastIndexOf(","));
    		String b = col.substring(col.lastIndexOf(",") + 1, col.length());
    		int red = Integer.parseInt(r);
    		int green = Integer.parseInt(g);
    		int blue = Integer.parseInt(b);
    		cc = Color.argb(255, red, green, blue);
    		
    	}
    	else {
    		int red = Integer.parseInt(args[0]);
    		int green = Integer.parseInt(args[1]);
    		int blue = Integer.parseInt(args[2]);
    		cc = Color.argb(255, red, green, blue);//rgb(red, green, blue);
        }
		return cc;
    }
	
	private void populateProficiencyValue(double dbl, TextView tv, String sharedPrefName, String sharedPrefParam)
	{
		
		String value ="0.00%";
		
		if(dbl>0)
		{
		int ix = (int)(dbl * 10000.0);
		double dbl2 = ((double)ix)/100.0;
		value = dbl2 + "%";
		}
		
		tv.setText(value);
		SharedPreferences myPrefs = DeckView.this.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
	    prefsEditor.putString(sharedPrefParam, value);		            
	    prefsEditor.commit();
	}
	
	public void deleteAllBookmarks()
	{
    	profvalueBookMark.setText("0.00%");
    	totNumbOfBookMarkCard.setText(0 + " cards");
		SharedPreferences myPrefs = DeckView.this.getSharedPreferences("StrBookMarkPrefs", MODE_PRIVATE);
	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
	    prefsEditor.putString ("STRBOOKMARK", "0.00%");		            
	    prefsEditor.commit();
	    myPrefs = DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
	    prefsEditor = myPrefs.edit();
	    prefsEditor.putInt("BOOKMARKEDCARDS", 0);		            
	    prefsEditor.commit();
	}
	
	public void setStrAllCard(int strAllCard) {
		Log.e("setStrAllCard", strAllCard + " : " + 389);
		double dbl = (double)strAllCard/389;
    	populateProficiencyValue(dbl, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
	}

	public void setStrBookMark(int strBookMark, int total)
	{
		Log.e("setStrBookMark", strBookMark + " : " + total);
		double dbl = (double)strBookMark/total;
    	populateProficiencyValue(dbl, profvalueBookMark, "StrBookMarkPrefs", "STRBOOKMARK");
		SharedPreferences myPrefs = DeckView.this.getSharedPreferences("TotalbookMarkedProfPrefs", MODE_PRIVATE);
	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
	    prefsEditor.putInt("BOOKMARKEDCARDS", total);		            
	    prefsEditor.commit();
	    totNumbOfBookMarkCard.setText(total + " cards");
	}

	public void setProfSelGastro(int strQZ, int totals, int i) {
		Log.e("setProfSelGastro", strQZ + " : " + totals);
		double dbl = (double)strQZ/totals;
    	populateProficiencyValue(dbl, listDeckPreferenceView.get(i), listDeckPreferenceName.get(i), listDeckPreferenceValue.get(i));
	}

	public void deleteAllProficiency()
	{
    	profvalueBookMark.setText("0.00%");
    	populateProficiencyValue(0.00, profvalueAllCard, "StrAllCardPrefs", "STRALLCARD");
    	for (int i = 0; i < listDeckPreferenceView.size(); i++) {
			populateProficiencyValue(0.00, listDeckPreferenceView.get(i), listDeckPreferenceName.get(i), listDeckPreferenceValue.get(i));
	    System.out.println("deleteAllProficiency  =  " + i + " , " + listDeckPreferenceView.get(i) + " , " + listCardsPreferenceName.get(i)+ " , " +listCardsPreferenceValue.get(i));
	    
		}
    	isdeleteAllProficiency = true;
    	decrementProficiency();
	}
	
	public boolean isdeleteAllProficiency = false;
	
	private void decrementProficiency()
	{
		storeDataInPreferences(0, "StrAllCardPrefsGenericcd", "STRALLCARDGENERICcd");
		storeDataInPreferences(0, "StrBookMarkPrefsGenericcd", "STRBOOKMARKGENERICcd");
		storeDataInPreferences(0, "StrbookMarkProfcardsStatusPrefsGenericcd", "BOOKMARKPROFCARDSTATUScd");
		
		for (int i = 0; i < listCardsPreferenceName.size(); i++) {
			storeDataInPreferences(0, i/*listCardsPreferenceName.get(i), listCardsPreferenceValue.get(i)*/);
		}
	}
	
    protected void storeDataInPreferences(int value, String sharedPrefName, String sharedPrefParam) 
	{
		SharedPreferences myPrefs = DeckView.this.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
	    prefsEditor.putInt(sharedPrefParam, value);
	    prefsEditor.commit();
	    System.out.println("storeDataInPreferences  =  " + value + " , " + sharedPrefName+ " , " +sharedPrefParam);
	}
    
    protected void storeDataInPreferences(int value, int i/*, String sharedPrefParam*/)  
	{
		SharedPreferences myPrefs = DeckView.this.getSharedPreferences(listCardsPreferenceName.get(i), MODE_PRIVATE);
	    SharedPreferences.Editor prefsEditor = myPrefs.edit();
	    prefsEditor.putInt(listCardsPreferenceValue.get(i), value);
	    System.out.println("storeDataInPreferencesvalue  =  " + i + " , " + value + " , " + listCardsPreferenceName.get(i)+ " , " +listCardsPreferenceValue.get(i));
	    prefsEditor.commit();
	}
    
    public void resetApplication()
    {
    	decrementProficiency();
    	deleteAllBookmarks();
    	deleteAllProficiency();
    }
    
    //FACEBOOK
    
/*	private FacebookConnector facebookConnector;
	private final Handler mFacebookHandler = new Handler();

	
    final Runnable mUpdateFacebookNotification = new Runnable() {
        public void run() {
        	mProgressDialog.dismiss();
        	Toast.makeText(getBaseContext(), "Facebook updated !", Toast.LENGTH_LONG).show();
        }
    };
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.facebookConnector.getFacebook().authorizeCallback(requestCode, resultCode, data);
	}
	

	private String getFacebookMsg() {
		String value = profvalueAllCard.getText().toString();
		if(value != null && !value.equals(""))	
			proficScore = value;
		return TwitterConstants.MSGSUFF + proficScore + TwitterConstants.MSGPREF + MAX_CARD_NUMBER + TwitterConstants.MSGPREFTWT;
		//return Constants.MSGSUFF + quizScore + Constants.MSGPREF;//" at " + new Date().toLocaleString();
	}	
	
	String mPostText = "";
	protected String proficScore = "0";
	
	public void setmPostText(String mPostText) {
		this.mPostText = mPostText;
		postMessageInThreadFromDialog();
	}

	public void postMessage() {
		if (facebookConnector.getFacebook().isSessionValid()) {
			postMessageInThread();
		} else {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {
				
				@Override
				public void onAuthSucceed() {
					postMessageInThread();
				}
				
				@Override
				public void onAuthFail(String error) {
					
				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}
	}

	private void postMessageInThread() {
		FacebookDialog fb = new FacebookDialog(DeckView.this, getFacebookMsg());
        fb.show(); 
	}

	public void postMessageInThreadFromDialog() {
        mProgressDialog = ProgressDialog.show(DeckView.this, "", "...");
		mProgressDialog.setContentView(R.layout.customprogress);
		Thread t = new Thread() {
			public void run() {
		    	
		    	try {
		    		facebookConnector.postMessageOnWall(mPostText);
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TwitterConstants.TAG, "Error sending msg",ex);
				}
		    }
		};
		t.start();
	}
	
	 //FACEBOOK
	
	 //TWITTER
	private String getTweetMsg() 
	{
		String value = profvalueAllCard.getText().toString();
		if(value != null && !value.equals(""))	
			proficScore = value;
		return TwitterConstants.MSGSUFF + proficScore + TwitterConstants.MSGPREF + MAX_CARD_NUMBER + TwitterConstants.MSGPREFTWT;
//		return Constants.MSGSUFF + quizScore + Constants.MSGPREFTWT;
	}

	private SharedPreferences prefs;
	private final Handler mTwitterHandler = new Handler();
	
    final Runnable mUpdateTwitterNotification = new Runnable() {
        public void run() {
        	mProgressDialog.dismiss();
        	Toast.makeText(getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG).show();
        }
    };	
	
    final Runnable mUpdateTwitterNotificationwrongData = new Runnable() {
        public void run() {
        	mProgressDialog.dismiss();
        	Toast.makeText(getBaseContext(), "Error Duplicate Data !", Toast.LENGTH_LONG).show();
        }
    };	
    String mTweetMessage;
	public void postTweetMsgfromTwitterDialog(String tweetMessage) 
	{
		mTweetMessage = tweetMessage;
        mProgressDialog = ProgressDialog.show(DeckView.this, "", "...");
		mProgressDialog.setContentView(R.layout.customprogress);
		sendTweet();
	}
	public void postTweetMsg() 
	{
    	
	}*/
	private ProgressDialog mProgressDialog;
	
/*	private void startSendingMessage() 
    {
        TwitterDialog twit = new TwitterDialog(DeckView.this, prefs, getTweetMsg(),"RESULTVIEW");
        twit.show();
    }
    
	public void sendTweet() {
		Thread t = new Thread() {
	        public void run() {
	        	boolean b = false;
	        	try {
	        		//TwitterUtils.sendTweet(prefs, mTweetMessage);
	        		b=true;
	        		
				} catch (Exception ex) {
					b=false;
					ex.printStackTrace();
				}
				if(b)
					mTwitterHandler.post(mUpdateTwitterNotification);
				else
					mTwitterHandler.post(mUpdateTwitterNotificationwrongData);
	        }
	    };
	    t.start();
	}*/
	
	//TWITTER
}