package com.spearheadinc.flashcards.prayerapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewVoiceNotes  extends Activity {

	private static NewVoiceNotes screen;
	LinearLayout progressBut;
	private Button startBut;
	private Button stopBut;
	private Button doneBut;
//	private ImageView imageBut;
    
	private static final String AUDIO_RECORDER_FOLDER = "PhlasCards";
	private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".mp4";
	private MediaRecorder mRecorder = null;
    private String extStorageDirectory = "";
	private String audioFileName;
	private String recordFilePath;
	private EditText ed;
	private FCDBHelper mFCDbHelper;
	private String mFlashCardId = "";
	
	public static NewVoiceNotes getInstance() {
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
		setContentView(R.layout.voicenotemicview);
		screen = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
        	mFlashCardId = extras.getString("com.fadavis.pharmphlashfc.phone.fk_FlashCardId");
        }
        mFCDbHelper = FlashCards.getScreen().getMyFCDbHelper();
		Button backBut = (Button) findViewById(R.id.voicenote_back);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		
//		imageBut = (ImageView) findViewById(R.id.voicenote_speaker);
		startBut = (Button) findViewById(R.id.voicenote_but_start);
		startBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
		    	audioFileName = ed.getText().toString();
		    	if(audioFileName != null && !audioFileName.equals(""))
		    	{
		    		startRecording();
					progressBut.setVisibility(View.VISIBLE);
					startBut.setVisibility(View.INVISIBLE);
					stopBut.setVisibility(View.VISIBLE);
		    	}
		    	else
		    	{
					progressBut.setVisibility(View.INVISIBLE);
					startBut.setVisibility(View.VISIBLE);
					stopBut.setVisibility(View.INVISIBLE);
		    		showDialog("Please Enter Voice Note Title");
		    	}
//				progressBut.setVisibility(View.VISIBLE);
//				startBut.setVisibility(View.INVISIBLE);
////				imageBut.setImageResource(R.drawable.speakersel);
//				stopBut.setVisibility(View.VISIBLE);
//		    	audioFileName = ed.getText().toString();
//		    	if(audioFileName != null && !audioFileName.equals(""))
//		    	{
//		    		startRecording();
//		    	}
//		    	else
//		    		showDialog("Please enter title");
			}
		});
		
		stopBut = (Button) findViewById(R.id.voicenote_but_stop);
		stopBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressBut.setVisibility(View.INVISIBLE);
//				imageBut.setImageResource(R.drawable.speaker);
				doneBut.setVisibility(View.VISIBLE);
				stopBut.setVisibility(View.INVISIBLE);
				stopRecording();
			}
		});
		
		doneBut = (Button) findViewById(R.id.voicenote_but_done);
		doneBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveDBinfoForAudio();
//				imageBut.setImageResource(R.drawable.speaker);
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		
		ed = (EditText) findViewById(R.id.voicenote_edit_name);
		
		progressBut = (LinearLayout) findViewById(R.id.voicenote_progress_lin);
		progressBut.setVisibility(View.INVISIBLE);
		stopBut.setVisibility(View.INVISIBLE);
		doneBut.setVisibility(View.INVISIBLE);

    }

	private void startRecording() 
	{
		checkSDCardStatus();
	}

	private void saveDBinfoForAudio()
	{
	    String DATE_FORMAT = "MM/dd/yy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    Calendar c1 = Calendar.getInstance(); 
		String currentTime = sdf.format(c1.getTime());
		
    	mFCDbHelper.openDataBase();
    	mFCDbHelper.saveAudioFileInfo(mFlashCardId, audioFileName, recordFilePath, currentTime);
    	mFCDbHelper.close();
    	VoiceNoteDetails.getInstance().populateVoiceNoteView();
	}
    
    private void checkSDCardStatus()
    {
        if(isSdPresent())
        {
			extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			extStorageDirectory += "/" + AUDIO_RECORDER_FOLDER;
			File myNewFolder = new File(extStorageDirectory);
			if(!myNewFolder.exists())
			{
				myNewFolder.mkdir();
			}
			startAudioSaving();
        }
        else
        	showDialog("Please add  a SD Card first");
    }
    
    public void showDialog(String message)
    {
    	AlertDialog.Builder alt_bld = new AlertDialog.Builder(NewVoiceNotes.this);
    	alt_bld.setMessage(message).setCancelable(false)
    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			dialog.cancel();
			}
		});
    	AlertDialog alert = alt_bld.create();
		alert.setTitle("Alert");
		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
		alert.show();
    }
    
    private void startAudioSaving() 
    {
    	audioFileName = ed.getText().toString();
    	if(audioFileName != null && !audioFileName.equals(""))
    	{
	    	File file = new File(extStorageDirectory, "/" + audioFileName  + System.currentTimeMillis() + AUDIO_RECORDER_FILE_EXT_WAV);
	    	if(!file.exists())
	    	{
	    		try 
	    		{
	    			recordFilePath = file.getAbsolutePath();
	    			mRecorder = new MediaRecorder();
	    			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	    			mRecorder.setOutputFile(recordFilePath);
	    			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    			mRecorder.prepare();
	    			mRecorder.start();
	    			isRecorderStarted = true;
	    		}		
	    		catch (Exception e) {
	    			e.printStackTrace();
	    			Log.e("IOException", "prepare() failed");
	    		}
	    	}
    	}
    	else
    		showDialog("Please enter title");
	}
    
    boolean isRecorderStarted = false;

	private void stopRecording() 
	{
		if (null != mRecorder && isRecorderStarted) 
		{
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			CardDetails.getScreen().changeOptionIcon();
		}
	}
    
	public static boolean isSdPresent() 
	{
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

}
