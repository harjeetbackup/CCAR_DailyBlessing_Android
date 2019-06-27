package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class IndexSearch extends Activity {
	static IndexSearch mIndexSearch;
	public static IndexSearch getInstance() {
		return mIndexSearch;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.indexsearch);
		mIndexSearch = this;
		
		Button backBut = (Button) findViewById(R.id.index_searchcardsback_but);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
		
		List <String> lst = new ArrayList<String>();
		
		lst.add("B");
		lst.add("F");
		lst.add("O");
		lst.add("P");
		lst.add("R");
		lst.add("S");
		lst.add("T");
		lst.add("U");
		
        myListView=(ListView)findViewById(R.id.myListView);
        myListView.setFastScrollEnabled(false); //must be enabled
  
        SectionedAdapter adapter = new SectionedAdapter(this); 
        mFCDbHelper.openDataBase();
        List<List<String[]>> list = mFCDbHelper.getSearchResultCursora();
        mFCDbHelper.close();
        int count = 0;
        
        for (int i = 0; i < list.size(); i++) 
        {
        	List<String[]> l1 = list.get(i);
        	String [] sarr = new String[l1.size()];
        	count = count + 0+l1.size() +1;
        	indexarr[i] = count;
        	for (int j = 0; j < l1.size(); j++) {
        		String [] strArr = l1.get(j);
        		sarr[j] = strArr[1];
				listSearchCardid.add(strArr[0]);
			}
        	adapter.addSection(lst.get(i), new ArrayAdapter<String>(this,  R.layout.list_item,R.id.list_item_title, sarr));  
		}
        
        myListView.setAdapter(adapter); 
        myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				String s =  parent.getItemAtPosition(pos).toString();
				bookmaredCard = false;
				Log.e("setOnItemClickListener", pos + "" + s);
				
			
				
				if( s.startsWith("B"))
					pos = pos - 1;
				if(s.startsWith("F"))
					pos = pos - 2;
				if(s.startsWith("O"))
					pos = pos - 2 - 1;
				if( s.startsWith("P"))
					pos = pos - 3 - 1;
				if( s.startsWith("R"))
					pos = pos - 4 - 1;
				if( s.startsWith("S"))
					pos = pos - 5 - 1;
				if( s.startsWith("T"))
					pos = pos - 6 - 1;
				if( s.startsWith("U"))
					pos = pos - 7 - 1;
				Log.e("afterListener", pos + "" + s);
				
				Intent i = new Intent(IndexSearch.this, CardDetails.class);
				i.putExtra("FROM", "SEARCH");
				i.putExtra("POSITION", "" + pos);
				i.putExtra("SEARCHSTRING", "" + ""/*s*/);
				i.putExtra("TOTALCARDS", "" + listSearchCardid.size());
				i.putExtra("CLASSNAME", "INDEXSEARCH");
				i.putExtra("isBookmarked", bookmaredCard);
				startActivity(i);
			}
		});

      
               
        TextView tvb =(TextView) findViewById(R.id.indexer_b);
        tvb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvb", "	***	" + indexarr[0]);
				myListView.setSelection(0);
			}
		});
     
        TextView tvf =(TextView) findViewById(R.id.indexer_f);
        tvf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvf", "" + "	***	" + indexarr[0]);
				myListView.setSelection(indexarr[0]);
			}
		});
        
      
        
      
        
        TextView tvo =(TextView) findViewById(R.id.indexer_o);
        tvo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvo", "	***	" + indexarr[1]);
				myListView.setSelection(indexarr[1]);
			}
		});
        TextView tvp =(TextView) findViewById(R.id.indexer_p);
        tvp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvp", "	***	" + indexarr[2]);
				myListView.setSelection(indexarr[2]);
			}
		});
        TextView tvr =(TextView) findViewById(R.id.indexer_r);
        tvr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvr", "	***	" + indexarr[3]);
				myListView.setSelection(indexarr[3]);
			}
		});
        TextView tvs =(TextView) findViewById(R.id.indexer_s);
        tvs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvs", "	***	" + indexarr[4]);
				myListView.setSelection(indexarr[4]);
			}
		});
        
        TextView tvt =(TextView) findViewById(R.id.indexer_t);
        tvt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[5]);
				myListView.setSelection(indexarr[5]);
			}
		});
        
             
        TextView tvv =(TextView) findViewById(R.id.indexer_u);
        tvv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvv", "	***	" + indexarr[6]);
				myListView.setSelection(indexarr[6]);
			}
		});
        
      
        

    }
    int indexarr[] = new int[9];
    
	ListView myListView;
	Cursor myCursor;
	String[] proj;
	private FCDBHelper mFCDbHelper;
	boolean bookmaredCard;
    public final static String ITEM_TITLE = "title";  
    public final static String ITEM_CAPTION = "caption";   
  
    public Map<String,?> createItem(String title, String caption) {  
        Map<String,String> item = new HashMap<String,String>();  
        item.put(ITEM_TITLE, title);  
        item.put(ITEM_CAPTION, caption);  
        return item;  
    }  
	
	List<String> listSearchCardName = new ArrayList<String>();
	List<String> listSearchCardid = new ArrayList<String>();
	List<String> listpk_FlashCardFrontBackDetailId = new ArrayList<String>();
	public List<String> getListSearchCardName() {
		return listSearchCardName;
	}

	public List<String> getListSearchCardid() {
		return listSearchCardid;
	}

	public List<String> getListSearchpk_FlashCardFrontBackDetailId() {
		return listpk_FlashCardFrontBackDetailId;
	}

}
