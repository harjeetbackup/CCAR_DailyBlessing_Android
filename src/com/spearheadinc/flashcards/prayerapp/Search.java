package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class Search extends Activity 
{
	
	EditText ed;
	private FCDBHelper mFCDbHelper;
	private String mSearchString;
	private static Search screen;
	List<String[]> list;
    private	boolean bookmaredCard;
	public static Search getInstance() {
		return screen;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.searchcards);
		screen = this;
		
		Button backBut = (Button) findViewById(R.id.searchcards_back_but);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		mListView = (ListView) findViewById(R.id.searchcards_list);
		
		
		/*TextView searcBut = (TextView) findViewById(R.id.searchcards_back_hitsearch);
		searcBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager) 
				Search.this.getSystemService(Context.INPUT_METHOD_SERVICE); 
				inputManager.hideSoftInputFromWindow(ed.getWindowToken(), 
				InputMethodManager.HIDE_NOT_ALWAYS);
				mSearchString = ed.getText().toString();
				startSearch();
			}
		});
		*/
		 mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
	        updateList(true);
		ed = (EditText) findViewById(R.id.searchcards_edit_search);
		
		ed.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void afterTextChanged(Editable s) {
	        }

	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count,
	                int after) {
	        }

	        @Override
	        public void onTextChanged(CharSequence s, int start, int before,
	                int count) {
				//RefineSearchItems();

	           /* if (isEditable) {
	                isEditable = false;
	                styleText(s.toString());
	            } else {
	                isEditable = true;
	            }*/
	        }

	    });

		ed.setOnEditorActionListener(new OnEditorActionListener() 
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
			{
				Log.e("actionId", ""+actionId);
				if (actionId == EditorInfo.IME_ACTION_SEARCH) 
				{
					InputMethodManager inputManager = (InputMethodManager) 
					Search.this.getSystemService(Context.INPUT_METHOD_SERVICE); 
					inputManager.hideSoftInputFromWindow(ed.getWindowToken(), 
					InputMethodManager.HIDE_NOT_ALWAYS);
					mSearchString = ed.getText().toString();
					//startSearch();
					RefineSearchItems();
				}
				return true;
			}
		});
	
       
    	
    	mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				String s =  parent.getItemAtPosition(pos).toString();
				bookmaredCard = false;
				Log.e("setOnItemClickListener", s);
				Intent i = new Intent(Search.this, CardDetails.class);
				i.putExtra("FROM", "SEARCH");
				i.putExtra("POSITION", "" + pos);
				i.putExtra("SEARCHSTRING", "" + mSearchString);
				i.putExtra("TOTALCARDS", "" + listSearchCardName.size());
				i.putExtra("CLASSNAME", "NORMALSEARCH");
				i.putExtra("isBookmarked", bookmaredCard);
				startActivity(i);
			}
		});
    }
    
    ListView mListView;
    
    @SuppressWarnings("unused")
	private void updateList(boolean isAllCard)
    {
    	mFCDbHelper.openDataBase();
    	mListView = (ListView) findViewById(R.id.searchcards_list);
    	if(isAllCard)
    	{    		
    		list = mFCDbHelper.getAllFlashCardSearch();    		
    	}
    	else
    	{    		
    		
    		mSearchString = ed.getText().toString();
			list = mFCDbHelper.SearchCardsWithKeywordForAndroid(mSearchString);
		listSearchCardName = new ArrayList<String>();
			listSearchCardid = new ArrayList<String>();
    	}
    	mFCDbHelper.close();
    	if( list == null )
    		Log.i("Search","List is null #################################");
    	for (int i = 0; i < list.size(); i++) {
			String[] strArr = list.get(i);
			listSearchCardid.add(strArr[0]);
			listSearchCardName.add(strArr[1]);
    		//String str = (strArr[1]);
    		//int j = Integer.parseInt(str);
    		/*if(j % 2 == 0)
    			j--;

    		if(!listpk_FlashCardFrontBackDetailId.contains(j+""))
    		{
	    		Log.e("listSearchCardid)", j +  "	;	" + strArr[1]);
				listSearchCardid.add(strArr[0]);
				listSearchCardName.add(strArr[1]);
				//listpk_FlashCardFrontBackDetailId.add(j+"");
    		}*/
		}
     
    	mListView.setAdapter(new CustomAdapter(this, listSearchCardid, listSearchCardName));
    }
    
    protected void RefineSearchItems ()
	{
		// call method to get result
    	updateList (false);		
		 
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

    
    public class CustomAdapter extends BaseAdapter 
    {
    	private Activity activity;
    	private  LayoutInflater inflater = null;
		private List<String> mCardID;
		private List<String> mCardName;
    	
        public CustomAdapter(Activity a, List<String> cardid, List<String> cardname) 
        {
            activity = a;
            mCardID = cardid;
            mCardName = cardname;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return mCardID.size();
        }
        
		@SuppressWarnings("unused")
		private int selectedPos = -1;	

		public void setSelectedPosition(int pos){
			selectedPos = pos;
			notifyDataSetChanged();
		}

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        
        public class ViewHolder{
        	public TextView address;
        	public TextView time;        	
        }

        public View getView(int position, View convertView, ViewGroup parent) 
        {
        	View vi=convertView;
        	ViewHolder holder;
        	
    		if(convertView==null)
    		{
    			vi = inflater.inflate(R.layout.listitem_search, null);
    			holder=new ViewHolder();
    		    holder.address=(TextView)vi.findViewById(R.id.list_search_name);  
    		    holder.address.setTag(mCardID.get(position));
    			vi.setTag(holder);
    		}
    		else
    			holder=(ViewHolder)vi.getTag();
    			holder.address.setText(Html.fromHtml(mCardName.get(position)));
    		
            return vi;
        }
    }
}
