package com.spearheadinc.flashcards.prayerapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log; 

import java.io.ByteArrayOutputStream;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.InputStream;
import java.util.zip.ZipEntry; 
import java.util.zip.ZipInputStream; 


public class DecompressZip { 
  private String _zipFile; 
  private InputStream zipStream;
  private String _location; 

  public DecompressZip(String zipFile, String location, InputStream stream) { 
    _zipFile = zipFile; 
    _location = location; 
    zipStream = stream;
  } 

  public void unzip( Activity ctx ) { 
    try  { 
      InputStream fin = zipStream;
      ZipInputStream zin = new ZipInputStream(fin); 
      ZipEntry ze = null; 
      while ((ze = zin.getNextEntry()) != null) { 
        Log.v("Decompress", "Unzipping " + ze.getName()); 

        if(ze.isDirectory()) { 
        	System.out.println("ISDiredtr");
          _dirChecker(ze.getName()); 
        } else { 
          FileOutputStream fout = ctx.openFileOutput( ze.getName(), Context.MODE_WORLD_READABLE );
          System.out.println("Path:" + _location + ze.getName());
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
			int count;
			while ((count = zin.read(buffer)) != -1) {
				baos.write(buffer, 0, count);
  } 
			baos.writeTo( fout );
			zin.closeEntry(); 
	          fout.close(); 
      } 
      }
    
      zin.close(); 
    } catch(Exception e) { 
      Log.e("Decompress", "unzip", e); 
    } 

  } 
public boolean doesDirExist( String dir )
{
	  File f = new File(dir); 
	   if(f.isDirectory()) 
		      return( true);
	   return( false );
}
 
  public boolean _dirChecker(String dir) { 
    File f = new File( _location + dir); 

    if(!f.isDirectory()) { 
      f.mkdirs(); 
      return false;
    }
    else
    	return true;
  } 
}
