package com.flether.android.tangshi300;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.os.Environment;


public class Mp3Player {
	private String SDPATH = Environment.getExternalStorageDirectory() + "/";
	public MediaPlayer mPlayer;

	Boolean hasFile = false;
	
	
	public Mp3Player(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		mPlayer = new MediaPlayer();
		if (file.exists()){
			mPlayer.reset();
			mPlayer.setDataSource(SDPATH + fileName);			
			hasFile = true;
		}
		else{
	
		}
		
	}

	
	public int play() throws IllegalStateException, IOException{
		if (hasFile) {	
			mPlayer.prepare();
			mPlayer.start();			
			return (0);
		}
		else{
			return (-1);
		}
	
	}
	
	public void stop(){
		
		mPlayer.stop();
	}

	public Boolean isPlaying(){
		
		return mPlayer.isPlaying();
	}
	
	public Boolean hasFile(){
		
		return hasFile;
	}

	

}   
