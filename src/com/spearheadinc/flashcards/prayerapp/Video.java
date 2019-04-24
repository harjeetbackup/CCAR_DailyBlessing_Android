package com.spearheadinc.flashcards.prayerapp;


import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Activity {
private MediaController mc;


/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.video);
VideoView vd = (VideoView) findViewById(R.id.VideoView);

//Uri uri = Uri.parse("android.resource://" + getPackageName() + R.raw.sample);


//Uri uri = Uri.parse("android.resource://" + getPackageName() +"/"+ R.raw.avtr);/*sample  night    avtr*/



//Uri uri = Uri.parse("file:///android_asset/avtr.3gp");

//Uri uri = Uri.parse("android.resource://[package]/raw/video")"file:///android_asset/webpage.html"

//Uri uri = Uri.parse("android.resource://[package]/raw/video")
//or
//Uri uri = Uri.parse("android.resource://[package]/"+R.raw.video);
//http://blogingtutorials.blogspot.com/2010/12/play-video-from-raw-folder-in-android.html
//http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/media/index.html

mc = new MediaController(this);
vd.setMediaController(mc);

//vd.setVideoURI(uri);
vd.start();
}

}
