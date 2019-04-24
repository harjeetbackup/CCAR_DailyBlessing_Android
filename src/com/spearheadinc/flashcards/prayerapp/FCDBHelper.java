package com.spearheadinc.flashcards.prayerapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.util.Log;

public class FCDBHelper extends SQLiteOpenHelper
{
//    private static String DB_PATH = "/data/data/com.android.flash.screens/databases/";
    private static String DB_PATH = "/data/data/com.spearheadinc.flashcards.prayerapp/databases/";
    private static String DB_NAME = "PrayerApp1.db3";//"LeekFlashCardss.db3";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
    private boolean isRandomized;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public FCDBHelper(Context context)
    {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
//    public void createDataBase() throws IOException{
// 
//    	boolean dbExist = checkDataBase();
// 
//    	if(dbExist)
//    	{
//    		//do nothing - database already exist
//    	}
//    	else
//    	{
//    		//By calling this method an empty database will be created into the default system path
//               //of your application so we are gonna be able to overwrite that database with our database.
//        	this.getReadableDatabase();
//        	try 
//        	{
//    			copyDataBase();
//    		} catch (IOException e) {
//        		throw new Error("Error copying database");
//        	}
//    	}
//    }
    public void setRandomized(boolean b)
    {
    	isRandomized = b;
    }
    
    public boolean getRandomized()
    {
    	return isRandomized;
    }
    
    public List<String> selectCardStatus(String table, String[] strArr) 
    {
       List<String> list = new ArrayList<String>();
//       this.sqlDB = openHelper.getWritableDatabase();
       
       Cursor cursor = myDataBase.query(true, table, strArr, 
    		   null, null, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  for(int i = 0; i < strArr.length; i ++)
     		  {
	     		  String str = cursor.getString(i);
	    		  list.add(str);
     		  }
//     		  String str1 = cursor.getString(1);
//    		  list.add(str1);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
       for(int i = 0; i < list.size(); i ++)
	   {
		   Log.e("" + table, list.get(i)+"");
	   }
       return list;
    }
    
    
    
    /** 
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
    	try
    	{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
    
    /**
       * Creates a empty database on the system and rewrites it with your own database.
       * */
      public void createDataBase() throws IOException{
   
      	boolean dbExist = checkDataBase();
   
      	if(dbExist)
      	{
      		//do nothing - database already exist
      	}
      	else
      	{
      		//By calling this method an empty database will be created into the default system path
                 //of your application so we are gonna be able to overwrite that database with our database.
      		String path = DB_PATH + "PrayerApp.db3";
      		File f = new File( path); 
     	    if(f.isDirectory()) 
     	    {
     	    	 f.delete();
     	    }
          	this.getReadableDatabase();
          	try 
          	{
//      			copyDataBase();
      			copyFromZipFile();
      		} catch (IOException e) {
      			e.printStackTrace();
          		throw new Error("Error copying database");
          	}
      	}
      }
      private void copyFromZipFile() throws IOException
      {
//      	 InputStream is = myContext.getAssets().open("NewATI.zip", AssetManager.ACCESS_RANDOM);//
      	InputStream is = myContext.getResources().openRawResource(R.raw.schlossbergnew);//newati fadnclex nclexnew
      	// Path to the just created empty db
      	File outFile = new File(DB_PATH ,DB_NAME);
      	//Open the empty db as the output stream
      	OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
      	ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
      	try 
      	{
//      		ZipEntry ze;
      		while ((/*ze =*/ zis.getNextEntry()) != null) 
      		{
      			ByteArrayOutputStream baos = new ByteArrayOutputStream();
      			byte[] buffer = new byte[1024];
      			int count;
      			while ((count = zis.read(buffer)) != -1) {
      				baos.write(buffer, 0, count);
      				//Log.d("", buffer.toString());
  				}
      			baos.writeTo(myOutput);
  			}
  		} 
      	finally 
  		{
  			zis.close();
  			myOutput.flush();
  			myOutput.close();
  			is.close();
  		}
  	} 
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
//    private void copyDataBase() throws IOException
//    {
//    	InputStream myInput = myContext.getAssets().open(DB_NAME);
//    	String outFileName = DB_PATH + DB_NAME;
//    	OutputStream myOutput = new FileOutputStream(outFileName);
////    	int i = myInput.available();
//    	byte[] buffer = new byte[myInput.available() + 1];
//    	int length;
//    	while ((length = myInput.read(buffer))>0){
//    		myOutput.write(buffer, 0, length);
//    	}
//    	myOutput.flush();
//    	myOutput.close();
//    	myInput.close();
//    }
 
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }
 
    @Override
	public synchronized void close() {
	    if(myDataBase != null)
		    myDataBase.close();
	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	
	@SuppressWarnings("unchecked")
	public int getDeksTotalNumOfCards(String cardId)
	{
	       List list = new ArrayList();
//	       this.sqlDB = openHelper.getWritableDatabase();
	       Cursor cursor = null;
	       if(cardId.equalsIgnoreCase(""))
	       {
		       cursor = myDataBase.query(true, "m_FlashCard", new String[]{"pk_FlashCardId"}, 
		   				null, null, null, null, null, null); 
	       }
	       else
	    	   cursor = myDataBase.query(true, "m_FlashCard", new String[]{"InternalCardId"}, 
	    			   "fk_FlashCardDeckId= ?", new String[]{cardId}, null, null, null, null); 
	       int i=0;
	       if (cursor.moveToFirst())
	       {
	     	  do 
	     	  {	
//	     		  for(int i = 0; i < strArr.length; i ++)
	     		  {
		     		  String str = cursor.getString(0);
		    		  list.add(str);
					  Log.e("getDeksTotalNumOfCards  " + cardId, str+"");
		     		  i ++;
	     		  }
//	     		  String str1 = cursor.getString(1);
//	    		  list.add(str1);
	     	  } 
	     	  while (cursor.moveToNext());
	       }
	       if (cursor != null && !cursor.isClosed())
	       {
	     	  cursor.close();
	       }
//	       for(int i = 0; i < list.size(); i ++)
//		   {
//			   Log.e("" + table, list.get(i)+"");
//		   }
	       return list.size();
	}
	
	@SuppressWarnings("unchecked")
	public int getTotalNumOfDeks()
	{
	       List list = new ArrayList();
//	       this.sqlDB = openHelper.getWritableDatabase();
	       
	       Cursor cursor = myDataBase.query(true, "m_FlashCardDecks", new String[]{"pk_FlashCardDeckId"}, 
	    		   null, null, null, null, null, null); 
	       int i=0;
	       if (cursor.moveToFirst())
	       {
	     	  do 
	     	  {	
	     		  int str = cursor.getInt(0);
	    		  list.add(str);
				  Log.e("getTotalNumOfDeks " + i, str+"");
	     		  i ++;
	     	  } 
	     	  while (cursor.moveToNext());
	       }
	       if (cursor != null && !cursor.isClosed())
	       {
	     	  cursor.close();
	       }
//	       for(int i = 0; i < list.size(); i ++)
//		   {
//			   Log.e("" + table, list.get(i)+"");
//		   }
	       return list.size();
	}
	
	public List<String> getDeksName_Color()
	{
	       List<String> list = new ArrayList<String>();
//	       this.sqlDB = openHelper.getWritableDatabase();
	       
	       Cursor cursor = myDataBase.query(true, "m_FlashCardDecks", new String[]{"DeckTitle", "DeckColor", "pk_FlashCardDeckId","DeckImage"}, 
	    		   null, null, null, null, "pk_FlashCardDeckId ASC", null); 
	       int i=0;
	       if (cursor.moveToFirst())
	       {
	     	  do 
	     	  {	
	     		  String str = cursor.getString(0);
	    		  list.add(str);
				  Log.e("getDeksName_Color DeckTitle " + i, str+"");
	     		  str = cursor.getString(1);
	    		  list.add(str);
				  Log.e("getDeksName_Color DeckColor " + i, str+"");
	     		  i ++;
	     	  } 
	     	  while (cursor.moveToNext());
	       }
	       if (cursor != null && !cursor.isClosed())
	       {
	     	  cursor.close();
	       }
//	       for(int i = 0; i < list.size(); i ++)
//		   {
//			   Log.e("" + table, list.get(i)+"");
//		   }
	       return list;
	}
	
	public List<String> getSoundFileNme()
	{
       List<String> list = new ArrayList<String>();
//	       this.sqlDB = openHelper.getWritableDatabase();
       
       Cursor cursor = myDataBase.query(true, "m_FlashCardInternalDetails", new String[]{"fk_FlashCardId", "TitleData"}, 
    		   null, null, null, null, "fk_FlashCardId ASC", null); 
       int i=0;
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  String str = cursor.getString(0);
//	    		  list.add(str);
			  Log.e("getSoundFileNme fk_FlashCardId " + i, str+"");
     		  str = cursor.getString(1);
    		  list.add(str);
			  Log.e("getSoundFileNme TitleData " + i, str+"");
     		  i ++;
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
       for(int j = 0; j < list.size(); j ++)
	   {
		   Log.e("+ table" , list.get(j)+"");
	   }
       return list;
	}
	
	String[] lkp_TypesArrFC = new String[] {"lkp_Types", "pk_TypeId", "TypeValue"}; 
	String[] m_FlashCardDecksArr = new String[] {"m_FlashCardDecks", "pk_FlashCardDeckId", "DeckTitle", "DeckImage",
													"DeckColor"}; 
	
	String[] m_FlashCardArr = new String[] {"m_FlashCard", "pk_FlashCardId", "InternalCardId", "fk_FlashCardDeckId", 
													"ISKnown", "ISBookMarked"}; 
	
	String[] m_FlashCardInternalDetailsArr = new String[] {"m_FlashCardInternalDetails", "pk_FlashCardInternalDetailId", 
															"fk_FlashCardId", "fk_TypeId", "TitleData", 
															"FrontOrBack", "SortKey"}; 
	   
//	   public List<String> getAllBookMarkedCardStatus() 
//	   {
//		   List<String> list = new ArrayList<String>();
//		   Cursor cursor =sqlDB.query(true, TABLE_NAME, new String[]{bookmarkedCard, iKnowCard, cardID}, 
//	    		  						"bookmarkedCard=1", null, null, null, "cardID ASC", null);
//		   if (cursor.moveToFirst())
//		   {
//			   do 
//			   {	 
//				   list.add(cursor.getString(2));
//				   Log.e("DB  cursor.getString(2)  IIIIIIII  ", "" + cursor.getString(2));
//			   } 
//			   while (cursor.moveToNext());
//		   }
//		   if (cursor != null && !cursor.isClosed())
//		   {
//			   cursor.close();
//		   }
//		   return list;
//	   }
//		   
//	   public int getBookMarkedCardStatus() 
//	   {
//		   int i = 0;
//	      Cursor cursor =sqlDB.query(true, TABLE_NAME, new String[]{bookmarkedCard, iKnowCard, cardID}, 
//	    		  						"bookmarkedCard=1", null, null, null, null, null); 
//	      if (cursor.moveToFirst())
//	      {
//	    	  do 
//	    	  {	 
//	    		  i++;
//	    		  Log.e("DBiiiiiii  ",""+i);
//	    	  } 
//	    	  while (cursor.moveToNext());
//	      }
//	      if (cursor != null && !cursor.isClosed())
//	      {
//	    	  cursor.close();
//	      }
//	      return i;
//	   }
	
    public int UpdateResetCardValues(String bmc, String known, String id)
    {
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv=new ContentValues();
    	cv.put("ISBookMarked", bmc);
    	cv.put("ISKnown", known);
    	int i = db.update("m_FlashCard", cv, "pk_FlashCardId"+"=?", new String []{id});
    	return i;
    }
    
    public int UpdateKnownCardValues(String known, String id)
    {
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv=new ContentValues();
    	cv.put("ISKnown", known);
    	int i = db.update("m_FlashCard", cv, "pk_FlashCardId"+"=?", new String []{id});
    	return i;
    }
    
    public int UpdateBookMarkCardValues(String bmc, String id)
    {
//    	SQLiteDatabase db=openHelper.getWritableDatabase();
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv=new ContentValues();
    	cv.put("ISBookMarked", bmc);
    	int i = db.update("m_FlashCard", cv, "pk_FlashCardId"+"=?", new String []{id});
    	return i;
    }

    public void deleteAllBookMark() 
    {
    	ContentValues cv=new ContentValues();
    	cv.put("ISBookMarked", "0");
    	/*int i = */myDataBase.update("m_FlashCard", cv, null/*"pk_FlashCardId"+"=?"*/, null);
    }

    public void deleteAllProficiency() 
    {
    	ContentValues cv=new ContentValues();
    	cv.put("ISKnown", "0");
    	/*int i = */myDataBase.update("m_FlashCard", cv, null/*"pk_FlashCardId"+"=?"*/, null);
//       this.myDataBase.delete("m_FlashCard", "ISKnown= 0", new String[]{known});
    }
    
//   public void deleteAll() 
//   {
//      this.sqlDB.delete(TABLE_NAME, null, null);
//   }

   public void deleteAll(String known) 
   {
      this.myDataBase.delete("m_FlashCard", "iKnowCard= ?", new String[]{known});
   }
	   
//   public List<FlashCardDetailBean> selectAllCardStatus(String cardId) 
//   {
//      List<FlashCardDetailBean> list = new ArrayList<FlashCardDetailBean>();
//      Cursor cursor =myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked", "ISKnown", "pk_FlashCardId"}, 
//    		  						"cardID= ?", new String[]{cardId}, null, null, null, null); 
//      if (cursor.moveToFirst())
//      {
//    	  do 
//    	  {	 
//    		  FlashCardDetailBean artBean = new FlashCardDetailBean();
////    		  artBean.setbookmarkedCard(cursor.getString(0));
////    		  
////    		  artBean.setiKnowCard(cursor.getString(1));
////    		  artBean.setcardID(cursor.getString(2));
//        	 
//    		  list.add(artBean);
//    	  } 
//    	  while (cursor.moveToNext());
//      }
//      if (cursor != null && !cursor.isClosed())
//      {
//    	  cursor.close();
//      }
//      return list;
//   }
	
   public int getKnownBookMarkedCardStatus() 
   {
	   int i = 0;
      Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked", "ISKnown", "pk_FlashCardId"}, 
    		  						"ISBookMarked=1 AND ISKnown=1", null, null, null, null, null); 
      if (cursor.moveToFirst())
      {
    	  do 
    	  {	 
    		  i++;
    	  } 
    	  while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed())
      {
    	  cursor.close();
      }
	  Log.e("getKnownBookMarkedCardStatus  ",""+i);
      return i;
   }
   
   public List<String> getAllBookMarkedCardStatus() 
   {
	   List<String> list = new ArrayList<String>();
	   Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked", "ISKnown", "pk_FlashCardId"}, 
    		  						"ISBookMarked=1", null, null, null, "pk_FlashCardId ASC", null);
	   if (cursor.moveToFirst())
	   {
		   do 
		   {	 
			   list.add(cursor.getString(2));
			   Log.e("DB  getAllBookMarkedCardStatus  ", "" + cursor.getString(2));
		   } 
		   while (cursor.moveToNext());
	   }
	   if (cursor != null && !cursor.isClosed())
	   {
		   cursor.close();
	   }
	   return list;
   }
	   
   public List<String> getBookMarkedCardStatus() 
   {
	   int i = 0;
	   List<String> list = new ArrayList<String>();
      Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked", "pk_FlashCardId"/*, "ISKnown"*/}, 
    		  						"ISBookMarked=1", null, null, null, "pk_FlashCardId ASC", null); 
      if (cursor.moveToFirst())
      {
    	  do 
    	  {	  
			  list.add(cursor.getString(1));
    		  i++;
    		  Log.e("getBookMarkedCardStatus  ",""+i+cursor.getString(1));
    	  } 
    	  while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed())
      {
    	  cursor.close();
      }
      return list;
   }
   
   public String getBookMarkedCardStatus(String cardId) 
   {
	  String str = "";
      Cursor cursor =myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked"}, 
    		  						"ISBookMarked=1 AND pk_FlashCardId= ?", new String[]{cardId}, null, null, null, null);
      if (cursor.moveToFirst())
      {
    	  do 
    	  {	 
    		  str = cursor.getString(0);
    	  } 
    	  while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed())
      {
    	  cursor.close();
      }
	  Log.e(" int getBookMarkedCardStatus   ",""+str);
      return str;
   }
	   
   public int getKnownCardStatus()
   {
	  int i = 0;
      Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"ISBookMarked", "ISKnown", "pk_FlashCardId"}, 
    		  						"ISKnown=1", null, null, null, null, null); 
      if (cursor.moveToFirst())
      {
    	  do 
    	  {	 
    		  i++;
    	  } 
    	  while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed())
      {
    	  cursor.close();
      }
	  Log.e("getKnownCardStatus  ",""+i);
      return i;
   }

	public String getDeckID(String cardId) {
		  String str = "";
	      Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"fk_FlashCardDeckId"}, 
	    		  						"pk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
	      if (cursor.moveToFirst())
	      {
	    	  do 
	    	  {	 
	    		  str = cursor.getString(0);
	    	  } 
	    	  while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed())
	      {
	    	  cursor.close();
	      }
	    		  Log.e("getDeckID  ",""+str);
	      return str;
	}
	   
   public String getKnownCardStatus(String cardId) 
   {
	  String str = "";
      Cursor cursor = myDataBase.query(true, "m_FlashCard", new String[]{"ISKnown"}, 
    		  						"ISKnown=1 AND pk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
      if (cursor.moveToFirst())
      {
    	  do 
    	  {	 
    		  str = cursor.getString(0);
    	  } 
    	  while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed())
      {
    	  cursor.close();
      }
    		  Log.e("getKnownCardStatus  ",""+str);
      return str;
   }
	String[] m_FlashCardFrontBackDetailsArr = new String[] {"m_FlashCardFrontBackDetails", "pk_FlashCardFrontBackDetailId", 
			"fk_FlashCardId", "FrontOrBack", 
			"fk_TypeId", "TitleData", "SortKey"};
	
	public String getHTMLName(String cardId, boolean b) 
	{
		String str = "";
	    Cursor cursor = null;
		Log.e("getHTMLName(getHTMLName)", "" + cardId);
		if(b)
		    cursor = myDataBase.query(true, "m_FlashCardFrontBackDetails", new String[]{"TitleData"}, 
						"FrontOrBack=1 AND fk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
		else
		    cursor = myDataBase.query(true, "m_FlashCardFrontBackDetails", new String[]{"TitleData"}, 
					"FrontOrBack=2 AND fk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
			
		if (cursor.moveToFirst())
		{
			do 
			{
				str = cursor.getString(0);
			} 
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
				Log.e("getHTMLName  ",""+str);
		return str;
	}
	
	public String getSoundFileNme(String cardId)
	{
		String str = "";
       Cursor cursor = myDataBase.query(true, "m_FlashCardInternalDetails", new String[]{"TitleData"}, 
    		   "fk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  str = cursor.getString(0);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
		  Log.e("getSoundFileNme  ",""+str);
       return str;
	}
	
	public String getSoundFileFrontOrBackStatus(String cardId, String frorbk)
	{
		String str = "";
       Cursor cursor = myDataBase.query(true, "m_FlashCardInternalDetails", new String[]{"FrontOrBack"}, 
    		   "fk_FlashCardId= ? AND FrontOrBack=?", new String[]{cardId,frorbk}, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  str = cursor.getString(0);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
		  Log.e("getSoundFileFrontOrBackStatus  ",""+str);
       return str;
	}
	
//	public String getSoundFileFrontOrBackStatus(String cardId)
//	{
//		String str = "";
//       Cursor cursor = myDataBase.query(true, "m_FlashCardInternalDetails", new String[]{"FrontOrBack"}, 
//    		   "fk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
//       if (cursor.moveToFirst())
//       {
//     	  do 
//     	  {	
//     		  str = cursor.getString(0);
//     	  } 
//     	  while (cursor.moveToNext());
//       }
//       if (cursor != null && !cursor.isClosed())
//       {
//     	  cursor.close();
//       }
//		  Log.e("getSoundFileFrontOrBackStatus  ",""+str);
//       return str;
//	}
	
	public String getSoundFileStatusBack(String cardId)
	{
		String str = "";
       Cursor cursor = myDataBase.query(true, "m_FlashCardInternalDetails", new String[]{"TitleData"}, 
    		   "fk_FlashCardId= ?", new String[]{cardId}, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  str = cursor.getString(0);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
		  Log.e("getSoundFileNme  ",""+str);
       return str;
	}
	

	
	String[] m_ConfigDetailsArr = new String[] {"m_ConfigDetails", "Bookmark", "Animation", "AudioIcon", "Name", "HelpText",
													"Help", "Info", "TitleName", "DetailName"};
	public String getHelpText() {
		String str = "";
       Cursor cursor = myDataBase.query(true, "m_ConfigDetails", new String[]{"HelpText"}, 
    		   null, null, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  str = cursor.getString(0);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
		  Log.e("getHelpText  ",""+str);
       return str;
	}
	
	public String getInfoText() {
		String str = "";
       Cursor cursor = myDataBase.query(true, "m_ConfigDetails", new String[]{"Info"}, 
    		   null, null, null, null, null, null); 
       if (cursor.moveToFirst())
       {
     	  do 
     	  {	
     		  str = cursor.getString(0);
     	  } 
     	  while (cursor.moveToNext());
       }
       if (cursor != null && !cursor.isClosed())
       {
     	  cursor.close();
       }
		  Log.e("getInfoText  ",""+str);
       return str;
	}
	
	public List<String[]>getBookmarkedCards()
	{
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select pk_flashCardId,CardName,CardColor from m_FlashCard where IsBookMarked=1";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
					
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
	}
	
	public List<String[]>getDeckCards(int DeckId)
	{
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select pk_flashCardId,CardName,CardColor from m_FlashCard where fk_FlashCardDeckId="+DeckId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
					
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
	}
	
	public String getDeckColor(int DeckId)
	{
		String strArr = "";
		String sql = "Select DeckColor from m_FlashCardDecks where pk_FlashCardDeckId="+DeckId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				strArr = cursor.getString(0);
					
				//Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return strArr;
	}
	
	public String getDeckCardsName(int DeckId)
	{
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select DeckTitle from m_FlashCardDecks where pk_FlashCardDeckId="+DeckId;
		String DeckTitle="";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[1];
				strArr[0] = cursor.getString(0);			
				DeckTitle = strArr[0];
				//strArr[1] = cursor.getString(1);
				//strArr[2] = cursor.getString(2);
//				strArr[3] = cursor.getString(3);
				//Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				//list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return DeckTitle;
	}
	
	public List<String[]> getAllFlashCardSearch() {
		// TODO Auto-generated method stub select distinct fk_flashcardid, cardname from m_flashcardfrontbackdetails where filecontent like '%urine%' order by cardname
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select pk_flashCardId,CardName,CardColor from m_FlashCard where pk_flashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
				
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
		
	}
	
	public List<String[]> getAllFlashCard() {
		// TODO Auto-generated method stub select distinct fk_flashcardid, cardname from m_flashcardfrontbackdetails where filecontent like '%urine%' order by cardname
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select pk_flashCardId,CardName,CardColor from m_FlashCard where pk_flashCardId<54";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
				
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
		
	}
	
	public List<String[]> getAllSongs() {
		// TODO Auto-generated method stub select distinct fk_flashcardid, cardname from m_flashcardfrontbackdetails where filecontent like '%urine%' order by cardname
		List<String[]> list = new ArrayList<String[]>();
		String sql = "Select pk_flashCardId,CardName,CardColor from m_FlashCard where pk_flashCardId>53";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
				
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
		
	}
	
	
	public List<String[]> SearchCardsWithKeywordForAndroid(String strKeyword)
	{
		List<String[]> list = new ArrayList<String[]>();
		
		String sql ="select pk_FlashCardId,CardName from m_FlashCard where pk_flashCardId in (Select fk_flashCardId From m_FlashCardFrontBackDetails  where FileContent like '%"+strKeyword+"%') or CardName like '%"+strKeyword+"%'";
        Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[2];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
					
				//strArr[2] = cursor.getString(2);
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]*//*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
	}

	public List<String[]> getSearchResults(String givenAnswer) {
		// TODO Auto-generated method stub select distinct fk_flashcardid, cardname from m_flashcardfrontbackdetails where filecontent like '%urine%' order by cardname
		List<String[]> list = new ArrayList<String[]>();
		String sql = "select distinct fk_flashcardid, cardname, pk_FlashCardFrontBackDetailId from m_flashcardfrontbackdetails , m_flashcard " +
											"where pk_flashcardid=fk_flashcardid AND filecontent like '%"+ givenAnswer +"%' order by cardname";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
			
//				strArr[3] = cursor.getString(3);
				Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1] + "   " + strArr[2]/*+"    " + strArr[3]*/);
				list.add(strArr);
			}
			while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
		{
			cursor.close();
		}
		return list;
		
	}

	public List<List<String[]>> getSearchResultCursora(/*String givenAnswer*/) {
		List<String[]> list = null;//new ArrayList<String[]>();
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
		
		List <List<String[]>> listArr = new ArrayList<List<String[]>>();
		for (int i = 0; i < lst.size(); i++) {
			list = new ArrayList<String[]>();
//			select distinct fk_flashcardid, cardname, pk_FlashCardFrontBackDetailId from m_flashcardfrontbackdetails,m_flashcard  
//			where pk_flashcardid=fk_flashcardid and cardname like 'a%' order by cardname
			
			String sql = "select distinct fk_flashcardid, cardname, pk_FlashCardFrontBackDetailId from m_flashcardfrontbackdetails, m_flashcard " +
											"where pk_flashcardid=fk_flashcardid AND (cardname like '"+ lst.get(i) +"%' OR cardname like '<i>"+ lst.get(i) +"%') order by cardname";
			Cursor cursor = myDataBase.rawQuery(sql, null);
//	
//			String sql = "select distinct fk_flashcardid, cardname, pk_FlashCardFrontBackDetailId from m_flashcardfrontbackdetails " +
//														"where cardname like '"+ lst.get(i) +"%' order by cardname";
			
			List<String> listpk_FlashCardFrontBackDetailId = new ArrayList<String>();
			if (cursor.moveToFirst())
			{
				do
				{
					String strArr[] = new String[3];
					strArr[0] = cursor.getString(0);
					strArr[1] = cursor.getString(1);
					strArr[2] = cursor.getString(2);
					
	//				strArr[3] = cursor.getString(3);
					Log.e("getSearchResults ,   ",strArr[0] + "  ;  " + strArr[1] + "   " + strArr[2]/*+"    " + strArr[3]*/);
	
	        		String str = (strArr[2]);
	        		int k = Integer.parseInt(str);
	        		//if(k % 2 == 0)
	        			//k--;
	
	        		if(!listpk_FlashCardFrontBackDetailId.contains(k+""))
	        		{
	        			list.add(strArr);
	    				listpk_FlashCardFrontBackDetailId.add(k+"");
	        		}
				}
				while (cursor.moveToNext());
			}
			listArr.add(list);
		}
		return listArr;
	}

	public long saveAudioFileInfo(String flashcardID,	String audioFileName, String recordFilePath, String currentTime)
	{
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv = new ContentValues();
    	cv.put("fk_FlashCardId", flashcardID);
    	cv.put("audioTitle", audioFileName);
    	cv.put("audioFile", recordFilePath);
    	cv.put("recordingDate", currentTime);
    	long i = db.insert("m_FlashCardVoiceNotes", null, cv);
    	return i;
	}

	public List<String[]> getVoiceSearchResults() {
		List <String[]> listArr = new ArrayList<String[]>();
		String sql = "select distinct fk_FlashCardId, audioTitle, audioFile from m_FlashCardVoiceNotes order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[3];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
//				strArr[3] = cursor.getString(3);
				Log.e("getVoiceSearchResults ,   ",strArr[0] + "  ;  " + strArr[1] + "   " + strArr[2]/*+"    " + strArr[3]*/);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public List<String> getVoiceSearchResultsForIndividual() {
		List <String> listArr = new ArrayList<String>();
		String sql = "select distinct fk_FlashCardId from m_FlashCardVoiceNotes order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr = cursor.getString(0);
				Log.e("getVoiceSearchResultsForIndividual ,   ",strArr /*+ "  ;  " + strArr[1] + "   " + strArr[2]+"    " + strArr[3]*/);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public List<String[]> getVoiceSearchResultsForIndividualcard(String flashcardID) {
		List <String[]> listArr = new ArrayList<String[]>();
		String sql = "select distinct audioTitle, audioFile from m_FlashCardVoiceNotes where fk_FlashCardId = " + flashcardID + " order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[2];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				Log.e("getVoiceSearchResults ,   " , strArr[0] + "  ;  " + strArr[1]/* + "   " + strArr[2]+"    " + strArr[3]*/);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public boolean hasVoiceNotes(String flashcardID) {
		String sql = "select distinct audioFile from m_FlashCardVoiceNotes where fk_FlashCardId = " + flashcardID + " order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		boolean retValue = false;
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr = cursor.getString(0);
				retValue = true;
				Log.e("hasVoiceNotes ,   " , strArr);
			}
			while (cursor.moveToNext());
		}
		return retValue;
		
	}

	public String getCardName(String flashcardID) {
		String sql = "select distinct CardName from m_FlashCard where pk_FlashCardId = " + flashcardID + " order by pk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		String strArr = "";

		if (cursor.moveToFirst())
		{
			do
			{
				strArr = cursor.getString(0);
				
				Log.e("getCardName ,   ", strArr /*+ "  ;  " + strArr[1] + "   " + strArr[2]+"    " + strArr[3]*/);
			}
			while (cursor.moveToNext());
		}
		return strArr;
	}

	public long saveCommentsInfo(String flashcardID, String comments, String currentTime)
	{
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv = new ContentValues();
    	cv.put("fk_FlashCardId", flashcardID);
    	cv.put("Comments", comments);
    	cv.put("LastUpdated", currentTime);
    	long i = db.insert("m_FlashCardComments", null, cv);
    	return i;
	}

	public long updateCommentsInfo(String flashcardID, String comments, String currentTime)
	{
    	SQLiteDatabase db=myDataBase;
    	ContentValues cv = new ContentValues();
    	cv.put("Comments", comments);
    	cv.put("LastUpdated", currentTime);
    	int i = db.update("m_FlashCardComments", cv, "fk_FlashCardId"+"=?", new String []{flashcardID});
    	return i;
	}

	public List<String[]> getCommentSearchResults() {
		List <String[]> listArr = new ArrayList<String[]>();
		String sql = "select distinct fk_FlashCardId, Comments from m_FlashCardComments order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[2];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				Log.e("getCommentSearchResults ,   ",strArr[0] + "  ;  " + strArr[1]);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public List<String> getCommentSearchResultsForIndividual() {
		List <String> listArr = new ArrayList<String>();
		String sql = "select distinct fk_FlashCardId from m_FlashCardComments order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr = cursor.getString(0);
				Log.e("getVoiceSearchResultsForIndividual ,   ",strArr /*+ "  ;  " + strArr[1] + "   " + strArr[2]+"    " + strArr[3]*/);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public boolean hasCommentNotes(String flashcardID) {
		String sql = "select distinct Comments from m_FlashCardComments where fk_FlashCardId = " + flashcardID + " order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		boolean retValue = false;
		if (cursor.moveToFirst())
		{
			do
			{
				String strArr = cursor.getString(0);
				retValue = true;
				Log.e("hasCommentNotes ,   " , strArr);
			}
			while (cursor.moveToNext());
		}
		return retValue;
		
	}

	public List<String[]> getCommentSearchResultsForIndividualcard(String flashcardID) {
		List <String[]> listArr = new ArrayList<String[]>();
		String sql = "select distinct Comments from m_FlashCardComments where fk_FlashCardId = " + flashcardID + " order by fk_FlashCardId";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[1];
				strArr[0] = cursor.getString(0);
				Log.e("getVoiceSearchResults ,   " , strArr[0]);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public void clearVoiceNotesInDB() {
		myDataBase.delete("m_FlashCardVoiceNotes", null, null);
	}

	public void clearCommentsInDB() {
		myDataBase.delete("m_FlashCardComments", null, null);
	}

	public List<String[]> getDeksTotalIfo() {
		List <String[]> listArr = new ArrayList<String[]>();
		String sql = "select distinct pk_FlashCardDeckId, DeckTitle, DeckImage, DeckColor from m_FlashCardDecks";
		Cursor cursor = myDataBase.rawQuery(sql, null);

		if (cursor.moveToFirst())
		{
			do
			{
				String strArr[] = new String[4];
				strArr[0] = cursor.getString(0);
				strArr[1] = cursor.getString(1);
				strArr[2] = cursor.getString(2);
				strArr[3] = cursor.getString(3);
				Log.e("getCommentSearchResults ,   ",strArr[0] + "  ;  " + strArr[1] + "   " + strArr[2]+"    " + strArr[3]);
				listArr.add(strArr);
			}
			while (cursor.moveToNext());
		}
		return listArr;
	}

	public String getCardDeckid(String pk_FlashCardId) {
		
		String sql = "select distinct fk_FlashCardDeckId from m_FlashCard where pk_FlashCardId = " + pk_FlashCardId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		String strArr = "";
		if (cursor.moveToFirst())
		{
			do
			{
				strArr = cursor.getString(0);
				Log.e("getCommentSearchResults ,   ",strArr);
			}
			while (cursor.moveToNext());
		}
		return strArr;
		// TODO Auto-generated method stub
	}

	public static String DATABASE_TABLE_M_FLASHCARD = "m_FlashCard";
	public static String PK_FLASHCARDID = "pk_FlashCardId";
	public static String INTERNALCARDID = "InternalCardId";
	public static String CARDNAME = "CardName";
	public static String FK_FLASHCARDDECKID = "fk_FlashCardDeckId";
	public static String ISKNOWN = "ISKnown";
	public static String ISBOOKMARKED = "ISBookMarked";
	
	
	public static String DATABASE_TABLE_M_FLASHCARDCOMMENTS = "m_FlashCardComments";
	public static String PK_COMMENTID = "pk_CommentId";
	public static String FK_FLASHCARDID = "fk_FlashCardId";
	public static String COMMENTS = "Comments";
	public static String LASTUPDATED = "LastUpdated";

	public static String DATABASE_TABLE_M_FLASHCARDDECKS = "m_FlashCardDecks";
	public static String PK_FLASHCARDDECKID = "pk_FlashCardDeckId";
	public static String DECKTITLE = "DeckTitle";
	public static String DECKIMAGE = "DeckImage";
	public static String DECKCOLOR = "DeckColor";

	public static String DATABASE_TABLE_m_FlashCardFrontBackDetails = "m_FlashCardFrontBackDetails";
	public static String PK_FLASHCARDFRONTBACKDETAILID = "pk_FlashCardFrontBackDetailId";
	public static String FRONTORBACK = "FrontOrBack";
	public static String FK_TYPEID = "fk_TypeId";
	public static String TITLEDATA = "TitleData";
	public static String SORTKEY = "SortKey";
	public static String FILECONTENT = "FileContent";

	public static String DATABASE_TABLE_m_FlashCardInternalDetails = "m_FlashCardInternalDetails";
	public static String PK_FLASHCARDINTERNALDETAILID = "pk_FlashCardInternalDetailId";

	public static String DATABASE_TABLE_M_FLASHCARDVOICENOTES = "m_FlashCardVoiceNotes";
	public static String PK_VOICENOTEID = "pk_VoiceNoteId";
	public static String AUDIOTITLE = "audioTitle";
	public static String AUDIOFILE = "audioFile";
	public static String RECORDINGDATE = "recordingDate";

	public Cursor getDeksInfoCursor() 
	{
		String sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARDDECKS + " order by pk_FlashCardDeckId ASC";// where pk_FlashCardId = " + pk_FlashCardId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		return cursor;
	}

	public Cursor getm_FlashCardAllCursor() 
	{
		String sql;
		if(getRandomized())
		{
			 sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " order by Random()";// where pk_FlashCardId = " + pk_FlashCardId;
		}
		else
		{
			 sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " order by pk_FlashCardId ASC";// where pk_FlashCardId = " + pk_FlashCardId;
		}
		Cursor cursor = myDataBase.rawQuery(sql, null);
		return cursor;
	}

	public Cursor getm_FlashCardInfoCursor(String fk_FlashCardDeckId) 
	{
		String sql;
		if(getRandomized())
		{
			sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " where fk_FlashCardDeckId = " + fk_FlashCardDeckId + " order by Random()";
		}
		else
		{
			sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " where fk_FlashCardDeckId = " + fk_FlashCardDeckId + " order by pk_FlashCardId ASC";
		}
		
		Cursor cursor = myDataBase.rawQuery(sql, null);
		return cursor;
	}
	

	   
	   public Cursor getBookMarkedCardCursor() 
	   {
		 String sql;
         if(getRandomized())
         {
        	  sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " where ISBookMarked = 1 order by Random()";
         }
         else
         {
        	 sql = "select distinct * from " + DATABASE_TABLE_M_FLASHCARD + " where ISBookMarked = 1 order by pk_FlashCardId ASC";
         }
			Cursor cursor = myDataBase.rawQuery(sql, null);
	        return cursor;
	   }


		public void deleteCommentForcardID(String strCardId) 
		{
		      this.myDataBase.delete("m_FlashCardComments", "fk_FlashCardId= ?", new String[]{strCardId});
		}

		public void deleteVoiceNotesForcardID(String strCardId) 
		{
		      this.myDataBase.delete("m_FlashCardVoiceNotes", "fk_FlashCardId= ?", new String[]{strCardId});
		}

		public void deleteSingleVoiceNotesForcardID(String strCardId, String audTitle, String audPath) 
		{
		      int i = myDataBase.delete("m_FlashCardVoiceNotes", "fk_FlashCardId= ? AND audioTitle = ? AND audioFile = ?", new String[]{strCardId, audTitle, audPath});
		      System.out.println("deleteSingleVoiceNotesForcardID"+i);
		}

}