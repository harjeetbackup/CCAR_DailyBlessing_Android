package com.spearheadinc.flashcards.prayerapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.spearheadinc.flashcards.prayerapp.ListCardName.CustomAdapter;
import com.spearheadinc.flashcards.prayerapp.ListCardName.CustomAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
		
		lst.add("#");
		lst.add("A");
		lst.add("B");
		lst.add("C");
		lst.add("D");
		lst.add("E");
		lst.add("F");
		lst.add("G");
		lst.add("H");
		lst.add("I");
		lst.add("K");
		lst.add("L");
		lst.add("M");
		lst.add("N");
		lst.add("O");
		lst.add("P");
		lst.add("R");
		lst.add("S");
		lst.add("T");
		lst.add("V");
		lst.add("W");
		lst.add("Y");
		
        myListView=(ListView)findViewById(R.id.myListView);
        myListView.setFastScrollEnabled(false); //must be enabled
  
        //SectionedAdapter adapter = new SectionedAdapter(this);  
        MyCustomAdapter adapter = new MyCustomAdapter();  
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
        	adapter.addSeparatorItem(lst.get(i));
        	for (int j = 0; j < l1.size(); j++) {
        		String [] strArr = l1.get(j);
        		sarr[j] = strArr[1];
        		adapter.addItem(strArr[1]);
				listSearchCardid.add(strArr[0]);
				listSearchCardName.add(strArr[1]);
			}
        	
        	//adapter.addSection(lst.get(i), new ArrayAdapter<String>(this,  R.layout.list_item,R.id.list_item_title, sarr));  
         
		}
       
        myListView.setAdapter(adapter); 
        myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				String s =  parent.getItemAtPosition(pos).toString();
				bookmaredCard = false;
				Log.e("setOnItemClickListener", pos + "" + s);
				
				if(s.startsWith("<i>#") || s.startsWith("#"))
					pos = pos - 1;
				if( s.startsWith("<i>A") || s.startsWith("<i>a") || s.startsWith("A") || s.startsWith("a"))
					pos = pos - 2;
				if(s.startsWith("<i>B") || s.startsWith("<i>b") || s.startsWith("B") || s.startsWith("b"))
					pos = pos - 3;
				if( s.startsWith("<i>C") || s.startsWith("<i>c") || s.startsWith("C") || s.startsWith("c"))
					pos = pos - 4;
				if( s.startsWith("<i>D") || s.startsWith("<i>d") || s.startsWith("D") || s.startsWith("d"))
					pos = pos - 5;
				if( s.startsWith("<i>E") || s.startsWith("<i>e") || s.startsWith("E") || s.startsWith("e"))
					pos = pos - 6;
				if( s.startsWith("<i>F") || s.startsWith("<i>f") || s.startsWith("F") || s.startsWith("f"))
					pos = pos - 7;
				if( s.startsWith("<i>G") || s.startsWith("<i>g") || s.startsWith("G") || s.startsWith("g"))
					pos = pos - 8;
				if( s.startsWith("<i>H") || s.startsWith("<i>h") || s.startsWith("H") || s.startsWith("h"))
					pos = pos - 9;
				if( s.startsWith("<i>I") || s.startsWith("<i>i") || s.startsWith("I") || s.startsWith("i"))
					pos = pos - 10;
				if( s.startsWith("<i>K") || s.startsWith("<i>k") || s.startsWith("K") || s.startsWith("k"))
					pos = pos - 11;
				if( s.startsWith("<i>L") || s.startsWith("<i>l") || s.startsWith("L") || s.startsWith("l"))
					pos = pos - 12;
				if( s.startsWith("<i>M") || s.startsWith("<i>m") || s.startsWith("M") || s.startsWith("m"))
					pos = pos - 13;
				if( s.startsWith("<i>N") || s.startsWith("<i>n") || s.startsWith("N") || s.startsWith("n"))
					pos = pos - 14;
				if( s.startsWith("<i>O") || s.startsWith("<i>o") || s.startsWith("O") || s.startsWith("o"))
					pos = pos - 15;
				if( s.startsWith("<i>P") || s.startsWith("<i>p") || s.startsWith("P") || s.startsWith("p"))
					pos = pos - 16;
				if( s.startsWith("<i>R") || s.startsWith("<i>r") || s.startsWith("R") || s.startsWith("r"))
					pos = pos - 17;
				if( s.startsWith("<i>S") || s.startsWith("<i>s") || s.startsWith("S") || s.startsWith("s"))
					pos = pos - 18;
				if( s.startsWith("<i>T") || s.startsWith("<i>t") || s.startsWith("T") || s.startsWith("t"))
					pos = pos - 19;
				if( s.startsWith("<i>V") || s.startsWith("<i>v") || s.startsWith("V") || s.startsWith("v"))
					pos = pos - 20;
				if( s.startsWith("<i>W") || s.startsWith("<i>w") || s.startsWith("W") || s.startsWith("w"))
					pos = pos - 21;
				if( s.startsWith("<i>Y") || s.startsWith("<i>y") || s.startsWith("Y") || s.startsWith("y"))
					pos = pos - 22;
			
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

        TextView tvHash =(TextView) findViewById(R.id.indexer_hash);
        tvHash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvb", "	***	" + indexarr[0]);
				myListView.setSelection(0);
				
			}
		});
       
        TextView tva =(TextView) findViewById(R.id.indexer_a);
        tva.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvb", "	***	" + indexarr[0]);
				myListView.setSelection(0);
				
			}
		});
     
               
        TextView tvb =(TextView) findViewById(R.id.indexer_b);
        tvb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvf", "" + "	***	" + indexarr[0]);
				myListView.setSelection(indexarr[1]);
				
			}
		});
        TextView tvc =(TextView) findViewById(R.id.indexer_c);
        tvc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvf", "" + "	***	" + indexarr[0]);
				myListView.setSelection(indexarr[2]);
				
			}
		});
        TextView tvd =(TextView) findViewById(R.id.indexer_d);
        tvd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvf", "" + "	***	" + indexarr[0]);
				myListView.setSelection(indexarr[3]);
			}
		});
        
       
        TextView tve =(TextView) findViewById(R.id.indexer_e);
        tve.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[1 + 1]);
				myListView.setSelection(indexarr[4]);
			}
		});
        
        TextView tvf =(TextView) findViewById(R.id.indexer_f);
        tvf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[2 + 1]);
				myListView.setSelection(indexarr[5]);
			}
		});
        
        
        
        TextView tvg =(TextView) findViewById(R.id.indexer_g);
        tvg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[3 + 1]);
				myListView.setSelection(indexarr[6]);
			}
		});
        
        TextView tvh =(TextView) findViewById(R.id.indexer_h);
        tvh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[4 + 1]);
				myListView.setSelection(indexarr[7]);
			}
		});
        
        
        TextView tvi =(TextView) findViewById(R.id.indexer_i);
        tvi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[5 + 1]);
				myListView.setSelection(indexarr[8]);
			}
		});
        
        TextView tvk =(TextView) findViewById(R.id.indexer_k);
        tvk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[6 + 1]);
				myListView.setSelection(indexarr[9]);
			}
		});
       
        TextView tvl =(TextView) findViewById(R.id.indexer_l);
        tvl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[6 + 1]);
				myListView.setSelection(indexarr[10]);
			}
		});
        
        TextView tvm =(TextView) findViewById(R.id.indexer_m);
        tvm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[11]);
			}
		});
        TextView tvn =(TextView) findViewById(R.id.indexer_n);
        tvn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[12]);
			}
		});
        TextView tvo =(TextView) findViewById(R.id.indexer_o);
        tvo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[13]);
			}
		});
        TextView tvp =(TextView) findViewById(R.id.indexer_p);
        tvp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[14]);
			}
		});
        TextView tvr =(TextView) findViewById(R.id.indexer_r);
        tvr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[15]);
			}
		});
        
        TextView tvs =(TextView) findViewById(R.id.indexer_s);
        tvs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[16]);
			}
		});     
        TextView tvt =(TextView) findViewById(R.id.indexer_t);
        tvt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[17]);
			}
		});     
        TextView tvv =(TextView) findViewById(R.id.indexer_v);
        tvv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[18]);
			}
		});
        TextView tvw =(TextView) findViewById(R.id.indexer_w);
        tvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[19]);
			}
		});
        TextView tvy =(TextView) findViewById(R.id.indexer_y);
        tvy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("tvt", "	***	" + indexarr[7 + 1]);
				myListView.setSelection(indexarr[20]);
			}
		});

    }
    int indexarr[] = new int[22];
    
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
	 private class MyCustomAdapter extends BaseAdapter {
		 
		 private static final int TYPE_SEPARATOR = 0;
	        private static final int TYPE_ITEM = 1;	       
	        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	 
	        private ArrayList mData = new ArrayList<String>();
	        private LayoutInflater mInflater;
	 
	        private TreeSet mSeparatorsSet = new TreeSet();
	 
	        public MyCustomAdapter() {
	            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        }
	 
	        public void addItem(final String item) {
	            mData.add(item);
	            notifyDataSetChanged();
	        }
	 
	        public void addSeparatorItem(final String item) {
	            mData.add(item);
	            // save separator position
	            mSeparatorsSet.add(mData.size()-1);
	            notifyDataSetChanged();
	        }
	 
	        @Override
	        public int getItemViewType(int position) {
	            return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	        }
	 
	        @Override
	        public int getViewTypeCount() {
	            return TYPE_MAX_COUNT;
	        }
	 
	        @Override
	        public int getCount() {
	            return mData.size();
	        }
	 
	        @Override
	        public String getItem(int position) {
	            return (String) mData.get(position);
	        }
	 
	        @Override
	        public long getItemId(int position) {
	            return position;
	        }
	 
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            ViewHolder holder = null;
	            int type = getItemViewType(position);
	            System.out.println("getView " + position + " " + convertView + " type = " + type);
	            if (convertView == null) {
	                holder = new ViewHolder();
	                convertView = mInflater.inflate(R.layout.cardname, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.list_card_name);
	              /*  switch (type) {
	                case TYPE_SEPARATOR:
                        convertView = mInflater.inflate(R.layout.list_header, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.list_header_title);
                        break;
	                    case TYPE_ITEM:
	                        convertView = mInflater.inflate(R.layout.cardname, null);
	                        holder.textView = (TextView)convertView.findViewById(R.id.list_card_name);
	                        break;
	                    
	                }*/
	                convertView.setTag(holder);
	            } else {
	                holder = (ViewHolder)convertView.getTag();
	            }
	            holder.textView.setText(Html.fromHtml((String) mData.get(position)));
	            return convertView;
	        }
	       
	    }
	 public static class ViewHolder {
	        public TextView textView;
	    }
}
